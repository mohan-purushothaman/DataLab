/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.designer.wizard;


import javax.swing.JComponent;
import javax.swing.event.ChangeListener;
import org.apache.commons.lang.StringUtils;
import org.openide.WizardDescriptor;
import org.openide.util.ChangeSupport;
import org.openide.util.HelpCtx;
import org.openide.util.Lookup;
import org.ai.datalab.core.Data;
import org.ai.datalab.core.builder.ExecutionUnit;
import org.ai.datalab.core.executor.ExecutorType;
import org.ai.datalab.designer.panels.VisualNodeProvider;
import org.ai.datalab.visual.impl.widget.DescriptiveExecutionUnit;

public class ConnectorWizardPanel2 implements WizardDescriptor.Panel<WizardDescriptor> {

    /**
     * The visual component that displays this panel. If you need to access the
     * component from this class, just use getComponent().
     */
    private JComponent component;

    private final ExecutorType requiredType;

    private final ChangeSupport changeSupport = new ChangeSupport(this);

    private final DescriptiveExecutionUnit existingNode;

    public ConnectorWizardPanel2(ExecutorType requiredType, DescriptiveExecutionUnit existingNode) {
        this.requiredType = requiredType;
        this.existingNode = existingNode;
    }

    // Get the visual component for the panel. In this template, the component
    // is kept separate. This can be more efficient: if the wizard is created
    // but never displayed, or not all panels are displayed, it is better to
    // create only those which really need to be visible.
    @Override
    public JComponent getComponent() {
        assert component != null;
        return component;
    }

    public void setProvider(VisualNodeProvider provider, Data sampleInput, String[] displayNames) {
        VisualNodeProvider editProvider = getNearestVisualProvider(existingNode);
        if (existingNode != null && (provider == null || editProvider.getClass() == provider.getClass())) {
            provider=editProvider;
            this.component = editProvider.createEditPanel(existingNode, sampleInput);
        } else {
            this.component = provider.createProviderPanel(requiredType, sampleInput);
        }
        if (component instanceof JComponent) { // assume Swing components
            JComponent jc = (JComponent) component;

            jc.putClientProperty(WizardDescriptor.PROP_CONTENT_SELECTED_INDEX, 1);
            displayNames[1] = provider.getProviderName();
            jc.putClientProperty(WizardDescriptor.PROP_CONTENT_DATA, displayNames);
            jc.putClientProperty(WizardDescriptor.PROP_AUTO_WIZARD_STYLE, true);
            jc.putClientProperty(WizardDescriptor.PROP_CONTENT_DISPLAYED, true);
            jc.putClientProperty(WizardDescriptor.PROP_CONTENT_NUMBERED, true);
        }
    }

    @Override
    public HelpCtx getHelp() {
        // Show no Help button for this panel:
        return HelpCtx.DEFAULT_HELP;
        // If you have context help:
        // return new HelpCtx("help.key.here");
    }

    @Override
    public boolean isValid() {

        // If it is always OK to press Next or Finish, then:
        return node != null;
        // If it depends on some condition (form filled out...) and
        // this condition changes (last form field filled in...) then
        // use ChangeSupport to implement add/removeChangeListener below.
        // WizardDescriptor.ERROR/WARNING/INFORMATION_MESSAGE will also be useful.
    }

    @Override
    public void addChangeListener(ChangeListener l) {
        changeSupport.addChangeListener(l);
    }

    @Override
    public void removeChangeListener(ChangeListener l) {
        changeSupport.removeChangeListener(l);
    }

    private ExecutionUnit node;

    public void fireVisualNodeChanged(ExecutionUnit node) {
        this.node = node;
        changeSupport.fireChange();
    }

    @Override
    public void readSettings(WizardDescriptor wiz) {
        // use wiz.getProperty to retrieve previous panel state
    }

    @Override
    public void storeSettings(WizardDescriptor wiz) {
        // use wiz.putProperty to remember current panel state
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
