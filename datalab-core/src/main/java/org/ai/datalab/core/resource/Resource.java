/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.core.resource;

import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

/**
 *
 * @author Mohan Purushothaman
 * @param <V>
 */
public class Resource<V> implements AutoCloseable {

    private final ObjectPool<V> pool;
    private final  V resource;

    public Resource(ObjectPool<V> pool,  V resource) throws Exception {
        this.pool = pool;
        this.resource = resource;
    }

    public V get() throws Exception {
        return resource;
    }

    @Override
    public void close() throws Exception {
        pool.returnObject(resource);
    }
}
