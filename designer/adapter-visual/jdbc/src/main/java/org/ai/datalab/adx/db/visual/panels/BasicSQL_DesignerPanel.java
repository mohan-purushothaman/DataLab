/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.adx.db.visual.panels;

import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Collection;
import javax.swing.ComboBoxModel;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import org.netbeans.api.options.OptionsDisplayer;
import org.netbeans.api.progress.ProgressHandle;
import org.openide.util.Exceptions;
import org.ai.datalab.adx.db.DB_Adapter;
import org.ai.datalab.adx.db.DB_Processor;
import org.ai.datalab.adx.db.DB_Provider;
import org.ai.datalab.adx.db.visual.DbExecutionUnit;
import org.ai.datalab.adx.db.visual.JDBC_ResourceCreater;
import org.ai.datalab.core.AbstractExecutorProvider;
import org.ai.datalab.core.Data;
import org.ai.datalab.core.adx.misc.MappingHelper;
import org.ai.datalab.core.executor.ExecutorType;
import org.ai.datalab.core.misc.DataUtil;
import org.ai.datalab.core.misc.Type;
import org.ai.datalab.core.misc.TypeUtil;
import org.ai.datalab.core.resource.Resource;
import org.ai.datalab.core.resource.ResourcePool;
import org.ai.datalab.designer.editor.SimpleEditor;
import org.ai.datalab.designer.panels.VisualNodeValidator;
import org.ai.datalab.designer.util.ResourceVisualUtil;
import org.ai.datalab.designer.visual.resource.ResourceCreator;
import org.ai.datalab.designer.visual.resource.ResourceStore;
import org.ai.datalab.visual.impl.widget.DescriptiveExecutionUnit;
import org.openide.util.Lookup;

/**
 *
 * @author Mohan Purushothaman
 */
public class BasicSQL_DesignerPanel extends VisualNodeValidator {

    /**
     * Creates new form BasicSQL_DesignerPanel
     */
    private final ExecutorType type;
    private final Data sampleInput;

    private final DescriptiveExecutionUnit existingUnit;
    private final JTextComponent textArea;

    public BasicSQL_DesignerPanel(DescriptiveExecutionUnit existingUnit, ExecutorType type, Data sampleInput) {
        this.type = type;
        this.sampleInput = sampleInput;
        this.existingUnit = existingUnit;
        initComponents();
        textArea = SimpleEditor.addVariableEditorPane(editorPanel, sampleInput, new TextListener() {
            @Override
            public void textValueChanged(TextEvent e) {
                Document d = (Document) e.getSource();
                try {
                    replacedQuery.setText(DataUtil.substituteData(d.getText(0, d.getLength()), sampleInput));
                } catch (Exception ex) {
                    replacedQuery.setText("Exception occured : " + ex);
                }
            }
        });

        if (type == ExecutorType.READER) {
            jScrollPane1.setVisible(false);
        }

        if (this.existingUnit != null && this.existingUnit instanceof DbExecutionUnit) {
            DbExecutionUnit unit=(DbExecutionUnit) this.existingUnit;
            DB_Provider provider=(DB_Provider) unit.getExecutorProvider();
            resourcePools.setSelectedItem(ResourceStore.getResourcePool(provider.getResourceID()));
            textArea.setText(provider.getQuery());
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        resourcePools = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        editorPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        replacedQuery = new javax.swing.JTextArea();

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(BasicSQL_DesignerPanel.class, "BasicSQL_DesignerPanel.jLabel1.text")); // NOI18N

        resourcePools.setModel((ComboBoxModel<ResourcePool<Connection>>)ResourceVisualUtil.getResourceComboBox(Connection.class,null));

        org.openide.awt.Mnemonics.setLocalizedText(jButton1, org.openide.util.NbBundle.getMessage(BasicSQL_DesignerPanel.class, "BasicSQL_DesignerPanel.jButton1.text")); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(resourcePools, 0, 517, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(resourcePools, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        editorPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout editorPanelLayout = new javax.swing.GroupLayout(editorPanel);
        editorPanel.setLayout(editorPanelLayout);
        editorPanelLayout.setHorizontalGroup(
            editorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        editorPanelLayout.setVerticalGroup(
            editorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 283, Short.MAX_VALUE)
        );

        replacedQuery.setEditable(false);
        replacedQuery.setColumns(20);
        replacedQuery.setRows(5);
        jScrollPane1.setViewportView(replacedQuery);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(editorPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(editorPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
         
        Collection<? extends ResourceCreator> r = Lookup.getDefault().lookupAll(ResourceCreator.class);

        for (ResourceCreator resourceCreator : r) {
            if (resourceCreator instanceof JDBC_ResourceCreater) {
                ResourcePool<File> rp = resourceCreator.createResourcePool();
                if (rp != null) {
                    try {
                        ResourceStore.addResourcePool(rp);
                        resourcePools.setModel((ComboBoxModel<ResourcePool<Connection>>) ResourceVisualUtil.getResourceComboBox(Connection.class, null));
                        resourcePools.setSelectedItem(rp);
                    } catch (Exception ex) {
                        Exceptions.printStackTrace(ex);
                    }
                }
            }
        }
        
        
        
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel editorPanel;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea replacedQuery;
    private javax.swing.JComboBox<ResourcePool<Connection>> resourcePools;
    // End of variables declaration//GEN-END:variables

    @Override
    public DescriptiveExecutionUnit validateConnector(ProgressHandle handle) throws Exception {
        try {
            ResourcePool<Connection> pool = resourcePools.getItemAt(resourcePools.getSelectedIndex());
            String query = textArea.getText();
            if (pool == null) {
                throw new Exception("DB resource not found");
            }
            MappingHelper mapping = executerQuery(pool, query);
            AbstractExecutorProvider provider = getProvider(pool, query, mapping);
            return new DbExecutionUnit("DB " + type, provider, sampleInput);
        } catch (Exception e) {
            Exceptions.printStackTrace(e);
            throw e;
        }
    }

    private AbstractExecutorProvider getProvider(ResourcePool<Connection> pool, String query, MappingHelper mapping) {
        switch (type) {
            case READER:
                return DB_Adapter.createReader(pool, query, mapping);
            case PROCESSOR:
                return new DB_Processor(pool, query, mapping);
            case WRITER:
                return new DB_Processor(pool, query, null);
        }
        throw new RuntimeException("undefined type");
    }

    private MappingHelper executerQuery(ResourcePool<Connection> pool, String query) throws Exception {
        MappingHelper mapping = new MappingHelper();

        try (Resource<Connection> r = pool.getResource()) {
            try (Connection c = r.get()) {
                try {
                    c.setAutoCommit(false);
                    try (Statement p = c.createStatement()) {
                        if (p.execute(DataUtil.substituteData(query, sampleInput))) {
                            ResultSet set = p.getResultSet();
                            boolean rowExists = set.next();
                            for (int i = 1; i <= set.getMetaData().getColumnCount(); i++) {
                                String columnName = set.getMetaData().getColumnName(i);
                                Object val = rowExists ? set.getObject(columnName) : null;
                                String name = DataUtil.normalizeFieldKey(columnName, sampleInput);

                                Type detectedType = TypeUtil.detectType(val);

                                mapping.addIdMap(columnName, name, detectedType.getConverter(), val);
                            }

                        } else {
                            String name = DataUtil.normalizeFieldKey("UPDATE_COUNT", sampleInput);
                            mapping.addIdMap("NOT_USED", name, Type.Long.getConverter(), p.getUpdateCount());
                        }

                    }

                } finally {
                    c.rollback();
                }

            }
        }
        return mapping;

    }

    @Override
    public String prepareValidation() {
        return "validating SQL query";
    }

    @Override
    public void finishValidation() {

    }
}
