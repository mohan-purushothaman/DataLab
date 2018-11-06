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

    public OutputVisualPanel outputVisualPanel;

    public OutputWizard(ExecutorWizardIterator iterator) {
        super(iterator);
    }

    @Override
    public Component getComponent() {
        
        if(outputVisualPanel==null){
            outputVisualPanel = new OutputVisualPanel(getIterator());
            outputVisualPanel.putClientProperty(WizardDescriptor.PROP_CONTENT_SELECTED_INDEX, 0);
            outputVisualPanel.putClientProperty(WizardDescriptor.PROP_CONTENT_DATA, getIterator().getPropContent());
            outputVisualPanel.putClientProperty(WizardDescriptor.PROP_AUTO_WIZARD_STYLE, true);
            outputVisualPanel.putClientProperty(WizardDescriptor.PROP_CONTENT_DISPLAYED, true);
            outputVisualPanel.putClientProperty(WizardDescriptor.PROP_CONTENT_NUMBERED, true);
        }
        
        
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
