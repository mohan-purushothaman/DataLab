/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.core;

import org.ai.datalab.core.resource.ResourcePool;

/**
 *
 * @author Mohan Purushothaman
 */
public class ExecutionConfig {

    private final ResourcePool pool;

    public ExecutionConfig(ResourcePool pool) {
        this.pool = pool;
    }

    public ResourcePool getResourcePool() {
        return pool;
    }
    
   
    
    
    
}
