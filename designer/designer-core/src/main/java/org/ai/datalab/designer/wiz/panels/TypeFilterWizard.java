/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.designer.wiz.panels;

import java.awt.Component;
import org.openide.WizardDescriptor;
import org.openide.util.HelpCtx;
import org.ai.datalab.core.executor.ExecutorType;
import org.ai.datalab.designer.panels.VisualNodeProvider;
import org.ai.datalab.designer.wiz.ExecutorWizardIterator;

/**
 *
 * @author Mohan Purushothaman
 */
public class TypeFilterWizard extends WizardPanel implements WizardDescriptor.Panel<WizardDescriptor> {

    private final TypeFilterVisualPanel typeFilterVisualPanel;

    public TypeFilterWizard(ExecutorWizardIterator iterator) {
        super(iterator);
        typeFilterVisualPanel = new TypeFilterVisualPanel(iterator);
    }

    @Override
    public Component getComponent() {
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
        return typeFilterVisualPanel.getSelectedConnector();
    }
    
    public ExecutorType getExecutorType(){
        return typeFilterVisualPanel.getExecutorType();
    }

}
