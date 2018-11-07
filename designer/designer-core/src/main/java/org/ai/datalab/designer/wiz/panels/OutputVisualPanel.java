/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.designer.wiz.panels;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import javax.swing.JPanel;
import org.ai.datalab.core.adx.misc.MappingHelper;
import org.ai.datalab.core.adx.misc.SingleMapping;
import org.ai.datalab.designer.wiz.ExecutorWizardIterator;
import org.ai.datalab.designer.wiz.panels.misc.DataVisualView;
import org.openide.WizardValidationException;

/**
 *
 * @author Mohan Purushothaman
 */
public class OutputVisualPanel extends JPanel {

    /**
     * Creates new form TypeFilterVisualPanel
     */
    private final ExecutorWizardIterator iterator;

    public OutputVisualPanel(ExecutorWizardIterator iterator) {
        this.iterator = iterator;
        initComponents();
        if (iterator.getExistingNode() == null) {
            existingData.setVisible(false);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        viewPanel = new DataVisualView(iterator,"Sample Output Data");
        existingData = new DataVisualView(iterator,"Existing Output Data",true);

        javax.swing.GroupLayout viewPanelLayout = new javax.swing.GroupLayout(viewPanel);
        viewPanel.setLayout(viewPanelLayout);
        viewPanelLayout.setHorizontalGroup(
            viewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 364, Short.MAX_VALUE)
        );
        viewPanelLayout.setVerticalGroup(
            viewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 431, Short.MAX_VALUE)
        );

        existingData.setEnabled(iterator.getExistingNode()!=null);

        javax.swing.GroupLayout existingDataLayout = new javax.swing.GroupLayout(existingData);
        existingData.setLayout(existingDataLayout);
        existingDataLayout.setHorizontalGroup(
            existingDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 364, Short.MAX_VALUE)
        );
        existingDataLayout.setVerticalGroup(
            existingDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(viewPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(existingData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(viewPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(existingData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel existingData;
    private javax.swing.JPanel viewPanel;
    // End of variables declaration//GEN-END:variables

    void refresh() {
        ((DataVisualView) viewPanel).refresh(iterator.getSelectedNode());
        if (iterator.getExistingNode() != null) {
            ((DataVisualView) existingData).refresh(iterator.getExistingNode());
        }
    }

    public void validateMapping() throws WizardValidationException {
        MappingHelper newMapping = ((DataVisualView) viewPanel).getUnit().getMapping();
        MappingHelper existingMap = ((DataVisualView) existingData).getUnit().getMapping();

        Map<String, SingleMapping> newMap = new LinkedHashMap<>();

        for (Object s : newMapping.getIdList(DataVisualView.ALPHABETICAL_MAPPING)) {
            SingleMapping si = (SingleMapping) s;
            newMap.put(si.getFieldKey(), si);
        }

        for (Object s : existingMap.getIdList(DataVisualView.ALPHABETICAL_MAPPING)) {
            SingleMapping ex = (SingleMapping) s;
            SingleMapping nw = newMap.remove(ex.getFieldKey());
            if (nw == null) {
                String msg = ex.getFieldKey() + " is missing in new connector";
                throw new WizardValidationException(this, msg, msg);
            }
            if (ex.getConverter().getResultType() != nw.getConverter().getResultType()) {
                String msg = nw.getFieldKey() + " is of type " + nw.getConverter().getResultType() + " , But expecting " + ex.getConverter().getResultType();
                throw new WizardValidationException(this, msg, msg);
            }
        }

        for (String s : newMap.keySet()) {
            String msg = s + " is not present in existing connector";
            throw new WizardValidationException(this, msg, msg);
        }

    }

}
