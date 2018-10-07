/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.core.builder;

import org.ai.datalab.core.ExecutorProvider;
import org.ai.datalab.core.executor.ExecutorType;

/**
 *
 * @author Mohan Purushothaman
 */
public interface ExecutionUnit {

    public ExecutionUnit addExecutor(String description,ExecutorProvider provider);

    public ConditionExecutionUnit addCondition(String description,ExecutorProvider provider);

    public ExecutionUnit getParent();

    public ExecutionUnit getChildAt(int index);

    public int getChildCount();
    
    public ExecutorProvider getExecutorProvider();
    
    public default ExecutorType getProvidingType(){
        return getExecutorProvider().getProvidingType();
    }

    public int getThreadCount();

    public ExecutionUnit setThreadCount(int threadCount);

 //   public long getInputQueueTimeoutInSeconds();

   // public ExecutionUnit setInputQueueTimeout(int timeout, TimeUnit unit);

    public String getDescription();

    /**
     * NEED TO IMPLEMENT IN FUTURE
     *
     * public void setChildAt(int index,ExecutorProvider provider);
     *
     * public void insertChildAt(int index,ExecutorProvider provider);
     *
     *
     */
}
