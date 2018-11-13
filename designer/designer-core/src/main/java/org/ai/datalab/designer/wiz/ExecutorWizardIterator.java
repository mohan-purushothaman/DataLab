/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.designer.wiz;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import javax.swing.JComponent;
import org.ai.datalab.designer.wiz.panels.WIZARD_PANEL;
import javax.swing.event.ChangeListener;
import org.netbeans.api.progress.ProgressHandle;
import org.openide.WizardDescriptor;
import org.ai.datalab.core.Data;
import org.ai.datalab.core.adx.misc.MappingHelper;
import org.ai.datalab.core.executor.ExecutorType;
import org.ai.datalab.core.misc.SimpleData;
import org.ai.datalab.designer.wiz.panels.ConfigWizard;
import org.ai.datalab.designer.wiz.panels.InputWizard;
import org.ai.datalab.designer.wiz.panels.OutputWizard;
import org.ai.datalab.designer.wiz.panels.TypeFilterWizard;
import static org.ai.datalab.designer.wiz.panels.WIZARD_PANEL.*;
import org.ai.datalab.visual.impl.widget.DescriptiveExecutionUnit;

/**
 *
 * @author Mohan Purushothaman
 */
public class ExecutorWizardIterator implements WizardDescriptor.ProgressInstantiatingIterator<WizardDescriptor> {

    private WIZARD_PANEL currentPanel;

    private final ExecutorType type;
    private final MappingHelper mapping;
    private final Data sampleInput;
    private final DescriptiveExecutionUnit existingNode;
    private WizardDescriptor wizardDescriptor;

    public ExecutorWizardIterator(ExecutorType type, MappingHelper mapping, DescriptiveExecutionUnit existingNode) {
        this.type = type;
        this.mapping=mapping;
        this.sampleInput = (mapping == null ? new SimpleData() : mapping.getSampleData());
        this.existingNode = existingNode;
        currentPanel = existingNode==null?TYPE_FILTER_PANEL:INPUT_PANEL;
        initPanel();
    }

    public void setWizardDescriptor(WizardDescriptor wizardDescriptor) {
        this.wizardDescriptor = wizardDescriptor;
    }

    @Override
    public WizardDescriptor.Panel<WizardDescriptor> current() {
        return getPanel(currentPanel);
    }

    @Override
    public String name() {
        return currentPanel.getName();
    }

    @Override
    public boolean hasNext() {
        return currentPanel.ordinal() < WIZARD_PANEL.values().length - 1;
    }

    @Override
    public boolean hasPrevious() {
        return currentPanel.ordinal() > 0;
    }

    @Override
    public void nextPanel() {
        int increment = 1;

        if (currentPanel == INPUT_PANEL && (type == ExecutorType.WRITER || type == ExecutorType.CONDITION)) {
            increment++;
        }

        currentPanel = WIZARD_PANEL.values()[currentPanel.ordinal() + increment];
    }

    @Override
    public void previousPanel() {
        int increment = 1;

        if (currentPanel == CONFIG_PANEL && (type == ExecutorType.WRITER || type == ExecutorType.CONDITION)) {
            increment++;
        }
        currentPanel = WIZARD_PANEL.values()[currentPanel.ordinal() - increment];
    }

    @Override
    public void addChangeListener(ChangeListener l) {
    }

    @Override
    public void removeChangeListener(ChangeListener l) {
    }

    private DescriptiveExecutionUnit selectedNode;

    public DescriptiveExecutionUnit getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(DescriptiveExecutionUnit selectedNode) {
        this.selectedNode = selectedNode;
    }

    private Map<WIZARD_PANEL, WizardDescriptor.Panel<WizardDescriptor>> panelWizard = null;
    
    private String[] propContent;

    private void initPanel() {
        if (panelWizard == null) {
            panelWizard = new EnumMap<>(WIZARD_PANEL.class);
            propContent = new String[WIZARD_PANEL.values().length];

            int i = 0;

            for (WIZARD_PANEL w : WIZARD_PANEL.values()) {
                WizardDescriptor.Panel<WizardDescriptor> p = loadPanel(w, this);
                panelWizard.put(w, p);
                propContent[i] = w.getName();
                i++;
            }

        }
    }

    public String[] getPropContent() {
        return propContent;
    }
    
    
    
    

    public final WizardDescriptor.Panel<WizardDescriptor> getPanel(WIZARD_PANEL wizardPanel) {
        return panelWizard.get(wizardPanel);
    }

    private static WizardDescriptor.Panel<WizardDescriptor> loadPanel(WIZARD_PANEL wizardPanel, ExecutorWizardIterator iterator) {
        switch (wizardPanel) {
            case TYPE_FILTER_PANEL:
                return new TypeFilterWizard(iterator);
            case INPUT_PANEL:
                return new InputWizard(iterator);
            case OUTPUT_PANEL:
                return new OutputWizard(iterator);
            case CONFIG_PANEL:
                return new ConfigWizard(iterator);
        }
        throw new UnsupportedOperationException(wizardPanel + " missed");
    }

    public ExecutorType getType() {
        return type;
    }

    public Data getSampleInput() {
        return sampleInput;
    }

    public MappingHelper getMapping() {
        return mapping;
    }
    
    
    

    public DescriptiveExecutionUnit getExistingNode() {
        return existingNode;
    }

    public WizardDescriptor getWizardDescriptor() {
        return wizardDescriptor;
    }

    @Override
    public Set instantiate(ProgressHandle handle) throws IOException {
        return Collections.emptySet();
    }

    @Override
    public Set instantiate() throws IOException {
        return Collections.emptySet();
    }

    @Override
    public void initialize(WizardDescriptor wizard) {
    }

    @Override
    public void uninitialize(WizardDescriptor wizard) {
    }

}
