/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.core.misc;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicInteger;
import org.ai.datalab.core.Data;
import org.ai.datalab.core.DataLabJobListener;
import org.ai.datalab.core.DefaultExecutionUnit;
import org.ai.datalab.core.ExecutionConfig;
import org.ai.datalab.core.ExecutionResult;
import org.ai.datalab.core.Executor;
import org.ai.datalab.core.JobState;
import org.ai.datalab.core.resource.ResourcePool;
import org.ai.datalab.core.executor.Condition;
import static org.ai.datalab.core.executor.ExecutorType.READER;
import org.ai.datalab.core.executor.Processor;
import org.ai.datalab.core.executor.Reader;
import org.ai.datalab.core.executor.Writer;

/**
 *
 * @author Mohan Purushothaman
 */
public final class ThreadUnit implements Runnable {

    private final DefaultExecutionUnit providerUnit;
    private final Executor job;

    private final DataLabJobListener listener;
    private final ExecutionConfig config;
    private final int threadNo;

    //private final PrintWriter logOut;
    //private final PrintWriter logErr;
    private boolean isInterrupted;

    public ThreadUnit(DefaultExecutionUnit providerUnit, DataLabJobListener listener, ExecutionConfig config, int threadNo) {

        this.providerUnit = providerUnit;
        this.job = providerUnit.getExecutorProvider().getNewExecutor(); //this should be the only place where new block is getting created
        if (!providerUnit.getExecutorProvider().getProvidingType().isInstance(job)) {
            throw new RuntimeException("expected " + providerUnit.getExecutorProvider().getProvidingType() + " but found " + job.getClass());
        }
        this.listener = listener;
        this.config = config;
        this.threadNo = threadNo;
        //logOut = listener.getWriter() == null ? dummyWriter : listener.getWriter();
        //logErr = listener.getErrorWriter() == null ? dummyWriter : listener.getErrorWriter();
        updateState(JobState.PENDING);
    }

    private JobState currentState;

    public void updateState(JobState state) {
        this.currentState = state;
        listener.updateState(providerUnit, threadNo, state);
    }

    @Override
    public void run() {

        Thread.currentThread().setName(providerUnit.getDescription() + " (" + threadNo + ")");
        for (DefaultExecutionUnit unit : providerUnit.getChilds()) {
            unit.getInputQueue().increaseProducerCount();
        }
        try {
            try {
                updateState(JobState.INITIATING);
                job.init(config);
            } catch (Throwable e) {
                updateState(JobState.INITIATION_FAILED);
                throw e;//if changed INTERRUPTED EXCEPTION should be handled as separate and thrown back 
            }
            updateState(JobState.IN_PROGRESS);

            switch (providerUnit.getExecutorProvider().getProvidingType()) {

                case READER: {
                    doRead();
                    break;
                }
                case PROCESSOR: {
                    doProcess();
                    break;
                }
                case CONDITION: {
                    doConditionFlow();
                    break;
                }
                case WRITER: {
                    doWrite();
                    break;
                }
                default: {
                    throw new Exception("Unknown executor");
                }

            }

            try {
                updateState(JobState.SHUTDOWN_INITIATING);
                job.shutdown(config);

            } catch (Throwable e) {
                updateState(JobState.SHUTDOWN_FAILED);
                throw e; //if changed INTERRUPTED EXCEPTION should be handled as separate and thrown back 
            }
            boolean isEnded = providerUnit.getChildCount() == 0; //if no consumer then it is ended
            for (DefaultExecutionUnit unit : providerUnit.getChilds()) {
                isEnded = unit.getInputQueue().reduceProducerCount();   // all would end at same time
                //TODO redesign or else we might miss complete event some times with many branches
            }
            updateState(JobState.COMPLETED);
            if (isEnded) {
                listener.jobEnded(providerUnit);
            }
        } catch (Throwable ex) {
            if (currentState != JobState.SHUTDOWN_INITIATING && currentState != JobState.SHUTDOWN_FAILED) {
                try {
                    job.shutdown(config);
                } catch (Throwable e) {

                    if (currentState != JobState.INITIATION_FAILED) { //if initialzation failed , most probably ending also would fail, dont report this issue in that case
                        listener.jobInterrupted(providerUnit, threadNo, e);
                    }
                }
            }
            listener.jobInterrupted(providerUnit, threadNo, ex);
        }
    }

    private void doRead() throws Exception {
        Reader readerJob = (Reader) job;
        Data data = null;
        long lastUpdateTime = System.nanoTime();
        while (!(isInterrupted = Thread.currentThread().isInterrupted())) {
            try {
                data = readerJob.readData(config);
                if (data == null) {
                    break;
                }
                copyDataToOutputStream(data);

                listener.updateProgress(providerUnit, threadNo, new ExecutionResult(1, System.nanoTime() - lastUpdateTime, 1, config),data);
                lastUpdateTime = System.nanoTime();
            } catch (InterruptedException ex) {
                isInterrupted = true;
                throw ex;
            } catch (Exception e) {
                e.printStackTrace();
                listener.updateErrorProgress(providerUnit, threadNo, e, data);
            }
        }
    }

    private void doProcess() throws Exception {
        try {
            Processor processJob = (Processor) job;
            Data[] data;
            long lastUpdateTime = System.nanoTime();
            SingleThreadedBatchedQueue batchQueue = new SingleThreadedBatchedQueue(processJob.getMaximumBatchSize(), providerUnit.getInputQueue());
            while (!(isInterrupted = Thread.currentThread().isInterrupted()) && (data = batchQueue.fetchBatchedData()) != null) {
                try {
                    Data[] outputData = processJob.processData(data, config);
                    copyDataToOutputStream(outputData);
                    listener.updateProgress(providerUnit, threadNo, new ExecutionResult(data.length, System.nanoTime() - lastUpdateTime, outputData != null ? outputData.length : 0, config),outputData);
                    lastUpdateTime = System.nanoTime();
                } catch (InterruptedException ex) {
                    isInterrupted = true;
                    throw ex;
                } catch (Exception e) {
                    listener.updateErrorProgress(providerUnit, threadNo, e, data);
                }
            }
        } finally {
            if (!isInterrupted) {
                providerUnit.getInputQueue().put(Data.POISON_DATA);
            }
        }
    }

    private void doConditionFlow() throws Exception {
        try {
            Condition conditionJob = (Condition) job;
            Data data;
            long lastUpdateTime = System.nanoTime();
            while (!(isInterrupted = Thread.currentThread().isInterrupted()) && (data = providerUnit.getInputQueue().take()) != Data.POISON_DATA) {
                try {
                    copyDataToOutputStreamWithCondition(data, conditionJob.checkCondition(data, config));
                    listener.updateProgress(providerUnit, threadNo, new ExecutionResult(1, System.nanoTime() - lastUpdateTime, 1, config));
                    lastUpdateTime = System.nanoTime();
                } catch (InterruptedException ex) {
                    isInterrupted = true;
                    throw ex;
                } catch (Exception e) {
                    listener.updateErrorProgress(providerUnit, threadNo, e, data);
                }
            }
        } finally {
            if (!isInterrupted) {
                providerUnit.getInputQueue().put(Data.POISON_DATA);
            }
        }
    }

    private void doWrite() throws Exception {
        try {
            Writer writeJob = (Writer) job;
            Data[] data;
            long lastUpdateTime = System.nanoTime();
            SingleThreadedBatchedQueue batchQueue = new SingleThreadedBatchedQueue(writeJob.getMaximumBatchSize(), providerUnit.getInputQueue());
            while (!(isInterrupted = Thread.currentThread().isInterrupted()) && (data = batchQueue.fetchBatchedData()) != null) {
                try {
                    writeJob.writeData(data, config);
                    listener.updateProgress(providerUnit, threadNo, new ExecutionResult(data.length, System.nanoTime() - lastUpdateTime, 0, config));
                    lastUpdateTime = System.nanoTime();
                } catch (InterruptedException ex) {
                    isInterrupted = true;
                    throw ex;
                } catch (Exception e) {
                    listener.updateErrorProgress(providerUnit, threadNo, e, data);
                }
            }
        } finally {
            if (!isInterrupted) {
                providerUnit.getInputQueue().put(Data.POISON_DATA); // we need to put back if we read poison data
            }
        }
    }

    public void copyDataToOutputStream(Data... baseData) throws InterruptedException,Exception {
        for (DefaultExecutionUnit outUnit : providerUnit.getChilds()) {
            for (Data b : baseData) {
                outUnit.getInputQueue().put(b.cloneData());
            }
        }
    }

    public void copyDataToOutputStreamWithCondition(Data baseData, boolean flowCondition) throws InterruptedException, Exception {
        for (DefaultExecutionUnit outUnit : providerUnit.getChilds()) {
            outUnit.getInputQueue().put(baseData.cloneData(), flowCondition);
        }
    }

    private static final PrintWriter dummyWriter = new PrintWriter(new OutputStream() {

        @Override
        public void write(int b) throws IOException {

        }

        @Override
        public void write(byte[] b, int off, int len) throws IOException {
        }

        @Override
        public void write(byte[] b) throws IOException {
        }

    });

}
