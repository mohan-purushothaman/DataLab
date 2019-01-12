/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.core;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import org.ai.datalab.core.resource.ResourcePool;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.ai.datalab.core.builder.ConditionExecutionUnit;
import org.ai.datalab.core.builder.ExecutionUnit;
import org.ai.datalab.core.executor.ExecutorType;
import org.ai.datalab.core.misc.Property;
import org.ai.datalab.core.misc.PropertyHandler;
import org.ai.datalab.core.resource.ResourceFactory;
import org.ai.datalab.core.misc.ThreadUnit;

/**
 *
 * @author Mohan Purushothaman
 */
public class DefaultExecutionUnit extends PropertyHandler implements ExecutionUnit {

    private ExecutorProvider provider;
    private final Set<ExecutionUnit> parent = new LinkedHashSet<>();

    private final Set<DefaultExecutionUnit> childUnits = new LinkedHashSet<>();

    private final DataLabQueue inputQueue;
    private ExecutorService executorPool;
    private String description;

    protected DefaultExecutionUnit(String description, ExecutorProvider provider, ExecutionUnit parent, DataLabQueue inputQueue) {
        this.provider = provider;
        if (parent != null) {
            addParent(parent);
        }
        this.description = description;
        this.inputQueue = inputQueue;
        setThreadCount(1); //ADD THREAD PROPERTY INTO executor unit
    }

    @Override
    public ExecutionUnit addExecutor(String description, ExecutorProvider provider) {
        switch (provider.getProvidingType()) {
            case CONDITION:
                return addCondition(description, provider);
            default:
                DefaultExecutionUnit unit = new DefaultExecutionUnit(description, provider, this, DataLabQueue.newQueue());
                addUnit(unit);
                return unit;
        }
    }

    public void addChild(DefaultExecutionUnit unit) {
        addUnit(unit);
    }

    protected void addUnit(DefaultExecutionUnit unit) {
        childUnits.add(unit);
    }

    @Override
    public ConditionExecutionUnit addCondition(String description, ExecutorProvider provider) {
        ConditionChildUnit unit = new ConditionChildUnit(description, provider, this);
        addUnit(unit);
        return unit;
    }

    @Override
    public List<ExecutionUnit> getParent() {
        return new ArrayList<>(parent);
    }

    public boolean isRootNode() {
        return parent.isEmpty();
    }

    @Override
    public ExecutionUnit getFirstParent() {
        return parent.iterator().hasNext() ? parent.iterator().next() : null;
    }

    public void setProvider(ExecutorProvider provider) {
        this.provider = provider;
    }

    public final void addParent(ExecutionUnit parent) {
        this.parent.add(parent);
    }

//    public void setInputQueue(DataLabQueue inputQueue) {
//        this.inputQueue = inputQueue;
//    }
    public void setDescription(String description) {
        this.description = description;
    }

    public DataLabQueue getInputQueue() {
        return inputQueue;
    }

    @Override
    public int getChildCount() {
        return childUnits.size();
    }

    @Override
    public List<DefaultExecutionUnit> getChilds() {
        return new ArrayList<>(childUnits);
    }

    @Override
    public ExecutorProvider getExecutorProvider() {
        return provider;
    }

    // private long inputQueueTimeoutInSeconds;
    @Override
    public int getThreadCount() {
        Integer i = getProperty(Property.THREAD_COUNT, Integer.class);
        if (i != null) {
            return Math.max(1, i);
        }
        return 1;
    }

    @Override
    public final ExecutionUnit setThreadCount(int threadCount) {
        if (threadCount < 1) {
            threadCount = 1;
        }
        switch (provider.getProvidingType()) {
            case READER:
                if (threadCount != 1) {
                    throw new RuntimeException("Can't change thread count for Reader, it should be 1 always");
                }
        }

        setProperty(Property.THREAD_COUNT, threadCount, null, !provider.isMultiThreadingSupported());
        return this;
    }
//
//    @Override
//    public long getInputQueueTimeoutInSeconds() {
//        return inputQueueTimeoutInSeconds;
//    }
//
//    @Override
//    public ExecutionUnit setInputQueueTimeout(int timeout, TimeUnit unit) {
//        this.inputQueueTimeoutInSeconds = unit.toSeconds(timeout);
//        return this;
//    }

    void scheduleExecution(DataLabJobListener listener, List<ExecutorService> executingServices) {
        int threadCount = getThreadCount();
        if (!provider.isMultiThreadingSupported()) {
            threadCount = 1;
        }
        executorPool = Executors.newFixedThreadPool(threadCount);
        executingServices.add(executorPool);
        ExecutionConfig config = new ExecutionConfig(getResourcePool());
        listener.jobStarting(this, config);

        for (int i = 0; i < threadCount; i++) {
            executorPool.submit(getWorkerUnit(this, listener, config, i));
        }
        executorPool.shutdown();

    }

    private static Runnable getWorkerUnit(DefaultExecutionUnit unit, DataLabJobListener listener, ExecutionConfig config, int threadNo) {
        return new ThreadUnit(unit, listener, config, threadNo);
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return getDescription();
    }

    private ResourcePool getResourcePool() {
        String id = provider.getResourceID();
        if (id != null) {
            ResourcePool pool = ResourceFactory.getResourcePool(id);
            if (pool == null) {
                throw new RuntimeException("Resource pool not registered (" + id + ")");
            }
            return pool;
        }
        return null;
    }

    class ConditionChildUnit extends DefaultExecutionUnit implements ConditionExecutionUnit {

        ConditionChildUnit(String description, ExecutorProvider provider, ExecutionUnit parent) {
            super(description, provider, parent, DataLabQueue.newQueue());
            assert provider.getProvidingType() == ExecutorType.CONDITION;
        }

        @Override
        public ExecutionUnit addExecutor(String description, ExecutorProvider provider, boolean addInTrueBranch) {
            DefaultExecutionUnit unit = new DefaultExecutionUnit(description, provider, this, DataLabQueue.newQueue(addInTrueBranch));
            addUnit(unit);
            return unit;
        }

    }

}
