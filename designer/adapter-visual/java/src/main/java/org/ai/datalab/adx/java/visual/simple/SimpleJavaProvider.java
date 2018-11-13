/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.adx.java.visual.simple;

import org.ai.datalab.adx.java.core.simple.*;
import java.util.EnumSet;
import java.util.Set;
import org.ai.datalab.adx.java.JavaCodeGenerator;
import org.ai.datalab.adx.java.core.JavaExecutorProvider;
import org.ai.datalab.adx.java.visual.JavaExecutionUnit;
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
public class SimpleJavaProvider extends VisualNodeProvider {

    @Override
    public VisualNodeValidator createProviderPanel(ExecutorType type, Data sampleInput,MappingHelper inputMapping) {

        return new SimpleJavaConnectorPanel(getGenerator(type, sampleInput,inputMapping), type, sampleInput);
    }

    @Override
    public VisualNodeValidator createEditPanel(DescriptiveExecutionUnit unit, Data sampleInput,MappingHelper inputMapping) {
        SimpleJavaCodeGenerator gen = (SimpleJavaCodeGenerator) ((JavaExecutorProvider) unit.getExecutorProvider()).getCodeGenerator();
        return new SimpleJavaConnectorPanel(gen, unit.getProvidingType(), sampleInput);
    }

    @Override
    public String getProviderName() {
        return "Simple Java Adapter";
    }

    @Override
    public Set<ExecutorType> getSupportedTypes() {
        return EnumSet.of(ExecutorType.PROCESSOR, ExecutorType.WRITER, ExecutorType.CONDITION);
    }

    private SimpleJavaCodeGenerator getGenerator(ExecutorType type, Data sampleInput,MappingHelper inputMapping) {
        switch (type) {
            case READER:
                return null;
            case PROCESSOR:
                return new SimpleJavaProcessorCodeGenerator(inputMapping);
            case CONDITION:
                return new SimpleJavaConditionCodeGenerator(inputMapping);
            case WRITER:
                return new SimpleJavaWriterCodeGenerator(inputMapping);
        }
        throw new UnsupportedOperationException(type + " not available");
    }

}
