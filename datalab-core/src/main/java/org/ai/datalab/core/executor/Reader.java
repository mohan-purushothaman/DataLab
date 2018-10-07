/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.core.executor;

import org.ai.datalab.core.Data;
import org.ai.datalab.core.ExecutionConfig;
import org.ai.datalab.core.Executor;
import org.ai.datalab.core.resource.ResourcePool;

/**
 * interface for Reader which reads data from other system
 *
 * Reader are single threaded as part of DataLab Beta
 * 
 * Readers would be made multithreaded once DataLab supports distributed systems
 * 
 * Readers should not involve any of processing in
 * doing so since these are starting part of DataLab
 *
 * Reader should be designed like simply read data (parsing included) from that
 * system (in bulk would be recommended) and sending it down streams
 *
 * If other executors are waiting for reader , that reader is not optimized or
 * designed wrong
 *
 * @author Mohan Purushothaman
 */
public interface Reader extends Executor{

    /**
     *
     * @param config  ExecutionConfigs needed by Executor
     * @return read next Data from the system , null if no more data present in this reader
     * @throws java.lang.Exception when error happened in reading the data
     */
    public Data readData(ExecutionConfig config) throws Exception;
}
