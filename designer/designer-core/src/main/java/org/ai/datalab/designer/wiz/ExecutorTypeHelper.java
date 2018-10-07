/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.designer.wiz;

import java.util.Collection;
import java.util.LinkedList;
import org.openide.util.Lookup;
import org.ai.datalab.core.executor.ExecutorType;
import org.ai.datalab.designer.panels.VisualNodeProvider;

/**
 *
 * @author Mohan Purushothaman
 */
public class ExecutorTypeHelper {

    public static Collection<VisualNodeProvider> getProvider(ExecutorType executorType) {        
        Collection<VisualNodeProvider> values = new LinkedList<>();
        for (VisualNodeProvider visualNodeProvider : Lookup.getDefault().lookupAll(VisualNodeProvider.class)) {
            if (visualNodeProvider.getSupportedTypes().contains(executorType)) {
                values.add(visualNodeProvider);
            }
        }
        return values;
    }
}
