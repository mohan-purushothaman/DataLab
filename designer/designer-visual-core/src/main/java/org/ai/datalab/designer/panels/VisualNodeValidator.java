/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.designer.panels;

import javax.swing.JPanel;
import org.netbeans.api.progress.ProgressHandle;
import org.openide.WizardValidationException;
import org.ai.datalab.visual.impl.widget.DescriptiveExecutionUnit;

/**
 *
 * @author Mohan Purushothaman
 */
public abstract class VisualNodeValidator extends JPanel {

    /**
     * Called synchronously from UI thread when Next of Finish buttons clicked.
     * It allows to lock user input to assure official data for background
     * validation.
     */
    public abstract String prepareValidation();

    /**
     * Is called in separate thread when Next of Finish buttons are clicked and
     * allows deeper check to find out that panel is in valid state and it is ok
     * to leave it.
     *
     * @return
     * @throws Exception when validation fails
     */
    public abstract DescriptiveExecutionUnit validateConnector(ProgressHandle handle) throws Exception;

    /**
     * Called synchronously from UI thread when the background validation is
     * finished (even when throwing validation exception). It allows to enable
     * user input locked in prepareValidation() method.
     */
    public abstract void finishValidation();
}
