/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.designer.wiz.panels;

import java.awt.Component;
import org.openide.WizardDescriptor;
import org.openide.util.HelpCtx;
import org.ai.datalab.designer.wiz.ExecutorWizardIterator;
import org.openide.WizardValidationException;

/**
 *
 * @author Mohan Purushothaman
 */
public class OutputWizard extends WizardPanel implements WizardDescriptor.ValidatingPanel<WizardDescriptor> {

    private final OutputVisualPanel outputVisualPanel;

    public OutputWizard(ExecutorWizardIterator iterator) {
        super(iterator);
        outputVisualPanel = new OutputVisualPanel(iterator);
        outputVisualPanel.putClientProperty(WizardDescriptor.PROP_CONTENT_SELECTED_INDEX, 2);
        outputVisualPanel.putClientProperty(WizardDescriptor.PROP_CONTENT_DATA, getIterator().getPropContent());
        outputVisualPanel.putClientProperty(WizardDescriptor.PROP_AUTO_WIZARD_STYLE, true);
        outputVisualPanel.putClientProperty(WizardDescriptor.PROP_CONTENT_DISPLAYED, true);
        outputVisualPanel.putClientProperty(WizardDescriptor.PROP_CONTENT_NUMBERED, true);
    }

    @Override
    public Component getComponent() {

        outputVisualPanel.refresh();
        return outputVisualPanel;
    }

    @Override
    public HelpCtx getHelp() {
        return HelpCtx.DEFAULT_HELP;
    }

    @Override
    public void readSettings(WizardDescriptor settings) {

    }

    @Override
    public void storeSettings(WizardDescriptor settings) {

    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void validate() throws WizardValidationException {
        if (getIterator().getExistingNode() != null) {
            outputVisualPanel.validateMapping();
        }
    }

}
