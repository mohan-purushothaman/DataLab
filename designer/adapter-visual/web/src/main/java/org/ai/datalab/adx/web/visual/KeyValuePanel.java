/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.adx.web.visual;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Mohan Purushothaman
 */
public class KeyValuePanel extends javax.swing.JPanel {

    /**
     * Creates new form KeyValuePanel
     */
    public KeyValuePanel() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        addRow = new javax.swing.JButton();
        deleteRow = new javax.swing.JButton();

        setMinimumSize(new java.awt.Dimension(200, 200));

        jTable1.setModel(new KeyValueModel()
        );
        jScrollPane1.setViewportView(jTable1);

        org.openide.awt.Mnemonics.setLocalizedText(addRow, org.openide.util.NbBundle.getMessage(KeyValuePanel.class, "KeyValuePanel.addRow.text")); // NOI18N
        addRow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addRowActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(deleteRow, org.openide.util.NbBundle.getMessage(KeyValuePanel.class, "KeyValuePanel.deleteRow.text")); // NOI18N
        deleteRow.setEnabled(true);
        deleteRow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteRowActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(addRow, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(deleteRow, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(addRow)
                .addGap(18, 18, 18)
                .addComponent(deleteRow)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 383, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 343, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void addRowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addRowActionPerformed
        KeyValueModel model = (KeyValueModel) jTable1.getModel();
        model.addRow();
    }//GEN-LAST:event_addRowActionPerformed

    private void deleteRowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteRowActionPerformed
        KeyValueModel model = (KeyValueModel) jTable1.getModel();
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow != -1) {
            model.deleteRow(selectedRow);
        }
    }//GEN-LAST:event_deleteRowActionPerformed

    public Map<String, String> getMap() {
        KeyValueModel model = (KeyValueModel) jTable1.getModel();

        return model.getMap();

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addRow;
    private javax.swing.JButton deleteRow;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}

class KeyValueModel extends AbstractTableModel {

    private final List<KeyValue> values = new LinkedList<>();

    {
            values.add(new KeyValue());
    }

    @Override
    public int getRowCount() {
        return values.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        KeyValue row = values.get(rowIndex);
        return columnIndex == 0 ? row.getKey() : row.getValue();
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        KeyValue row = values.get(rowIndex);
        if (columnIndex == 0) {
            row.setKey((String) aValue);
        } else {
            row.setValue((String) aValue);
        }
    }

    public void deleteRow(int row) {
        values.remove(row);
        fireTableStructureChanged();
    }

    public void addRow() {
        values.add(new KeyValue());
        fireTableStructureChanged();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    @Override
    public String getColumnName(int column) {
        return column == 0 ? "Key" : "Value";
    }

    public Map<String, String> getMap() {
        Map<String, String> map = new LinkedHashMap<>();
        for (KeyValue value : values) {
            map.put(value.getKey(), value.getValue());
        }
        return map;
    }

}

class KeyValue {

    private String key;
    private String value;

    public KeyValue(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public KeyValue() {
        this("", "");
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
