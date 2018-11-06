/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.designer.wiz.panels;

import org.openide.WizardDescriptor;
import org.openide.util.HelpCtx;
import org.ai.datalab.designer.panels.VisualNodeProvider;
import org.ai.datalab.designer.wiz.ExecutorWizardIterator;

/**
 *
 * @author Mohan Purushothaman
 */
public class TypeFilterWizard extends WizardPanel implements WizardDescriptor.Panel<WizardDescriptor> {

    private TypeFilterVisualPanel typeFilterVisualPanel;

    public TypeFilterWizard(ExecutorWizardIterator iterator) {
        super(iterator);

    }

    @Override
    public TypeFilterVisualPanel getComponent() {
        if (typeFilterVisualPanel == null) {
            typeFilterVisualPanel = new TypeFilterVisualPanel(getIterator());
            typeFilterVisualPanel.putClientProperty(WizardDescriptor.PROP_CONTENT_SELECTED_INDEX, 0);
            typeFilterVisualPanel.putClientProperty(WizardDescriptor.PROP_CONTENT_DATA, getIterator().getPropContent());
            typeFilterVisualPanel.putClientProperty(WizardDescriptor.PROP_AUTO_WIZARD_STYLE, true);
            typeFilterVisualPanel.putClientProperty(WizardDescriptor.PROP_CONTENT_DISPLAYED, true);
            typeFilterVisualPanel.putClientProperty(WizardDescriptor.PROP_CONTENT_NUMBERED, true);
        }
        return typeFilterVisualPanel;
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
        // TODO add expected types , like initally ONLY READER ACCEPTED
        return getSelectedConnector() != null;
    }

    public VisualNodeProvider getSelectedConnector() {
        return getComponent().getSelectedConnector();
    }

}
