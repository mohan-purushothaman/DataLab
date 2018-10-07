/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.core.misc;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.ai.datalab.core.Data;
import org.ai.datalab.core.DataLabQueue;

/**
 *
 * @author Mohan Purushothaman
 */
public class SimpleLimitedQueue implements DataLabQueue {

    private final ArrayBlockingQueue<Data> baseQueue;

    private final AtomicInteger producersCount;

    private final boolean flowCondition;

    private final boolean hasFlowCondition;

    private boolean isEnded = false;

    public SimpleLimitedQueue(Boolean flowCondition) {
        baseQueue = new ArrayBlockingQueue<>(5000, true);
        producersCount = new AtomicInteger();
        this.hasFlowCondition = flowCondition != null;
        this.flowCondition = hasFlowCondition ? flowCondition : true; // should not be used unless it is output queue of condition
    }

    @Override
    public Boolean getFlowCondition() {
        return hasFlowCondition ? flowCondition : null;
    }

    @Override
    public void put(Data e) throws InterruptedException {
        baseQueue.put(e);
    }

    @Override
    public void put(Data e, boolean flowCondition) throws InterruptedException {
        if (this.flowCondition == flowCondition) {
            baseQueue.put(e);
        }
    }

    @Override
    public Data take() throws InterruptedException {
        return baseQueue.take();
    }
    
     @Override
    public Data pool(long timeout, TimeUnit unit) throws InterruptedException {
        return baseQueue.poll(timeout, unit);
    }

    @Override
    public int increaseProducerCount() {
        return producersCount.incrementAndGet();
    }

    @Override
    public boolean reduceProducerCount() throws InterruptedException {
        if (producersCount.decrementAndGet() == 0) {
            if (!isEnded) {
                isEnded = true;
                put(Data.POISON_DATA);
            }
            return true;
        }
        return false;
    }

    @Override
    public int size() {
        int size = baseQueue.size();
        if (isEnded) {
            return Math.max(0, size - 1);  //if ended it will have POISON DATA present
        }
        return size;
    }

}
