/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.core.builder;

import java.util.List;
import org.ai.datalab.core.DefaultExecutionUnit;
import org.ai.datalab.core.ExecutorProvider;
import org.ai.datalab.core.executor.ExecutorType;

/**
 *
 * @author Mohan Purushothaman
 */
public interface ExecutionUnit {

    public ExecutionUnit addExecutor(String description,ExecutorProvider provider);

    public ConditionExecutionUnit addCondition(String description,ExecutorProvider provider);

    public List<ExecutionUnit> getParent();
    
    public ExecutionUnit getFirstParent();
    

    public int getChildCount();
    
    public List<DefaultExecutionUnit> getChilds();
    
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
