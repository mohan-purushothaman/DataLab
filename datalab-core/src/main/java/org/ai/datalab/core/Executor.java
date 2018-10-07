/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.core;

/**
 * Base executor interface which is core executing part of DataLab
 *
 * @author Mohan Purushothaman
 */
public interface Executor {

    default public void init(ExecutionConfig config) throws Exception {
    }

    default public void shutdown(ExecutionConfig config) throws Exception {
    }

    /**
     * batch size on how much data this writer can handle at a time
     *
     * It is a maximum size, executors should honor this and data size must be
     * less or equal to this maximum size
     *
     * @return maximum size it can process , should be >0
     */
    default public int getMaximumBatchSize() {
        return 1;
    }

}
