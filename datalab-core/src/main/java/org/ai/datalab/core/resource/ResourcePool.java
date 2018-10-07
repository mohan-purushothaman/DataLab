/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.core.resource;

/**
 *
 * @author Mohan Purushothaman
 * @param <V>
 */
public interface ResourcePool<V> {
    public Resource<V> getResource() throws Exception ;

    public String getResourceId();
    
    public int getNumActive();
    
    public int getMaxCount();
    
    public void setMaxCount(int maxCount);
    
    public Class<V> getResourceClass();
    
    public void init() throws Exception;
}
