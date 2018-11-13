/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.designer.panels;

import java.util.EnumSet;
import java.util.Set;
import org.ai.datalab.core.Data;
import org.ai.datalab.core.adx.misc.MappingHelper;
import org.ai.datalab.core.executor.ExecutorType;
import org.ai.datalab.visual.impl.widget.DescriptiveExecutionUnit;

/**
 *
 * @author Mohan Purushothaman
 */
public abstract class VisualNodeProvider {
    
    public static final String VISUAL_NODE_PROVIDER_PATH="DataLab/VisualProvider";
    
    public abstract VisualNodeValidator createProviderPanel(ExecutorType type, Data sampleInput,MappingHelper inputMapping);

    public abstract VisualNodeValidator createEditPanel(DescriptiveExecutionUnit unit, Data sampleInput,MappingHelper inputMapping);

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
