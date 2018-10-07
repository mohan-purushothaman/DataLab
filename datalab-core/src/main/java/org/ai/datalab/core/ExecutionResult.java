/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.core;

/**
 *
 * @author Mohan Purushothaman
 */
public class ExecutionResult {

    private final int processedCount;
    private final long processedNanoSeconds;
    private final int outputCount;
    private final ExecutionConfig config;

    public ExecutionResult(int processedCount, long processedNanoSeconds, int outputCount, ExecutionConfig config) {
        this.processedCount = processedCount;
        this.processedNanoSeconds = processedNanoSeconds;
        this.outputCount = outputCount;
        this.config = config;
    }

    public int getProcessedCount() {
        return processedCount;
    }

    public long getProcessedNanoSeconds() {
        return processedNanoSeconds;
    }

    public int getOutputCount() {
        return outputCount;
    }

    public ExecutionConfig getConfig() {
        return config;
    }

    
    

}
