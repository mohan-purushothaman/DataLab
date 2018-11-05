package org.ai.datalab.visual.impl.widget.misc.test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import org.ai.datalab.core.Data;
import org.ai.datalab.core.ExecutionConfig;
import org.ai.datalab.core.resource.ResourcePool;
import org.ai.datalab.core.executor.Processor;
import org.ai.datalab.core.executor.impl.SimpleUpdateProcessor;

/**
 *
 * @author Mohan Purushothaman
 */
public class NumberProcessor implements Processor{

    @Override
    public Data[] processData(Data[] data,ExecutionConfig config) throws Exception {
        for (Data d : data) {
            d.setValue("j",null, ((Number)d.getValue("i")).longValue()*2);
        }
        //int i=1/0;
        return data;
    }

    @Override
    public int getMaximumBatchSize() {
        return 11;
    }

    

    
}
