/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.core.executor.impl;

import java.util.List;
import org.ai.datalab.core.Data;
import org.ai.datalab.core.ExecutionConfig;
import org.ai.datalab.core.resource.ResourcePool;
import org.ai.datalab.core.executor.Processor;

/**
 * Basic processor implementation which processes one data at a time
 *
 * One to Many Data Processor
 *
 * @author Mohan Purushothaman
 */
public abstract class OneToManyDataProcessor implements Processor {

    @Override
    public final Data[] processData(Data[] data,ExecutionConfig config )  throws Exception{
        assert data.length==1:"expected length is 1 but found "+data.length;
        return processData(data[0],config);
    }

    public abstract Data[] processData(Data data,ExecutionConfig config) throws Exception;

    @Override
    public final int getMaximumBatchSize() {
        return 1;
    }

//    @Override
//    public int getOptimalBatchSize() {
//        return getMaximumBatchSize();
//    }

}
