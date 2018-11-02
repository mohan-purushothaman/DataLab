/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.core;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import org.ai.datalab.core.builder.ExecutionUnit;
import org.ai.datalab.core.misc.Configuration;

/**
 *
 * @author Mohan Purushothaman
 */
public class DataJob {

    public static DataJob getJob(String name, Configuration conf) {
        return new DataJob(name, conf);
    }

    private final String name;
    private final Configuration conf;

    private DataJob(String name, Configuration conf) {
        this.name = name;
        this.conf = conf;
    }

    public String getName() {
        return name;
    }

    public Configuration getConf() {
        return conf;
    }

    private DefaultExecutionUnit readerUnit;

    public ExecutionUnit setReader(String description, ExecutorProvider provider) {
        readerUnit = new DefaultExecutionUnit(description, provider, null, null);
        return readerUnit;
    }

    public ExecutionUnit getReaderUnit() {
        return readerUnit;
    }

    private transient List<ExecutorService> executingServices = null;

    public final Future<?> startJob(final DataLabJobListener progressListener) throws Exception {
        synchronized (this) {
            if (executingServices != null) {
                throw new Exception("Job already started");
            }
            executingServices = new LinkedList<>();
        }

        FutureTask task = new FutureTask<Void>(new Runnable() {
            @Override
            public void run() {
                try {
                    scheduleExecution(readerUnit, progressListener, executingServices);
                    progressListener.initCompleted();
                    for (ExecutorService executingService : executingServices) {
                        executingService.awaitTermination(2, TimeUnit.DAYS);
                    }
                    progressListener.jobEnded();
                } catch (Exception ex) {
                    System.err.println(ex.getMessage());
                    progressListener.jobInterrupted(ex);
                } finally {
                    executingServices = null;
                }
            }

        }, null) {

            @Override
            public boolean cancel(boolean mayInterruptIfRunning) {
                try {
                    interruptJob();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return super.cancel(mayInterruptIfRunning);

            }

        };

        Executors.newFixedThreadPool(1).execute(task);
        return task;
    }

    public final void interruptJob() {
        for (ExecutorService executingService : executingServices) {

            try {
                List<Runnable> p = executingService.shutdownNow();
            } catch (Throwable t) {
                t.printStackTrace();
            }

        }
    }

    private void scheduleExecution(DefaultExecutionUnit unit, DataLabJobListener listener, List<ExecutorService> executingServices) {
        unit.scheduleExecution(listener, executingServices);

        for (DefaultExecutionUnit u : unit.getChilds()) {
            scheduleExecution(u, listener, executingServices);
        }
    }

}
