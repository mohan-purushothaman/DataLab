/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.test;

import org.ai.datalab.core.Data;
import org.ai.datalab.core.ExecutionConfig;
import org.ai.datalab.core.resource.ResourcePool;
import org.ai.datalab.core.executor.Reader;
import org.ai.datalab.core.misc.SimpleData;

/**
 *
 * @author Mohan Purushothaman
 */
public class NumberReader implements Reader {

    public static final long start = 0;
    public static  final long end = 1000;

    private long i = end;

    @Override
    public Data readData(ExecutionConfig config) throws Exception {
        if (i < end) {
            Data data = new SimpleData();
            data.setValue("i",null, i);
            i++;
            return data;
        } else {
            return null;
        }
    }

    @Override
    public void init(ExecutionConfig config) {
        i = start;
    }

    @Override
    public void shutdown(ExecutionConfig config) {
        assert i == end : "on shutdown i should be equal to end";
    }

}
