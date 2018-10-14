/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.core;

import org.ai.datalab.core.adx.misc.MappingHelper;

/**
 *
 * @author Mohan Purushothaman
 */
public abstract class AbstractExecutorProvider implements ExecutorProvider {

    public MappingHelper mapping;
    
    private final String resourceId;

    public AbstractExecutorProvider(MappingHelper mapping,String resourceId) {
        this.mapping = mapping;
        this.resourceId=resourceId;
    }

    public MappingHelper getMapping() {
        return mapping;
    }

    public void setMapping(MappingHelper mapping) {
        this.mapping = mapping;
    }

    @Override
    public String getResourceID() {
        return resourceId;
    }

}
