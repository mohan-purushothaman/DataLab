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
 * Simple Update Processor which simply update given data
 *
 * most of basic Data Processing can be cover by this processor
 *
 * @author Mohan Purushothaman
 */
public abstract class SimpleUpdateProcessor extends OneToOneDataProcessor {


    @Override
    public final Data process(Data data,ExecutionConfig config) throws Exception{
        updateData(data,config);
        return data;
    }

    public abstract void updateData(Data data,ExecutionConfig config) throws Exception;

}
