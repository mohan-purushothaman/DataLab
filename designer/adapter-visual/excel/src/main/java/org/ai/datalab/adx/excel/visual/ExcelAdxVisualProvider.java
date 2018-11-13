/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.adx.excel.visual;

import java.util.EnumSet;
import java.util.Set;
import org.openide.util.lookup.ServiceProvider;
import org.ai.datalab.core.Data;
import org.ai.datalab.core.adx.misc.MappingHelper;
import org.ai.datalab.core.executor.ExecutorType;
import org.ai.datalab.designer.panels.VisualNodeProvider;
import org.ai.datalab.designer.panels.VisualNodeValidator;
import org.ai.datalab.visual.impl.widget.DescriptiveExecutionUnit;

/**
 *
 * @author Mohan Purushothaman
 */
@ServiceProvider(service = VisualNodeProvider.class)
public class ExcelAdxVisualProvider extends VisualNodeProvider {  

    @Override
    public VisualNodeValidator createProviderPanel(ExecutorType type, Data sampleInput,MappingHelper inputMapping) {
        return new ExcelConnectorPanel(null,type, sampleInput);
    }

   @Override
    public String getProviderName() {
        return "Excel Connector";
    }
    
     @Override
    public Set<ExecutorType> getSupportedTypes() {
        return EnumSet.of(ExecutorType.READER, ExecutorType.WRITER);
    }

    @Override
    public VisualNodeValidator createEditPanel(DescriptiveExecutionUnit unit, Data sampleInput,MappingHelper inputMapping) {
        return new ExcelConnectorPanel(unit,unit.getProvidingType(), sampleInput);
    }
}
