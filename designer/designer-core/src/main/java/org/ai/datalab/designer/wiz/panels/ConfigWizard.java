/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.designer.wiz.panels;

import java.awt.Component;
import javax.swing.event.ChangeListener;
import org.openide.WizardDescriptor;
import org.openide.util.HelpCtx;
import org.ai.datalab.designer.wiz.ExecutorWizardIterator;

/**
 *
 * @author Mohan Purushothaman
 */
public class ConfigWizard extends WizardPanel implements WizardDescriptor.Panel<WizardDescriptor> {

    private ConfigVisualPanel configVisualPanel;

    public ConfigWizard(ExecutorWizardIterator iterator) {
        super(iterator);

    }

    @Override
    public Component getComponent() {

        if (configVisualPanel == null) {
            configVisualPanel = new ConfigVisualPanel(getIterator());
            configVisualPanel.putClientProperty(WizardDescriptor.PROP_CONTENT_SELECTED_INDEX, 3);
            configVisualPanel.putClientProperty(WizardDescriptor.PROP_CONTENT_DATA, getIterator().getPropContent());
            configVisualPanel.putClientProperty(WizardDescriptor.PROP_AUTO_WIZARD_STYLE, true);
            configVisualPanel.putClientProperty(WizardDescriptor.PROP_CONTENT_DISPLAYED, true);
            configVisualPanel.putClientProperty(WizardDescriptor.PROP_CONTENT_NUMBERED, true);
        }

        configVisualPanel.update(getIterator());

        return configVisualPanel;
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
