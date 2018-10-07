/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.ai.datalab.core.Data;
import org.ai.datalab.core.ExecutionConfig;
import org.ai.datalab.core.resource.ResourcePool;
import org.ai.datalab.core.executor.Writer;

/**
 *
 * @author Mohan Purushothaman
 */
public class NumberWriter implements Writer {

    private final Set<Long> iSet = new HashSet<Long>();

    @Override
    public void init(ExecutionConfig config) {
        for (long i = NumberReader.start; i < NumberReader.end; i++) {
            iSet.add(i);
        }
        System.err.println("Writer inited" + new Date());
    }

    @Override
    public void shutdown(ExecutionConfig config) {
        assert iSet.isEmpty() : " missed to remove some data's " + iSet;
        System.err.println("Job ending " + new Date());
    }

    @Override
    public void writeData(Data[] data, ExecutionConfig config) throws Exception {
        for (Data d : data) {
            assert iSet.remove(((Number) d.getValue("i")).longValue()) : " invalid data found in data " + d;
        }

    }

    @Override
    public int getMaximumBatchSize() {
        return 10;
    }

}
