/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.designer.wiz;

import java.text.MessageFormat;
import org.openide.DialogDisplayer;
import org.openide.WizardDescriptor;
import org.openide.util.lookup.ServiceProvider;
import org.ai.datalab.core.adx.misc.MappingHelper;
import org.ai.datalab.core.executor.ExecutorType;
import org.ai.datalab.designer.core.ConnectorWizardIteratorInterface;
import org.ai.datalab.visual.impl.widget.DescriptiveExecutionUnit;

/**
 *
 * @author Mohan Purushothaman
 */
@ServiceProvider(service = ConnectorWizardIteratorInterface.class)
public class ExecutorWizardProvider implements ConnectorWizardIteratorInterface {

    @Override
    public DescriptiveExecutionUnit getVisualNode(ExecutorType type, MappingHelper mapping, DescriptiveExecutionUnit existingNode) {
        ExecutorWizardIterator connectorWizardIterator = new ExecutorWizardIterator(type, mapping, existingNode);
        WizardDescriptor latestWizard = new WizardDescriptor(connectorWizardIterator);
        connectorWizardIterator.setWizardDescriptor(latestWizard);
        // {0} will be replaced by WizardDescriptor.Panel.getComponent().getName()
        // {1} will be replaced by WizardDescriptor.Iterator.name()
        latestWizard.setTitleFormat(new MessageFormat("{0} ({1})"));
        latestWizard.setTitle("Select Connector");
        if (DialogDisplayer.getDefault().notify(latestWizard) == WizardDescriptor.FINISH_OPTION) {
            return connectorWizardIterator.getSelectedNode();
        }
        return null;
    }

    @Override
    public DescriptiveExecutionUnit getVisualNode(ExecutorType type, MappingHelper mapping) {
        return getVisualNode(type, mapping, null);
    }

}
