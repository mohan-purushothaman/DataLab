/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.core.executor;

import org.ai.datalab.core.Data;
import org.ai.datalab.core.ExecutionConfig;
import org.ai.datalab.core.Executor;

/**
 *
 * @author Mohan Purushothaman
 */
public interface Condition extends Executor {

    /**
     * @param data data need to be tested for a condition
     * @param config
     * @param pool
     * @return true if data satisfies the condition or false
     * @throws java.lang.Exception
     */
    public boolean checkCondition(Data data,ExecutionConfig config) throws Exception;
    
    
    
}
