/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.designer.core;

import org.ai.datalab.core.adx.misc.MappingHelper;
import org.ai.datalab.core.executor.ExecutorType;
import org.ai.datalab.visual.impl.widget.DescriptiveExecutionUnit;

/**
 *
 * @author Mohan Purushothaman
 */
public interface ConnectorWizardIteratorInterface {
    public DescriptiveExecutionUnit getVisualNode(ExecutorType type, MappingHelper mapping,DescriptiveExecutionUnit executionUnit);

    public DescriptiveExecutionUnit getVisualNode(ExecutorType executorType, MappingHelper mapping);
}
