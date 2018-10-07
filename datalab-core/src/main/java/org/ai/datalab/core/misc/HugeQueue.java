///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package org.ai.datalab.core.misc;
//
//import com.hazelcast.config.Config;
//import com.hazelcast.core.Hazelcast;
//import com.hazelcast.core.HazelcastInstance;
//import java.util.concurrent.ArrayBlockingQueue;
//import java.util.concurrent.BlockingQueue;
//import java.util.concurrent.atomic.AtomicInteger;
//import org.ai.datalab.core.Data;
//import org.ai.datalab.core.DataLabQueue;
//
///**
// *
// * @author Mohan Purushothaman
// */
//public class HugeQueue implements DataLabQueue{
//    
//    private final BlockingQueue<Data> baseQueue;
//
//    private final AtomicInteger producersCount;
//
//    private final boolean flowCondition;
//
//    private final boolean hasFlowCondition;
//
//    private boolean isEnded = false;
//    
//    
//
//    public HugeQueue(Boolean flowCondition) {
//        baseQueue = HazelcastHelper.getPrivateQueue();
//        producersCount = new AtomicInteger();
//        this.hasFlowCondition = flowCondition != null;
//        this.flowCondition = hasFlowCondition ? flowCondition : true; // should not be used unless it is output queue of condition
//    }
//
//    public Boolean getFlowCondition() {
//        return hasFlowCondition ? flowCondition : null;
//    }
//
//
//    public void put(Data e) throws InterruptedException {
//        baseQueue.put(e);
//    }
//
//    @Override
//    public void put(Data e, boolean flowCondition) throws InterruptedException {
//        if (this.flowCondition == flowCondition) {
//            baseQueue.put(e);
//        }
//    }
//
//    public Data take() throws InterruptedException {
//        return baseQueue.take();
//    }
//
//    public int increaseProducerCount() {
//        return producersCount.incrementAndGet();
//    }
//
//    public boolean reduceProducerCount() throws InterruptedException {
//        if (producersCount.decrementAndGet() == 0) {
//            put(Data.POISON_DATA);
//            isEnded = true;
//            return true;
//        }
//        return false;
//    }
//
//    public int size() {
//        int size = baseQueue.size();
//        if (isEnded) {
//            return Math.max(0, size - 1);  //if ended it will have POISON DATA present
//        }
//        return size;
//    }
//
//}
