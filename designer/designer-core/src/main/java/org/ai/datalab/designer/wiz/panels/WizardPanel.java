/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.designer.wiz.panels;

import javax.swing.JPanel;
import javax.swing.event.ChangeListener;
import org.openide.util.ChangeSupport;
import org.ai.datalab.designer.wiz.ExecutorWizardIterator;

/**
 *
 * @author Mohan Purushothaman
 */
public abstract class WizardPanel {
    
    private final ExecutorWizardIterator iterator;

    public WizardPanel(ExecutorWizardIterator iterator) {
        this.iterator = iterator;
    }
    
    private final ChangeSupport changeSupport = new ChangeSupport(this);

    public void addChangeListener(ChangeListener l) {
        changeSupport.addChangeListener(l);
    }

    public void removeChangeListener(ChangeListener l) {
        changeSupport.removeChangeListener(l);
    }

    public ChangeSupport getChangeSupport() {
        return changeSupport;
    }
    
    

    public ExecutorWizardIterator getIterator() {
        return iterator;
    }
    
    
    
}
