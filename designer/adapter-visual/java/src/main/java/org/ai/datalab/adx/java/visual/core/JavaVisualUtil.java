/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.adx.java.visual.core;

import java.awt.BorderLayout;
import java.io.OutputStream;
import java.util.EnumSet;
import java.util.Map;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.Document;
import javax.swing.text.StyledDocument;
import org.netbeans.api.editor.DialogBinding;
import org.netbeans.api.editor.guards.GuardedSectionManager;
import org.netbeans.api.editor.guards.InteriorSection;
import org.netbeans.api.editor.mimelookup.MimeLookup;
import org.netbeans.modules.editor.NbEditorDocument;
import org.netbeans.spi.editor.guards.GuardedEditorSupport;
import org.netbeans.spi.editor.guards.GuardedSectionsFactory;
import org.netbeans.spi.editor.guards.GuardedSectionsProvider;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.util.Exceptions;
import org.ai.datalab.adx.java.JavaCodeGenerator;
import org.ai.datalab.adx.java.core.simple.SimpleJavaCodeGenerator;
import org.ai.datalab.core.Data;
import org.ai.datalab.core.adx.CodeSegment;
import org.ai.datalab.core.adx.misc.MappingHelper;
import org.ai.datalab.core.adx.misc.SingleMapping;
import org.ai.datalab.core.misc.Type;
import org.ai.datalab.core.misc.TypeUtil;
import org.ai.datalab.visual.impl.DescriptiveSingleMapping;

/**
 *
 * @author Mohan Purushothaman
 */
public class JavaVisualUtil {

    public static final String JAVA_MIME_TYPE = "text/x-java";
    private static JEditorPane latestEditor;

    public static JEditorPane getLatestEditor() {
        return latestEditor;
    }

    public static JPanel addJavaEditorPane(String fileName, JavaCodeGenerator generator) {

        try {

            FileObject folder = FileUtil.createMemoryFileSystem().getRoot().createFolder("src").createFolder("test");
            for (FileObject c : folder.getChildren()) {
                c.delete();
            }

            FileObject fob = folder.createData(fileName, "java");

            final DataObject dob = DataObject.find(fob);

            JEditorPane editor = (latestEditor = new JEditorPane(JAVA_MIME_TYPE, ""));

            editor.getDocument().putProperty(Document.StreamDescriptionProperty, dob);

            DialogBinding.bindComponentToFile(fob, 0, 0, editor);

            NbEditorDocument newDoc = (NbEditorDocument) editor.getDocument();// dob.getCookie(EditorCookie.class).openDocument();

            //copyDoc(newDoc, (NbEditorDocument)editor.getDocument());
            //editor.setDocument(newDoc);
            GuardedSectionsProvider p = MimeLookup.getLookup(JAVA_MIME_TYPE).lookup(GuardedSectionsFactory.class).create(new GuardedEditorSupport() {
                @Override
                public StyledDocument getDocument() {
                    return newDoc;
                }
            });

            p.createGuardedReader(null, null);

            populateDocument(newDoc, generator);
            
            JPanel jp = new JPanel(new BorderLayout());
            jp.add(newDoc.createEditor(editor), BorderLayout.CENTER);
            

            return jp;
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }
        return null;
    }

    public static void populateDocument(StyledDocument doc, JavaCodeGenerator generator) throws Exception {

        GuardedSectionManager m = GuardedSectionManager.getInstance(doc);
        CodeSegment lastSegmentProcessed = null;
        for (CodeSegment codeSegment : generator.getSegmentOrder()) {
            String preContent = generator.formatValue(generator.getPreSection(codeSegment));
            String content = generator.formatValue(generator.getCodeSegmentHandler().getCodeSegment(codeSegment));
            String postContent = generator.formatValue(generator.getPostSection(codeSegment));

            try {
                int pos = 0;
                if (lastSegmentProcessed != null) {
                    InteriorSection s = m.findInteriorSection(lastSegmentProcessed.name());
                    pos = s.getEndPosition().getOffset() + 1;
                }

                InteriorSection sec = m.createInteriorSection(doc.createPosition(pos), codeSegment.name());
                sec.setHeader(preContent);
                sec.setBody(content);
                sec.setFooter(postContent);
            } finally {
                lastSegmentProcessed = codeSegment;
            }
        }
    }

    public static void populateGenerator(StyledDocument doc, JavaCodeGenerator generator) throws Exception {
        GuardedSectionManager m = GuardedSectionManager.getInstance(doc);
        for (CodeSegment codeSegment : generator.getSegmentOrder()) {
            InteriorSection s = m.findInteriorSection(codeSegment.name());
            if (s != null) {
                generator.getCodeSegmentHandler().setCodeSegment(codeSegment, s.getBody());
            }
        }
    }

    public static JPanel getSimpleJavaEditor(SimpleJavaCodeGenerator generator, String fileName) {
        try {
            FileObject folder = FileUtil.createMemoryFileSystem().getRoot().createFolder("src").createFolder("test");
            for (FileObject c : folder.getChildren()) {
                c.delete();
            }

            FileObject fob = folder.createData(fileName, "java");

            String content = generator.getSourceContentExcludingExecute();

            try (OutputStream o = fob.getOutputStream()) {
                o.write(content.getBytes());
            }
            DataObject dob = DataObject.find(fob);

            JEditorPane pane = (latestEditor = new JEditorPane(JAVA_MIME_TYPE, ""));

            pane.getDocument().putProperty(Document.StreamDescriptionProperty, dob);

            DialogBinding.bindComponentToFile(fob, generator.findExecuteIndex(content), 0, pane);

            NbEditorDocument newDoc = (NbEditorDocument) pane.getDocument();

            pane.setText(generator.getCodeSegmentHandler().getCodeSegment(CodeSegment.EXECUTE));
            JPanel jp = new JPanel(new BorderLayout());
            jp.add(newDoc.createEditor(pane), BorderLayout.CENTER);
            return jp;
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
            JPanel p = new JPanel();
            p.add(new JLabel("initiating failed"));
            return p;
        }

    }

    public static MappingHelper getOutputMapping(Data sampleData, MappingHelper sampleMapping) {

        MappingHelper helper = new MappingHelper();
        Map<String, SingleMapping> existingMapping = MappingHelper.getMapping(sampleMapping);

        for (Map.Entry<String, Object> entry : sampleData.getEntrySet()) {
            Object val = entry.getValue();
            SingleMapping s1 = existingMapping == null ? null : existingMapping.get(entry.getKey());
            Type type = s1==null?TypeUtil.detectType(val):s1.getConverter().getResultType();
            SingleMapping s = new DescriptiveSingleMapping(false, entry.getKey(), entry.getKey(), type.getConverter(), val, helper);
            helper.addIdMap(s);
        }
        return helper;
    }

//    private static Object[] props = {"mimeType", InputAttributes.class};
//
//    public static void copyDoc(NbEditorDocument newDoc, NbEditorDocument oldDoc) {
//        oldDoc.getProperty(newDoc);
//        Enumeration en = oldDoc.getDocumentProperties().keys();
//        while (en.hasMoreElements()) {
//            Object key = en.nextElement();
//            newDoc.putProperty(key, oldDoc.getProperty(key));
//        }
//    }
}
