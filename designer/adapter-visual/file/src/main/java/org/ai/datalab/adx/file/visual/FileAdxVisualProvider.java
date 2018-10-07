/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.adx.file.visual;

import java.util.EnumSet;
import java.util.Set;
import org.openide.util.lookup.ServiceProvider;
import org.ai.datalab.core.Data;
import org.ai.datalab.core.executor.ExecutorType;
import org.ai.datalab.designer.panels.VisualNodeProvider;
import org.ai.datalab.designer.panels.VisualNodeValidator;

/**
 *
 * @author Mohan Purushothaman
 */
@ServiceProvider(service = VisualNodeProvider.class)
public class FileAdxVisualProvider extends VisualNodeProvider {

    @Override
    public VisualNodeValidator createProviderPanel(ExecutorType type, Data sampleInput) {
        return new FileConnectorPanel(type, sampleInput);
    }

    @Override
    public String getProviderName() {
        return "File Connector";
    }
    
     @Override
    public Set<ExecutorType> getSupportedTypes() {
        return EnumSet.of(ExecutorType.READER, ExecutorType.WRITER);
    }

}
