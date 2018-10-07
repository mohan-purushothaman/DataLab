/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.core.resource;

import java.lang.reflect.TypeVariable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Mohan Purushothaman
 */
public class ResourceFactory {

    private static final ConcurrentHashMap<String, ResourcePool> RESOURCE_POOL = new ConcurrentHashMap<String, ResourcePool>();

    public static final ResourcePool getResourcePool(String id) {
        return RESOURCE_POOL.get(id);
    }

    public static Collection<ResourcePool> getResourceList() {
        return RESOURCE_POOL.values();
    }

    public static final boolean addResourcePool(ResourcePool pool) {

        String id = pool == null ? null : pool.getResourceId();

        if (id == null || id.isEmpty()) {
            throw new RuntimeException("ResourcePool name can't be empty");
        }
        ResourcePool existingPool = RESOURCE_POOL.putIfAbsent(id, pool);
//        if (RESOURCE_POOL.containsKey(id)) {
//             throw new RuntimeException(id + " already registered");
//        }
        return existingPool != null;
    }

    public static final ResourcePool removeResourcePool(String poolName) {
        return RESOURCE_POOL.remove(poolName);
    }

    public static <V> Collection<ResourcePool> findResourcePools(Class<V> expectedResourceClazz, ResourcePoolQualifier<V> qualifier) {
        Collection<ResourcePool> poolList = new LinkedList<>();
        for (ResourcePool pool : RESOURCE_POOL.values()) {
            if (expectedResourceClazz.equals(pool.getResourceClass())) {
                if (qualifier == null || qualifier.validate(pool)) {
                    poolList.add(pool);
                }
            }

        }
        return poolList;
    }

}
