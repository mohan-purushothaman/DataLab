/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.adx.web.visual;

import java.util.EnumSet;
import java.util.Set;
import org.ai.datalab.core.Data;
import org.ai.datalab.core.adx.misc.MappingHelper;
import org.ai.datalab.core.executor.ExecutorType;
import org.ai.datalab.designer.panels.VisualNodeProvider;
import org.ai.datalab.designer.panels.VisualNodeValidator;
import org.ai.datalab.visual.impl.widget.DescriptiveExecutionUnit;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Mohan Purushothaman
 */
@ServiceProvider(service = VisualNodeProvider.class)
public class HttpVisualProvider extends VisualNodeProvider {

    @Override
    public VisualNodeValidator createProviderPanel(ExecutorType type, Data sampleInput,MappingHelper inputMapping) {
        return new Http_DesignerPanel(null,type, sampleInput);
    }
    
    @Override
    public VisualNodeValidator createEditPanel(DescriptiveExecutionUnit unit, Data sampleInput,MappingHelper inputMapping) {
        return new Http_DesignerPanel(unit,unit.getProvidingType(), sampleInput);
    }

    @Override
    public String getProviderName() {
        return "HTTP/HTTPS connector";
    }

    @Override
    public Set<ExecutorType> getSupportedTypes() {
        return EnumSet.of(ExecutorType.PROCESSOR,ExecutorType.WRITER);
    }

    
    
    
    
}
