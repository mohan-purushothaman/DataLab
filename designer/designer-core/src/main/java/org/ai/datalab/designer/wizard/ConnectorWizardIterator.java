/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.designer.wizard;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import javax.swing.JComponent;
import javax.swing.event.ChangeListener;
import org.openide.WizardDescriptor;
import org.ai.datalab.core.Data;
import org.ai.datalab.core.executor.ExecutorType;
import org.ai.datalab.core.misc.SimpleData;
import org.ai.datalab.visual.impl.widget.DescriptiveExecutionUnit;



public final class ConnectorWizardIterator implements WizardDescriptor.Iterator<WizardDescriptor> {

    // Example of invoking this wizard:
    // @ActionID(category="...", id="...")
    // @ActionRegistration(displayName="...")
    // @ActionReference(path="Menu/...")
    // public static ActionListener run() {
    //     return new ActionListener() {
    //         @Override public void actionPerformed(ActionEvent e) {
    //             WizardDescriptor wiz = new WizardDescriptor(new ConnectorWizardIterator());
    //             // {0} will be replaced by WizardDescriptor.Panel.getComponent().getName()
    //             // {1} will be replaced by WizardDescriptor.Iterator.name()
    //             wiz.setTitleFormat(new MessageFormat("{0} ({1})"));
    //             wiz.setTitle("...dialog title...");
    //             if (DialogDisplayer.getDefault().notify(wiz) == WizardDescriptor.FINISH_OPTION) {
    //                 ...do something...
    //             }
    //         }
    //     };
    // }
    private int index;

    private List<WizardDescriptor.Panel<WizardDescriptor>> panels;

    private final ExecutorType type;
    private final Data sampleInput;
    private final DescriptiveExecutionUnit existingNode;
    private  WizardDescriptor wizardDescriptor;
    
    public ConnectorWizardIterator(ExecutorType type, Data sampleInput,DescriptiveExecutionUnit existingNode) {
        this.type = type;
        this.sampleInput = (sampleInput == null ? new SimpleData() : sampleInput);
        this.existingNode=existingNode;
    }

    public void setWizardDescriptor(WizardDescriptor wizardDescriptor) {
        this.wizardDescriptor = wizardDescriptor;
    }
    
    

    private List<WizardDescriptor.Panel<WizardDescriptor>> getPanels() {
        if (panels == null) {
            panels = new ArrayList<>();
            String[] steps = new String[3];
            steps[1] = "Connector Details";
            ConnectorWizardPanel2 panel2 = new ConnectorWizardPanel2(type, existingNode);
            panels.add(new ConnectorWizardPanel1(type, sampleInput, panel2, steps,existingNode));
            panels.add(panel2);
            ConfigurationWizardPanel wiz = new ConfigurationWizardPanel();

            panels.add(wiz);

            // Default step name to component name of panel.
            for (int i : new int[]{0, 2}) {
                Component c = panels.get(i).getComponent();
                steps[i] = c.getName();

                if (c instanceof JComponent) { // assume Swing components
                    JComponent jc = (JComponent) c;
                    jc.putClientProperty(WizardDescriptor.PROP_CONTENT_SELECTED_INDEX, i);
                    jc.putClientProperty(WizardDescriptor.PROP_CONTENT_DATA, steps);
                    jc.putClientProperty(WizardDescriptor.PROP_AUTO_WIZARD_STYLE, true);
                    jc.putClientProperty(WizardDescriptor.PROP_CONTENT_DISPLAYED, true);
                    jc.putClientProperty(WizardDescriptor.PROP_CONTENT_NUMBERED, true);
                }
            }
            if (type == ExecutorType.CONDITION) {
                index = 1;
            }
            if(existingNode!=null){
                index=1;
                panel2.setProvider( null, sampleInput, steps);
            }
        }
        return panels;
    }

    @Override
    public WizardDescriptor.Panel<WizardDescriptor> current() {
        return getPanels().get(index);
    }

    @Override
    public String name() {
        return index + 1 + ". from " + getPanels().size();
    }

    @Override
    public boolean hasNext() {
        return index < getPanels().size() - 1;
    }

    @Override
    public boolean hasPrevious() {
        return index > 0;
    }

    @Override
    public void nextPanel() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        index++;
    }

    @Override
    public void previousPanel() {
        if (!hasPrevious()) {
            throw new NoSuchElementException();
        }
        index--;
    }

    // If nothing unusual changes in the middle of the wizard, simply:
    @Override
    public void addChangeListener(ChangeListener l) {
    }

    @Override
    public void removeChangeListener(ChangeListener l) {
    }
    // If something changes dynamically (besides moving between panels), e.g.
    // the number of panels changes in response to user input, then use
    // ChangeSupport to implement add/removeChangeListener and call fireChange
    // when needed

   

    
    


    private DescriptiveExecutionUnit node;
    
     public DescriptiveExecutionUnit getNode() {
        return node;
    }

//    @Override
//    public void visualNodeChanged(DescriptiveExecutionUnit node) {
//
//        this.node = node;
//        if (getPanels().size() > 1) {
//            ((ConnectorWizardPanel2) getPanels().get(1)).fireVisualNodeChanged(node);
//            ((ConfigurationVisualPanel) getPanels().get(2).getComponent()).setProvider((DescriptiveExecutionUnit) node);
//            if (wizardDescriptor != null && node != null) {
//                wizardDescriptor.putProperty(WizardDescriptor.PROP_ERROR_MESSAGE, null);
//            }
//        }
//
//    }
//
//   
//
//    @Override
//    public void handleException(Throwable e) {
//        visualNodeChanged(null);
//        if (wizardDescriptor != null) {
//            wizardDescriptor.putProperty(WizardDescriptor.PROP_ERROR_MESSAGE, String.valueOf(e.getMessage()));
//        }
//        e.printStackTrace();
//    }

}
