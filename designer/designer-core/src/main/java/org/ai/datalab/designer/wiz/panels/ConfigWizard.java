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

    private final ConfigVisualPanel configVisualPanel;

    public ConfigWizard(ExecutorWizardIterator iterator) {
        super(iterator);
        this.configVisualPanel = new ConfigVisualPanel(iterator);
    }

    @Override
    public Component getComponent() {
        update();
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

    public void update(){
        configVisualPanel.update(getIterator());
    }
    
    @Override
    public boolean isValid() {
        return true;
    }

}
