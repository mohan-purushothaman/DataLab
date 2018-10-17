/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.adx.web.visual;

import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.text.JTextComponent;
import org.ai.datalab.adx.web.HttpMethodType;
import org.ai.datalab.adx.web.HttpProcessor;
import org.ai.datalab.adx.web.HttpWriter;
import org.ai.datalab.adx.web.WebResourcePool;
import org.ai.datalab.adx.web.WebUtil;
import org.ai.datalab.core.Data;
import org.ai.datalab.core.adx.misc.MappingHelper;
import org.ai.datalab.core.executor.ExecutorType;
import org.ai.datalab.core.misc.Type;
import org.ai.datalab.core.misc.TypeUtil;
import org.ai.datalab.core.resource.Resource;
import org.ai.datalab.core.resource.ResourcePool;
import org.ai.datalab.designer.editor.SimpleEditor;
import org.ai.datalab.designer.panels.VisualNodeValidator;
import org.ai.datalab.designer.visual.resource.ResourceStore;
import org.ai.datalab.visual.impl.widget.DescriptiveExecutionUnit;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.HttpStatus;
import org.netbeans.api.options.OptionsDisplayer;
import org.netbeans.api.progress.ProgressHandle;

/**
 *
 * @author Mohan Purushothaman
 */
public class Http_DesignerPanel extends VisualNodeValidator {

    private final ExecutorType type;
    private final Data sampleInput;
    private final JTextComponent textArea;

    public Http_DesignerPanel(ExecutorType type, Data sampleInput) {
        initComponents();
        this.type = type;
        this.sampleInput = sampleInput;
        textArea = SimpleEditor.addVariableEditorPane(requestBody, this.sampleInput, null);
        jCheckBox1ActionPerformed(null);
        if (type == ExecutorType.WRITER) {
            jTabbedPane1.remove(sampleResponseTab);
        }
    }

    public ComboBoxModel<ResourcePool> getModel() {
        Collection<ResourcePool> pool = ResourceStore.findResourcePools(URL.class, null);
        return new DefaultComboBoxModel<>(pool.toArray(new ResourcePool[0]));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        requestBody = new javax.swing.JPanel();
        header = new org.ai.datalab.adx.web.visual.KeyValuePanel();
        param = new org.ai.datalab.adx.web.visual.KeyValuePanel();
        sampleResponseTab = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        sampleResponseText = new javax.swing.JTextArea();
        jCheckBox1 = new javax.swing.JCheckBox();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        webResource = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        httpMethod = new javax.swing.JComboBox<>();

        requestBody.setLayout(new java.awt.BorderLayout());
        jTabbedPane1.addTab("Request Body", requestBody);
        jTabbedPane1.addTab("Request Header", header);
        jTabbedPane1.addTab("Request Param", param);

        sampleResponseText.setColumns(20);
        sampleResponseText.setRows(5);
        jScrollPane1.setViewportView(sampleResponseText);

        jCheckBox1.setText("Do not Make Request, Sample response below");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout sampleResponseTabLayout = new javax.swing.GroupLayout(sampleResponseTab);
        sampleResponseTab.setLayout(sampleResponseTabLayout);
        sampleResponseTabLayout.setHorizontalGroup(
            sampleResponseTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sampleResponseTabLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(sampleResponseTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(sampleResponseTabLayout.createSequentialGroup()
                        .addComponent(jCheckBox1)
                        .addGap(0, 253, Short.MAX_VALUE)))
                .addContainerGap())
        );
        sampleResponseTabLayout.setVerticalGroup(
            sampleResponseTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sampleResponseTabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jCheckBox1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Sample Response", sampleResponseTab);

        jLabel1.setText("URL");

        webResource.setModel(getModel());

        jButton1.setText("Add New");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        httpMethod.setModel(new DefaultComboBoxModel<HttpMethodType>(HttpMethodType.values())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(webResource, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(httpMethod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(webResource, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(httpMethod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTabbedPane1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 349, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        boolean open = OptionsDisplayer.getDefault().open(ResourceStore.RESOURCE_PANEL_ID, true);
        webResource.setModel(getModel());
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
         
            sampleResponseText.setVisible(jCheckBox1.isSelected());

    }//GEN-LAST:event_jCheckBox1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.ai.datalab.adx.web.visual.KeyValuePanel header;
    private javax.swing.JComboBox<HttpMethodType> httpMethod;
    private javax.swing.JButton jButton1;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private org.ai.datalab.adx.web.visual.KeyValuePanel param;
    private javax.swing.JPanel requestBody;
    private javax.swing.JPanel sampleResponseTab;
    private javax.swing.JTextArea sampleResponseText;
    private javax.swing.JComboBox<ResourcePool> webResource;
    // End of variables declaration//GEN-END:variables

    @Override
    public String prepareValidation() {
        return "Validating Web connector";
    }

    @Override
    public DescriptiveExecutionUnit validateConnector(ProgressHandle handle) throws Exception {
        WebResourcePool pool = (WebResourcePool) webResource.getSelectedItem();
        HttpMethodType httpMethodType = (HttpMethodType) httpMethod.getSelectedItem();
        try (Resource<URL> url = pool.getResource()) {

            Map<String, Object> webResponse;

            if (jCheckBox1.isSelected()) {
                webResponse = new HashMap<>();
                webResponse.put(WebUtil.RESPONSE, sampleResponseText.getText());
                webResponse.put(WebUtil.STATUS_CODE, HttpStatus.SC_OK);
            } else {
                webResponse = WebUtil.getWebResponse(url.get().toExternalForm(), header.getMap(), param.getMap(), httpMethodType, textArea.getText(), sampleInput);
            }
            switch (type) {
                case PROCESSOR: {
                    MappingHelper helper = new MappingHelper();

                    for (Entry<String, Object> o : webResponse.entrySet()) {

                        Object value = o.getValue();

                        Type detectType = TypeUtil.detectType(value);

                        helper.addIdMap(o.getKey(), o.getKey(), detectType.getConverter(), value);
                    }
                    return new HttpExecutionUnit("Process using http connection", new HttpProcessor(header.getMap(), param.getMap(), httpMethodType, textArea.getText(), helper, pool.getResourceId()), sampleInput);
                }
                case WRITER: {
                    return new HttpExecutionUnit("write to http connection", new HttpWriter(header.getMap(), param.getMap(), httpMethodType, textArea.getText(), pool.getResourceId()), sampleInput);
                }
            }
        }
        throw new UnsupportedOperationException("unexpected operation");

    }

    @Override
    public void finishValidation() {

    }
}
