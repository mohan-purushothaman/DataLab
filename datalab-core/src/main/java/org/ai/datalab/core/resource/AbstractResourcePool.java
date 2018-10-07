/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.core.resource;

import java.io.IOException;
import java.io.ObjectInputStream;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;

/**
 *
 * @author Mohan Purushothaman
 * @param <V>
 */
public abstract class AbstractResourcePool<V> implements ResourcePool<V> {

    private final String resourceId;

    private transient GenericObjectPool<V> pool;

    private int maxCount;

    private final String resourceClassName;

    private transient Class<V> resourceClass;

    public AbstractResourcePool(String resourceId, int maxCount, Class<V> resourceClass) throws Exception {
        this.resourceId = resourceId;
        this.maxCount = maxCount;
        this.resourceClassName = resourceClass.getName();
        init();
    }

    public void init() throws Exception {
        pool = new GenericObjectPool<>(new BasePooledObjectFactory<V>() {

            @Override
            public V create() throws Exception {
                return createResource();
            }

            @Override
            public PooledObject<V> wrap(V obj) {
                return new ResourcePooledObject<>(obj);
            }

            @Override
            public boolean validateObject(PooledObject<V> p) {
                return true;
            }

            @Override
            public void destroyObject(PooledObject<V> p) throws Exception {
                releaseResource(p.getObject());
            }

        });
        //pool.setTestOnReturn(true);
        resourceClass = (Class<V>) Class.forName(resourceClassName);
        pool.setMaxTotal(maxCount);
    }

    @Override
    public final String getResourceId() {
        return resourceId;
    }

    protected abstract V createResource() throws Exception;

    protected abstract void releaseResource(V resource) throws Exception;

    @Override
    public Resource<V> getResource() throws Exception {
        return new Resource<>(pool, pool.borrowObject());
    }

    @Override
    public int getNumActive() {
        return pool.getNumActive();
    }

    @Override
    public int getMaxCount() {
        return maxCount;
    }

    @Override
    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
        pool.setMaxTotal(maxCount);
    }

    @Override
    public String toString() {
        return resourceId;
    }

    @Override
    public Class<V> getResourceClass(){
        return resourceClass;
    }
    
    
    

    static class ResourcePooledObject<V> extends DefaultPooledObject<V> {

        public ResourcePooledObject(V object) {
            super(object);
        }
    }
}
