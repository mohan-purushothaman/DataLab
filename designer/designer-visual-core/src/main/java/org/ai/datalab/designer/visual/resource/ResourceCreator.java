/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.designer.visual.resource;

import javax.swing.JComponent;
import org.ai.datalab.core.resource.ResourcePool;

/**
 *
 * @author Mohan Purushothaman
 */
public abstract class ResourceCreator<V> {
    
    public abstract JComponent getDetailsPanel(ResourcePool pool);

    public abstract <V> ResourcePool<V> createResourcePool();
    
    public abstract Class<V> getResourceClass();

    public abstract String getDisplayName();

    @Override
    public String toString() {
        return getDisplayName();
    }

}
