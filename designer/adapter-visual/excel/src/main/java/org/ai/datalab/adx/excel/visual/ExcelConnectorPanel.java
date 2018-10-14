/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.adx.excel.visual;

import java.io.File;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.AbstractTableModel;
import org.netbeans.api.progress.ProgressHandle;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.ai.datalab.adapter.excel.adx.ExcelReader;
import org.ai.datalab.adapter.excel.adx.ExcelUtil;
import org.ai.datalab.adapter.excel.adx.ExcelWriter;
import org.ai.datalab.core.Data;
import org.ai.datalab.core.adx.misc.MappingHelper;
import org.ai.datalab.core.adx.misc.ValueConverter;
import org.ai.datalab.core.executor.ExecutorType;
import org.ai.datalab.core.misc.DataUtil;
import org.ai.datalab.core.misc.Type;
import org.ai.datalab.core.misc.TypeUtil;
import org.ai.datalab.core.resource.Resource;
import org.ai.datalab.core.resource.ResourcePool;
import org.ai.datalab.core.resource.ResourcePoolQualifier;
import org.ai.datalab.designer.panels.VisualNodeValidator;
import org.ai.datalab.designer.util.ResourceVisualUtil;
import org.ai.datalab.designer.visual.resource.ResourceCreator;
import org.ai.datalab.designer.visual.resource.ResourceStore;
import org.ai.datalab.visual.impl.widget.DescriptiveExecutionUnit;

/**
 *
 * @author Mohan Purushothaman
 */
public class ExcelConnectorPanel extends VisualNodeValidator {

    private final ExecutorType type;
    private final Data sampleInput;

    /**
     * Creates new form ExcelConnectorPanel
     */
    public ExcelConnectorPanel(ExecutorType type, Data sampleInput) {
        this.type = type;
        this.sampleInput = sampleInput;
        initComponents();
        jComboBox1ActionPerformed(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        selectFile = new javax.swing.JButton();
        hasHeader = new javax.swing.JCheckBox();
        sheetNames = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        tablePane = new javax.swing.JScrollPane();
        dataTable = new javax.swing.JTable();

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(ExcelConnectorPanel.class, "ExcelConnectorPanel.jLabel1.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(selectFile, org.openide.util.NbBundle.getMessage(ExcelConnectorPanel.class, "ExcelConnectorPanel.selectFile.text")); // NOI18N
        selectFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectFileActionPerformed(evt);
            }
        });

        hasHeader.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(hasHeader, org.openide.util.NbBundle.getMessage(ExcelConnectorPanel.class, "ExcelConnectorPanel.hasHeader.text")); // NOI18N
        hasHeader.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hasHeaderActionPerformed(evt);
            }
        });

        sheetNames.setEditable(type==ExecutorType.WRITER);
        sheetNames.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sheetNamesActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, org.openide.util.NbBundle.getMessage(ExcelConnectorPanel.class, "ExcelConnectorPanel.jLabel3.text")); // NOI18N

        jComboBox1.setModel(getResourceComboBoxModel());
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(sheetNames, 0, 259, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(hasHeader))
                    .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(24, 24, 24)
                .addComponent(selectFile)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(selectFile)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(sheetNames, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hasHeader))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setLayout(new java.awt.BorderLayout());

        tablePane.setViewportView(dataTable);

        jPanel2.add(tablePane, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void selectFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectFileActionPerformed
        Collection<? extends ResourceCreator> r = Lookup.getDefault().lookupAll(ResourceCreator.class);

        for (ResourceCreator resourceCreator : r) {
            if (resourceCreator instanceof ExcelResourceCreator) {
                ResourcePool<File> rp = resourceCreator.createResourcePool();
                if (rp != null) {
                    try {
                        ResourceStore.addResourcePool(rp);
                        jComboBox1.setModel(getResourceComboBoxModel());
                        jComboBox1.setSelectedItem(rp);
                    } catch (Exception ex) {
                        Exceptions.printStackTrace(ex);
                    }
                }
            }
        }

    }//GEN-LAST:event_selectFileActionPerformed

    private void hasHeaderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hasHeaderActionPerformed
        updateTable();
    }//GEN-LAST:event_hasHeaderActionPerformed

    private void sheetNamesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sheetNamesActionPerformed
        updateTable();
    }//GEN-LAST:event_sheetNamesActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        updateSheets();
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private final ComboBoxModel<ResourcePool<File>> getResourceComboBoxModel() {
        return (ComboBoxModel<ResourcePool<File>>) ResourceVisualUtil.getResourceComboBox(File.class, new ResourcePoolQualifier<File>() {
            @Override
            public boolean validate(ResourcePool<File> resource) {
                try (Resource<File> f = resource.getResource()) {
                    File file = f.get();
                    return ExcelUtil.EXCEL_FILTER.accept(file) && (type != ExecutorType.READER || file.exists());
                } catch (Exception ex) {
                    Exceptions.printStackTrace(ex);
                    return false;
                }
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable dataTable;
    private javax.swing.JCheckBox hasHeader;
    private javax.swing.JComboBox<ResourcePool<File>> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JButton selectFile;
    private javax.swing.JComboBox<String> sheetNames;
    private javax.swing.JScrollPane tablePane;
    // End of variables declaration//GEN-END:variables

    @Override
    public String prepareValidation() {
        return "validating Excel connector";
    }

    @Override
    public DescriptiveExecutionUnit validateConnector(ProgressHandle handle) throws Exception {
        ResourcePool<File> pool = (ResourcePool<File>) jComboBox1.getSelectedItem();
        String sheetName = (String) sheetNames.getSelectedItem();
        boolean isHasHeader = hasHeader.isSelected();

        if (type == ExecutorType.READER) {
            MappingHelper mapping = getMapping(ref.get(),isHasHeader);
            return new ExcelExecutionUnit("reading from excel", new ExcelReader(mapping, pool, sheetName, isHasHeader), sampleInput);
        } else {
            return new ExcelExecutionUnit("writing to excel", new ExcelWriter(pool, sheetName, isHasHeader), sampleInput);
        }
    }

    @Override
    public void finishValidation() {
    }

    private AtomicReference<Object[][]> ref = new AtomicReference<>();

    private void updateTable() {
        if (type == ExecutorType.READER) {
            ResourcePool<File> pool = (ResourcePool<File>) jComboBox1.getSelectedItem();
            String sheetName = (String) sheetNames.getSelectedItem();

            try {
                Object[][] val = ExcelUtil.fetchFirst2Rows(pool, sheetName);
                ref.set(val);
                if (val != null) {
                    dataTable.setModel(new AbstractTableModel() {
                        @Override
                        public int getRowCount() {
                            return val.length;
                        }

                        @Override
                        public int getColumnCount() {
                            return val[0].length;
                        }

                        @Override
                        public Object getValueAt(int rowIndex, int columnIndex) {
                            return val[rowIndex][columnIndex];
                        }
                    });

                }
            } catch (Exception ex) {
                Exceptions.printStackTrace(ex);
            }
        } else if (type == ExecutorType.WRITER) {
            //TODO
        }

    }

    private void updateSheets() {
        ResourcePool<File> pool = (ResourcePool<File>) jComboBox1.getSelectedItem();
        if (pool != null) {
            try {
                Set<String> list = ExcelUtil.getSheetNames(pool);
                sheetNames.setModel(new DefaultComboBoxModel(list.toArray(new String[0])));
                if (!list.isEmpty()) {
                    sheetNames.setSelectedIndex(0);
                }
            } catch (Exception ex) {
                Exceptions.printStackTrace(ex);
            }
        }
    }

    private MappingHelper<Integer> getMapping(Object[][] data, boolean hasHeader) {
        MappingHelper<Integer> mapping = new MappingHelper<>();
        int size = data[0].length;

        for (int i = 0; i < size; i++) {
            String id = DataUtil.normalizeFieldKey(hasHeader ? String.valueOf(data[0][i]) : "FIELD_" + i, sampleInput);
            Object value = hasHeader ? data[1][i] : data[0][i];
            Type type=TypeUtil.detectType(value);
            
            
            mapping.addIdMap(i,id, type.getConverter(),value);
        }

        return mapping;

    }
}
