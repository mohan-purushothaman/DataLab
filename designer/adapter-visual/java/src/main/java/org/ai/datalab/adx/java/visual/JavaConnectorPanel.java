/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.adx.java.visual;

import org.ai.datalab.adx.java.visual.core.DataLabClassPathProvider;
import org.ai.datalab.adx.java.visual.core.ErrorAnnotation;
import org.ai.datalab.adx.java.visual.core.JavaVisualUtil;
import java.io.File;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.swing.DefaultListModel;
import javax.swing.ListModel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import javax.swing.text.Position;
import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.modules.editor.NbEditorDocument;
import org.openide.filesystems.FileChooserBuilder;
import org.openide.util.Exceptions;
import org.openide.util.Utilities;
import org.ai.datalab.adx.java.JavaCodeGenerator;
import org.ai.datalab.adx.java.core.JavaExecutorProvider;
import org.ai.datalab.adx.java.util.JavaUtil;
import org.ai.datalab.core.Data;
import org.ai.datalab.core.Executor;
import org.ai.datalab.core.adx.misc.MappingHelper;
import org.ai.datalab.core.adx.misc.SingleMapping;
import org.ai.datalab.core.executor.ExecutorType;
import org.ai.datalab.core.executor.Processor;
import org.ai.datalab.core.executor.Reader;
import org.ai.datalab.core.misc.SimpleData;
import org.ai.datalab.core.misc.Type;
import org.ai.datalab.core.misc.TypeUtil;
import org.ai.datalab.designer.panels.VisualNodeValidator;
import org.ai.datalab.visual.impl.DescriptiveSingleMapping;
import org.ai.datalab.visual.impl.widget.DescriptiveExecutionUnit;
import org.mdkt.compiler.CompilationException;

/**
 *
 * @author Mohan Purushothaman
 */
public class JavaConnectorPanel extends VisualNodeValidator {

    private final ExecutorType type;
    private final Data sampleInput;
    private final JavaCodeGenerator codeGenerator;
    private final String className;
    private final JTextComponent codePane;
    private final MappingHelper sampleMapping;

    public JavaConnectorPanel(JavaCodeGenerator generator, ExecutorType type, Data sampleInput,MappingHelper inputMapping) {
        this.type = type;
        this.sampleInput = sampleInput;
        this.codeGenerator = generator;
        this.sampleMapping=inputMapping;
        this.className = JavaUtil.getFileName(codeGenerator.getClazzName());
        initComponents();
        codePane = JavaVisualUtil.getLatestEditor();
        DataLabClassPathProvider.updateCodeGenerator(generator);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        jTabbedPane1 = new javax.swing.JTabbedPane();
        javaCodePane = JavaVisualUtil.addJavaEditorPane(className,codeGenerator);
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        libList = new javax.swing.JList<>();
        addNew = new javax.swing.JButton();
        deleteJar = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        javaCodePane.setMinimumSize(new java.awt.Dimension(400, 200));
        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(JavaConnectorPanel.class, "JavaConnectorPanel.javaCodePane.TabConstraints.tabTitle"), javaCodePane); // NOI18N

        libList.setModel(getLibModel());
        jScrollPane1.setViewportView(libList);

        org.openide.awt.Mnemonics.setLocalizedText(addNew, org.openide.util.NbBundle.getMessage(JavaConnectorPanel.class, "JavaConnectorPanel.addNew.text")); // NOI18N
        addNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addNewActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(deleteJar, org.openide.util.NbBundle.getMessage(JavaConnectorPanel.class, "JavaConnectorPanel.deleteJar.text")); // NOI18N

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, libList, org.jdesktop.beansbinding.ELProperty.create("!${selectionEmpty}"), deleteJar, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        deleteJar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteJarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(addNew, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(deleteJar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(addNew)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteJar)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 349, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(JavaConnectorPanel.class, "JavaConnectorPanel.jPanel1.TabConstraints.tabTitle"), jPanel1); // NOI18N

        add(jTabbedPane1, java.awt.BorderLayout.CENTER);

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents

    private void addNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addNewActionPerformed
        try {
            File f = new FileChooserBuilder(JavaConnectorPanel.class).addFileFilter(new FileNameExtensionFilter("Jar files", "jar")).showOpenDialog();
            if (f != null) {
                URL libUrl = Utilities.toURI(f).toURL();
                codeGenerator.addLibUrl(libUrl);
            }
            libList.setModel(getLibModel());
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }

    }//GEN-LAST:event_addNewActionPerformed

    private void deleteJarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteJarActionPerformed
        try {
            URL url = libList.getSelectedValue();
            codeGenerator.deleteLibUrl(url);
            libList.setModel(getLibModel());
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }
    }//GEN-LAST:event_deleteJarActionPerformed

    @Override
    public String prepareValidation() {
        return "validating Java connector";
    }

    private final List<ErrorAnnotation> errorAnnotations = new LinkedList<>();

    @Override
    public DescriptiveExecutionUnit validateConnector(ProgressHandle handle) throws Exception {
        NbEditorDocument doc = (NbEditorDocument) codePane.getDocument();

        JavaVisualUtil.populateGenerator(doc, codeGenerator);

        for (ErrorAnnotation e : errorAnnotations) {
            try {
                doc.removeAnnotation(e);
            } catch (Exception ex) {
                 Exceptions.printStackTrace(Exceptions.attachSeverity(ex, Level.INFO));
            }
        }
        errorAnnotations.clear();

        try {
            Class<Executor> clazz = JavaUtil.createClass(codeGenerator.getClazzName(), codePane.getText(), codeGenerator.getLibList()); // need to pass text editor content for line matching
            Executor e = clazz.newInstance();
            Data sampleOutput = getSampleData(e);
            JavaExecutorProvider javaExecutorProvider = new JavaExecutorProvider(type, codeGenerator, sampleOutput == null ? null : JavaVisualUtil.getOutputMapping(sampleOutput,sampleMapping));

            return new JavaExecutionUnit("java " + type.name().toLowerCase(), javaExecutorProvider, sampleInput);
        } catch (CompilationException e) {
            DiagnosticCollector diagnostics = e.getDiagnostics();

            if (diagnostics != null) {
                for (Object d : diagnostics.getDiagnostics()) {
                    Diagnostic d1 = (Diagnostic) d;
                    errorAnnotations.add(new ErrorAnnotation(d1));
                }
                for (ErrorAnnotation errorAnnotation : errorAnnotations) {
                    doc.addAnnotation(createPosition(doc, (int) errorAnnotation.getDiagnostic().getPosition()), -1, errorAnnotation);
                }
            }

            throw e;
        } catch (Exception e) {
            throw e;
        }

    }

    @Override
    public void finishValidation() {

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addNew;
    private javax.swing.JButton deleteJar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPanel javaCodePane;
    private javax.swing.JList<URL> libList;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    private Data getSampleData(Executor e) throws Exception {
        switch (type) {
            case READER:
                return ((Reader) e).readData(null);
            case PROCESSOR:
                return ((Processor) e).processData(new Data[]{SimpleData.cloneData(sampleInput)}, null)[0];
        }
        return null;
    }

   

    private ListModel<URL> getLibModel() {
        try {
            DefaultListModel<URL> model = new DefaultListModel<>();
            for (URL url : codeGenerator.getLibList()) {
                model.addElement(url);
            }
            return model;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Position createPosition(NbEditorDocument doc, int expectedPosition) throws BadLocationException {
        return doc.createPosition(Math.max(0, Math.min(expectedPosition, doc.getLength() - 1)));
    }
}
