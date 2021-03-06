/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.designer.misc;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.table.AbstractTableModel;
import org.ai.datalab.core.DataJob;
import org.ai.datalab.core.resource.ResourcePool;
import org.ai.datalab.designer.visual.resource.ResourceStore;
import org.netbeans.api.options.OptionsDisplayer;

/**
 *
 * @author Mohan Purushothaman
 */
public class ResourceFixPanel extends javax.swing.JPanel {

    /**
     * Creates new form ResourceFixPanel
     */
    private final DataJob job;

    private final Map<String, String> map = new LinkedHashMap<>();

    public ResourceFixPanel(DataJob job) {
        this.job = job;
        initComponents();
    }

    public Map<String, String> getReMapppingMap() {
        return map;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = getTable();
        jButton1 = new javax.swing.JButton();

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(ResourceFixPanel.class, "ResourceFixPanel.jLabel1.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(ResourceFixPanel.class, "ResourceFixPanel.jLabel2.text")); // NOI18N

        jScrollPane1.setViewportView(jTable1);

        org.openide.awt.Mnemonics.setLocalizedText(jButton1, org.openide.util.NbBundle.getMessage(ResourceFixPanel.class, "ResourceFixPanel.jButton1.text")); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean open = OptionsDisplayer.getDefault().open(ResourceStore.RESOURCE_PANEL_ID, true);
                cache.set(null);
                updateTableEditor(jTable1);
            }
        }).start();

    }//GEN-LAST:event_jButton1ActionPerformed

    private JTable getTable() {

        JTable table = new JTable(new AbstractTableModel() {

            List<String> resourceList = new ArrayList<>();

            {
                resourceList.addAll(ResourceValidatorUtil.findMissingResources(job));

                for (String s : resourceList) {
                    map.put(s, null);
                }

            }

            @Override
            public int getRowCount() {
                return map.size();
            }

            @Override
            public int getColumnCount() {
                return 2;
            }

            @Override
            public String getValueAt(int rowIndex, int columnIndex) {
                String s = resourceList.get(rowIndex);
                if (columnIndex == 0) {
                    return s;
                } else {
                    String val = map.get(s);
                    return val == null ? "" : val;
                }

            }

            @Override
            public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
                if (columnIndex == 1) {
                    String s = resourceList.get(rowIndex);
                    map.put(s, (String) aValue);
                }
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return columnIndex == 1;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return String.class;
            }

            @Override
            public String getColumnName(int column) {
                return column == 0 ? "Expected Resource Name" : "Select ResourceName";
            }

        });

        updateTableEditor(table);
        return table;
    }

    private List<ResourcePool> getList() {
        if (cache.get() == null) {
            cache.set(ResourceStore.getSortedResourceList(null));
        }
        return cache.get();
    }

    private AtomicReference<List<ResourcePool>> cache=new AtomicReference<>();


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables

    private void updateTableEditor(JTable table) {
        table.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(new JComboBox(new ComboBoxModel() {

            Object selectedObject;

            @Override
            public void setSelectedItem(Object anObject) {
                if ((selectedObject != null && !selectedObject.equals(anObject))
                        || selectedObject == null && anObject != null) {
                    selectedObject = anObject;
                    for (ListDataListener l : listenerList) {
                        l.contentsChanged(new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, -1, -1));
                    }
                }
            }

            @Override
            public Object getSelectedItem() {

                if (selectedObject == null) {
                    List<ResourcePool> list = getList();

                    if (!list.isEmpty()) {
                        return list.get(0);
                    }

                }

                return selectedObject;
            }

            @Override
            public int getSize() {
                return getList().size();
            }

            @Override
            public Object getElementAt(int index) {
                return ((ResourcePool) getList().get(index)).getResourceId();
            }

            protected List<ListDataListener> listenerList = new LinkedList<>();

            @Override
            public void addListDataListener(ListDataListener l) {
                listenerList.add(l);
            }

            @Override
            public void removeListDataListener(ListDataListener l) {
                listenerList.remove(l);
            }

        })));
    }
}
