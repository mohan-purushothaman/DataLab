/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.core;

import java.util.concurrent.TimeUnit;
import org.ai.datalab.core.misc.SimpleLimitedQueue;

/**
 *
 * @author Mohan Purushothaman
 */
public interface DataLabQueue {

    public Boolean getFlowCondition();

    public void put(Data e) throws InterruptedException;

    public void put(Data e, boolean flowCondition) throws InterruptedException;

    public Data take() throws InterruptedException;
    
    public Data pool(long timeout, TimeUnit unit) throws InterruptedException ;

    public int increaseProducerCount();

    public boolean reduceProducerCount() throws InterruptedException;

    public int size();
    
    
    public static DataLabQueue newQueue(Boolean flowCondition){
        return new SimpleLimitedQueue(flowCondition);
    }
    public static DataLabQueue newQueue(){
        return newQueue(null);
    }
}
