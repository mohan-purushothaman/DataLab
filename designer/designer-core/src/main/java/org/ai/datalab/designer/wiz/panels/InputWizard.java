/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.designer.wiz.panels;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.progress.ProgressHandleFactory;
import org.openide.WizardDescriptor;
import org.openide.WizardValidationException;
import org.openide.util.Cancellable;
import org.openide.util.Exceptions;
import org.openide.util.HelpCtx;
import org.ai.datalab.designer.panels.VisualNodeProvider;
import org.ai.datalab.designer.panels.VisualNodeValidator;
import org.ai.datalab.designer.wiz.ExecutorWizardIterator;
import org.ai.datalab.visual.impl.widget.DescriptiveExecutionUnit;
import org.apache.commons.lang.StringUtils;
import org.openide.util.Lookup;

/**
 *
 * @author Mohan Purushothaman
 */
public class InputWizard extends WizardPanel implements WizardDescriptor.ExtendedAsynchronousValidatingPanel<WizardDescriptor> {

    private JPanel fullPanel;

    private JPanel progressPanel;

    private VisualNodeProvider currentProvider;

    private boolean showEditPanel;

    public InputWizard(ExecutorWizardIterator iterator) {
        super(iterator);
    }

    @Override
    public Component getComponent() {
        ExecutorWizardIterator iterator = getIterator();
        if (currentProvider == null) {

            if (iterator.getExistingNode() != null) {
                setProvider(null);
            } else {
                return null;
            }
        }
        if (fullPanel != null) {
            return fullPanel;
        }
        VisualNodeValidator panel;
        if (showEditPanel) {
            panel = currentProvider.createEditPanel(iterator.getExistingNode(), iterator.getSampleInput());
        } else {
            panel = currentProvider.createProviderPanel(getIterator().getType(), iterator.getSampleInput());
        }

        fullPanel = new JPanel(new BorderLayout());
        fullPanel.add(panel, BorderLayout.CENTER);
        progressPanel = new JPanel(new BorderLayout(20, 0));
        progressPanel.setVisible(false);

        fullPanel.add(progressPanel, BorderLayout.SOUTH);

        fullPanel.putClientProperty(WizardDescriptor.PROP_CONTENT_SELECTED_INDEX, 1);
        //displayNames[1] = provider.getProviderName();
        fullPanel.putClientProperty(WizardDescriptor.PROP_CONTENT_DATA, iterator.getPropContent());
        fullPanel.putClientProperty(WizardDescriptor.PROP_AUTO_WIZARD_STYLE, true);
        fullPanel.putClientProperty(WizardDescriptor.PROP_CONTENT_DISPLAYED, true);
        fullPanel.putClientProperty(WizardDescriptor.PROP_CONTENT_NUMBERED, true);

        return fullPanel;
    }

    public void setProvider(VisualNodeProvider provider) {

        ExecutorWizardIterator iterator = getIterator();
        //TODO existing support
        DescriptiveExecutionUnit existingNode = iterator.getExistingNode();

        VisualNodeProvider editProvider = getNearestVisualProvider(existingNode);

        if (existingNode != null && (provider == null || editProvider.getClass() == provider.getClass())) {
            currentProvider = editProvider;
            showEditPanel = true;
        } else {
            currentProvider = provider;
            showEditPanel = false;
        }
        fullPanel = null;
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

    private String validateString;

    private VisualNodeValidator getPanel() {
        for (Component c : fullPanel.getComponents()) {
            if (c instanceof VisualNodeValidator) {
                return (VisualNodeValidator) c;
            }
        }
        return null;
    }

    @Override
    public void prepareValidation() {
        validateString = getPanel().prepareValidation();
    }

    @Override
    public void validate() throws WizardValidationException {
        try {
            Thread validateThread = Thread.currentThread();

            ProgressHandle handle = ProgressHandle.createHandle(validateString, new Cancellable() {
                @Override
                public boolean cancel() {
                    validateThread.interrupt();
                    return true;
                }
            });

            JComponent p = ProgressHandleFactory.createProgressComponent(handle);
            JButton cancelButton = new JButton("Cancel");
            cancelButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    validateThread.interrupt();
                }
            });
            progressPanel.add(ProgressHandleFactory.createMainLabelComponent(handle), BorderLayout.WEST);
            progressPanel.add(p, BorderLayout.CENTER);
            progressPanel.add(cancelButton, BorderLayout.EAST);
            progressPanel.setVisible(true);
            handle.start();
            try {
                DescriptiveExecutionUnit connector = getPanel().validateConnector(handle);

                if (isInnerClass(connector)) {
                    throw new WizardValidationException(getPanel(), "inner class not supported", "inner class not supported");
                }
                getIterator().setSelectedNode(connector);
                if (connector == null) {
                    throw new WizardValidationException(getPanel(), "details missing", "details missing");
                }
            } catch (Exception ex) {
                Exceptions.printStackTrace(Exceptions.attachSeverity(ex, Level.INFO));
                getIterator().setSelectedNode(null);
                throw new WizardValidationException(getPanel(), ex.getMessage(), ex.getLocalizedMessage());
            } finally {
                handle.finish();
            }
        } finally {
            progressPanel.removeAll();
            progressPanel.setVisible(false);
        }
    }

    @Override
    public void finishValidation() {
        getPanel().finishValidation();
    }

    private boolean isInnerClass(DescriptiveExecutionUnit connector) {

        if (connector != null) {
            Class clazz = connector.getClass();
            return clazz.getEnclosingClass() != null;
        }
        return false;
    }

    private VisualNodeProvider getNearestVisualProvider(DescriptiveExecutionUnit existingNode) {
        if (existingNode == null) {
            return null;
        }
        VisualNodeProvider nearestProvider = null;
        int minDifference = Integer.MAX_VALUE;
        for (VisualNodeProvider n : Lookup.getDefault().lookupAll(VisualNodeProvider.class)) {
            int difference = getDifference(n, existingNode);
            if (difference < minDifference) {
                minDifference = difference;
                nearestProvider = n;
                if (difference == 0) {
                    return n;
                }
            }
        }
        return nearestProvider;
    }

    private int getDifference(VisualNodeProvider n, DescriptiveExecutionUnit existingNode) {
        String name1 = n.getClass().getPackage().getName();
        String name2 = existingNode.getClass().getPackage().getName();
        if (name2.length() < name1.length()) {
            String tmp = name1;
            name1 = name2;
            name2 = tmp;
        }
        return StringUtils.difference(name1, name2).length();

    }

}
