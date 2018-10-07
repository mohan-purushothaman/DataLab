/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.designer.panels;

import java.awt.Component;
import java.util.EnumSet;
import java.util.Set;
import javax.swing.JPanel;
import org.ai.datalab.core.Data;
import org.ai.datalab.core.builder.ExecutionUnit;
import org.ai.datalab.core.executor.ExecutorType;

/**
 *
 * @author Mohan Purushothaman
 */
public abstract class VisualNodeProvider {
    
    public static final String VISUAL_NODE_PROVIDER_PATH="DataLab/VisualProvider";
    
    public abstract VisualNodeValidator createProviderPanel(ExecutorType type, Data sampleInput);

    public VisualNodeValidator createEditPanel(ExecutionUnit unit, Data sampleInput) {
        return createProviderPanel(unit.getProvidingType(), sampleInput);
    }

    public abstract String getProviderName();

    public String getProviderDescription() {
        return getProviderName();
    }

    @Override
    public String toString() {
        return getProviderName();
    }

    public Set<ExecutorType> getSupportedTypes() {
        return EnumSet.noneOf(ExecutorType.class);
    }

}
