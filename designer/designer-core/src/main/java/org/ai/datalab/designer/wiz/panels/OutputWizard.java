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

/**
 *
 * @author Mohan Purushothaman
 */
public class OutputWizard extends WizardPanel implements WizardDescriptor.Panel<WizardDescriptor> {

    public final OutputVisualPanel outputVisualPanel;

    public OutputWizard(ExecutorWizardIterator iterator) {
        super(iterator);
        this.outputVisualPanel = new OutputVisualPanel(iterator);
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

}
