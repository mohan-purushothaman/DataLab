/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.core.builder;

import org.ai.datalab.core.ExecutorProvider;

/**
 *
 * @author Mohan Purushothaman
 */
public interface ConditionExecutionUnit extends ExecutionUnit {

    public ExecutionUnit addExecutor(String description,ExecutorProvider provider, boolean addInTrueBranch);

    default ExecutionUnit addExecutorOnTrueBranch(String description,ExecutorProvider provider) {
        return addExecutor(description,provider, true);
    }

    default ExecutionUnit addExecutorOnFalseBranch(String description,ExecutorProvider provider) {
        return addExecutor(description,provider, false);
    }
    
    
}
