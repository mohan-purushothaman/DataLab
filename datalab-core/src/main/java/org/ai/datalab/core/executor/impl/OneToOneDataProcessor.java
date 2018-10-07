/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.core.executor.impl;

import org.ai.datalab.core.Data;
import org.ai.datalab.core.ExecutionConfig;
import org.ai.datalab.core.resource.ResourcePool;

/**
 * Basic processor implementation which processes one data at a time
 *
 * @author Mohan Purushothaman
 */
public abstract class OneToOneDataProcessor extends OneToManyDataProcessor {

    @Override
    public final Data[] processData(Data data,ExecutionConfig config) throws Exception{
        return new Data[]{process(data,config)};
    }

    public abstract Data process(Data data,ExecutionConfig config) throws Exception;

}
