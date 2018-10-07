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
 * interface for Processor which processes given set of data's
 *
 * Processor is the execution body of DataLab and should be able abstract any
 * processing model
 *
 * It is Many to many data processor implementation which can wrap any kind of
 * data analytics
 *
 * Many to many data processor
 *
 * @author Mohan Purushothaman
 */
public interface Processor extends Executor {

    /**
     * processor either can update the given data and return back the same or
     * create new data's
     *
     * @param data list of data needs to be processed
     * @param config 
     * @return list of data generated after processing
     * @throws java.lang.Exception any exception as part of processing
     */
    public Data[] processData(Data[] data,ExecutionConfig config) throws Exception;

    /**
     * batch size on how much data this processor can handle at a time
     *
     * It is a maximum size, executors will honor this and data size would be
     * less or equal to this maximum size
     *
     * @return maximum size it can process , should be >0
     */
    public abstract int getMaximumBatchSize();

//    /**
//     * Executors implementations need not honor this optimal size
//     *
//     * basic executor implementation will always try to squeeze upto maximum
//     * size regardless of optimal size
//     * 
//     * @return optimal size of data for this processor
//     */
//    public abstract int getOptimalBatchSize();

}
