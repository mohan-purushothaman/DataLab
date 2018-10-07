/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.designer.core;

import org.ai.datalab.core.Data;
import org.ai.datalab.core.executor.ExecutorType;
import org.ai.datalab.visual.impl.widget.DescriptiveExecutionUnit;

/**
 *
 * @author Mohan Purushothaman
 */
public interface ConnectorWizardIteratorInterface {
    public DescriptiveExecutionUnit getVisualNode(ExecutorType type, Data sampleInput,DescriptiveExecutionUnit executionUnit);

    public DescriptiveExecutionUnit getVisualNode(ExecutorType executorType, Data simpleData);
}
