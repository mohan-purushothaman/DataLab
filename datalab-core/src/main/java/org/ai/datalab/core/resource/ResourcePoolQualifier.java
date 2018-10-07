/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.core.resource;

/**
 *
 * @author Mohan Purushothaman
 */
public interface ResourcePoolQualifier<V> {
    boolean validate(ResourcePool<V> resource);
}
