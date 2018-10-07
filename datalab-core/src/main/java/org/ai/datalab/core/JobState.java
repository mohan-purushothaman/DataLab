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
public enum JobState {

    PENDING,
    INITIATING,
    INITIATION_FAILED,
    IN_PROGRESS,
    SHUTDOWN_INITIATING,
    SHUTDOWN_FAILED,
    COMPLETED;
    
    
    
}
