/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.adx.java.visual;

import java.util.EnumSet;
import java.util.Set;
import org.openide.util.lookup.ServiceProvider;
import org.ai.datalab.adx.java.JavaCodeGenerator;
import org.ai.datalab.adx.java.core.JavaConditionCodeGenerator;
import org.ai.datalab.adx.java.core.JavaProcessorCodeGenerator;
import org.ai.datalab.adx.java.core.JavaReaderCodeGenerator;
import org.ai.datalab.adx.java.core.JavaWriterCodeGenerator;
import org.ai.datalab.core.Data;
import org.ai.datalab.core.executor.ExecutorType;
import org.ai.datalab.designer.panels.VisualNodeProvider;
import org.ai.datalab.designer.panels.VisualNodeValidator;
import org.ai.datalab.visual.impl.widget.DescriptiveExecutionUnit;

/**
 *
 * @author Mohan Purushothaman
 */
@ServiceProvider(service = VisualNodeProvider.class)
public class JavaVisualProvider extends VisualNodeProvider {

    @Override
    public VisualNodeValidator createProviderPanel(ExecutorType type, Data sampleInput) {

        return new JavaConnectorPanel(getGenerator(type), type, sampleInput);
    }
    
    @Override
    public VisualNodeValidator createEditPanel(DescriptiveExecutionUnit unit, Data sampleInput) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getProviderName() {
        return "Java Adapter";
    }

    @Override
    public Set<ExecutorType> getSupportedTypes() {
        return EnumSet.of(ExecutorType.READER,ExecutorType.PROCESSOR,ExecutorType.WRITER,ExecutorType.CONDITION);
    }

    private JavaCodeGenerator getGenerator(ExecutorType type) {
        switch (type) {
            case READER:
                return new JavaReaderCodeGenerator();
            case PROCESSOR:
                return new JavaProcessorCodeGenerator();
            case CONDITION:
                return new JavaConditionCodeGenerator();
            case WRITER:
                return new JavaWriterCodeGenerator();
        }
        throw new UnsupportedOperationException(type + " not available");
    }

    

}
