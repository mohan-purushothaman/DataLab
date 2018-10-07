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
 * interface for Writers which write data into external systems
 *
 * writers are single threaded as part of DataLab Beta, but can improve
 * performance by implementing batched/buffered writing
 *
 * @author Mohan Purushothaman
 */
public interface Writer extends Executor {

    /**
     * @param data list of data need to be written
     * @param pool
     * @throws java.lang.Exception
     */
    public void writeData(Data[] data,ExecutionConfig config) throws Exception;

 

}
