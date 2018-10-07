/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.core.misc;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import org.ai.datalab.core.Data;
import org.ai.datalab.core.DataLabQueue;

/**
 *
 * @author Mohan Purushothaman
 */
public class SingleThreadedBatchedQueue {

    private final int batchSize;
    private final DataLabQueue queue;
    private Data[] queueData;

    private boolean isEnded = false;

    private final long BATCH_TIMEOUT = 30;

    public SingleThreadedBatchedQueue(int batchSize, DataLabQueue queue) {
        this.batchSize = batchSize;
        this.queue = queue;
        queueData = new Data[batchSize];
    }

    public Data[] fetchBatchedData() throws InterruptedException {
        if (isEnded) {
            return null;
        }
        Data temp;
        for (int i = 0; i < batchSize; i++) {
            temp = queue.pool(BATCH_TIMEOUT, TimeUnit.SECONDS);
            if (temp == null) { //timeout occured
                if (i == 0) { //no element is there in current batch
                    i = -1;  //resetting counter 
                    continue;  //i know unnecessary but to avoid
                }else{
                    return Arrays.copyOfRange(queueData, 0, i);
                }
            } else {
                if (Data.isPoisonData(temp)) {
                    isEnded = true;
                    if (i == 0) {
                        queueData = null;
                        return null;
                    }
                    Data[] data = new Data[i];
                    System.arraycopy(queueData, 0, data, 0, i);
                    queueData = null;
                    return data;
                }
                queueData[i] = temp;
            }
        }
        return queueData;
    }

}
