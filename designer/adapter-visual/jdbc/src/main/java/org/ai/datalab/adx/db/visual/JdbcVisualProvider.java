/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.adx.db.visual;

import java.util.EnumSet;
import java.util.Set;
import org.openide.util.lookup.ServiceProvider;
import org.ai.datalab.core.Data;
import org.ai.datalab.core.executor.ExecutorType;
import org.ai.datalab.adx.db.visual.panels.BasicSQL_DesignerPanel;
import org.ai.datalab.designer.panels.VisualNodeProvider;
import org.ai.datalab.designer.panels.VisualNodeValidator;
import org.ai.datalab.visual.impl.widget.DescriptiveExecutionUnit;

/**
 *
 * @author Mohan Purushothaman
 */
@ServiceProvider(service = VisualNodeProvider.class)
public class JdbcVisualProvider extends VisualNodeProvider {

    public JdbcVisualProvider() throws Exception {

    }
    
      @Override
    public VisualNodeValidator createEditPanel(DescriptiveExecutionUnit unit, Data sampleInput) {
        return new BasicSQL_DesignerPanel(unit,unit.getProvidingType(), sampleInput);
    }

    @Override
    public VisualNodeValidator createProviderPanel(ExecutorType type, Data sampleInput) {
        return new BasicSQL_DesignerPanel(null,type, sampleInput);
    }

    @Override
    public String getProviderName() {
        return "JDBC Connector";
    }

    @Override
    public Set<ExecutorType> getSupportedTypes() {
        return EnumSet.of(ExecutorType.READER, ExecutorType.PROCESSOR, ExecutorType.WRITER);
    }

  

}
