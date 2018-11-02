/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.visual.impl;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import org.ai.datalab.core.ExecutionConfig;
import org.ai.datalab.core.ExecutionResult;
import org.ai.datalab.core.resource.ResourcePool;

/**
 *
 * @author Mohan Purushothaman
 */
public class CumulativeResult {

    private final AtomicLong processedCount = new AtomicLong();
    private final AtomicLong processedNanoSeconds = new AtomicLong();

    private final Set<String> resourceSet = new ConcurrentSkipListSet<>();

    //private final AtomicLong outputCount=new AtomicLong();
    public void update(ExecutionResult result) {
        this.processedCount.addAndGet(result.getProcessedCount());
        this.processedNanoSeconds.addAndGet(result.getProcessedNanoSeconds());
        ResourcePool pool = result.getConfig().getResourcePool();
        if (pool != null) {
            resourceSet.add(pool.getResourceId());
        }
    }

    public long getProcessedCount() {
        return processedCount.get();
    }

    public long getProcessedNanoSeconds() {
        return processedNanoSeconds.get();
    }

    public long getAvgProcessTime() {
        return TimeUnit.NANOSECONDS.convert(getProcessedNanoSeconds() / Math.max(getProcessedCount(),1), TimeUnit.NANOSECONDS);
    }

    public String[] getResourceSet() {
        return resourceSet.toArray(new String[0]);
    }
    
    

}
