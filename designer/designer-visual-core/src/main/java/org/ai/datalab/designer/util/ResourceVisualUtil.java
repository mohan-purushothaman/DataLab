/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.designer.util;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import org.ai.datalab.core.resource.ResourceFactory;
import org.ai.datalab.core.resource.ResourcePool;
import org.ai.datalab.core.resource.ResourcePoolQualifier;

/**
 *
 * @author Mohan Purushothaman
 */
public class ResourceVisualUtil {

    public static <V> ComboBoxModel<? extends ResourcePool> getResourceComboBox(Class<V> clazz,ResourcePoolQualifier<V> qualifier) {
        return new DefaultComboBoxModel(ResourceFactory.findResourcePools(clazz,qualifier).toArray());
    }
}
