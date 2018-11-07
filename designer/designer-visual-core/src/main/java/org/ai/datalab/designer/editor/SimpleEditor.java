/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.designer.editor;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.EditorKit;
import javax.swing.text.JTextComponent;
import javax.swing.undo.UndoManager;
import org.netbeans.api.editor.DialogBinding;
import org.netbeans.editor.BaseDocument;
import org.netbeans.editor.BaseTextUI;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.text.DataEditorSupport;
import org.openide.util.Exceptions;
import org.ai.datalab.core.Data;
import org.ai.datalab.designer.VariableHighlighterListener;

/**
 *
 * @author Mohan Purushothaman
 */
public interface SimpleEditor {

    public static final int MAX_VARIABLE_LENGTH = 32;
    public static final String LANG_MIME_TYPE = "text/x-datalab-var";

    public static final String TMP_FILE_NAME = "tempFile";

    public static final String TMP_EXTN = "ignoreFileVarType";

    public static final String SAMPLE_INPUT = "sampleInput";

    public static JTextComponent addVariableEditorPane(JPanel panel, Data sampleInput, TextListener listener) {

        try {

            FileObject folder = FileUtil.createMemoryFileSystem().getRoot().createFolder("src").createFolder("test");
            for (FileObject c : folder.getChildren()) {
                c.delete();
            }
            FileObject fob = folder.createData(TMP_FILE_NAME, TMP_EXTN);
            final DataObject dob = DataObject.find(fob);
            EditorKit kit = DataEditorSupport.getEditorKit(LANG_MIME_TYPE);
            Document doc = kit.createDefaultDocument();

            JEditorPane editorPane = new JEditorPane();

            doc.putProperty(Document.StreamDescriptionProperty, dob);

            UndoManager undoManager = (UndoManager) doc.getProperty(BaseDocument.UNDO_MANAGER_PROP);
            if (undoManager == null) {
                undoManager = (UndoManager) doc.getProperty(UndoManager.class);
            }
            if (undoManager == null) {
                undoManager = new UndoManager();
            }
            DialogBinding.bindComponentToFile(fob, 0, 0, editorPane);

            Component c = ((BaseTextUI) editorPane.getUI()).getEditorUI().getExtComponent();
            doc.addUndoableEditListener(undoManager);
            doc.putProperty(BaseDocument.UNDO_MANAGER_PROP, undoManager);
            doc.putProperty(UndoManager.class, undoManager); // For tests compatibility
//            editorPane.addFocusListener(new FocusAdapter() {
//
//                @Override
//                public void focusGained(FocusEvent e) {
//                    EditorApiPackageAccessor.get().register(editorPane);
//                }
//
//            });
            if (listener != null) {
                editorPane.getDocument().addDocumentListener(new DocumentListener() {

                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        listener.textValueChanged(new TextEvent(e.getDocument(), TextEvent.TEXT_FIRST));
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        listener.textValueChanged(new TextEvent(e.getDocument(), TextEvent.TEXT_FIRST));
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {

                    }
                });
            }
            editorPane.setText("");
            editorPane.getDocument().putProperty(SAMPLE_INPUT, sampleInput);
            panel.setLayout(new BorderLayout());
            panel.add(c);
            return editorPane;
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
        return null;
    }

//    
//    public static TopComponent getTopComponent(JPanel panel){
//        Container top=panel;
//        while(((top=panel.getParent())!=null) && !(top instanceof TopComponent) ){
//            
//        }
//        return (TopComponent) top;
//    }
    class VariableFinder implements CaretListener {

        private final JEditorPane pane;
        private final VariableHighlighterListener listener;

        private final Executor executor = new LatestEventExecutorService(Executors.newSingleThreadExecutor());

        public VariableFinder(JEditorPane pane, VariableHighlighterListener listener) {
            this.pane = pane;
            this.listener = listener;
        }

        @Override
        public void caretUpdate(CaretEvent e) {

            executor.execute(new Runnable() {
                @Override
                public void run() {

                    int start = e.getDot();
                    int checkEnd = Math.min(pane.getDocument().getLength(), start + MAX_VARIABLE_LENGTH + 3);
                    int checkStart = Math.max(0, start - MAX_VARIABLE_LENGTH - 3);
                    try {
                        String text = pane.getText(checkStart, checkEnd - checkStart);
                        int startPos = start - checkStart;

                        int exactEnd = text.indexOf('}', startPos);
                        if (exactEnd != -1) {
                            int exactStart = text.substring(0, exactEnd).lastIndexOf("${");
                            if (exactStart != -1) {
                                exactStart = exactStart + 2;
                                if ((exactStart + checkStart) <= start && (exactEnd + checkStart) >= start) {
                                    String var = text.substring(exactStart, exactEnd);
                                    listener.fireVariableChange(var, exactStart, exactEnd);
                                    return;
                                }
                            }
                        }
                    } catch (BadLocationException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                    listener.fireReset();
                }

            });

        }
    }
}

class LatestEventExecutorService implements Executor {

    private final Executor executor;
    // the field which keeps track of the latest available event to process
    private final AtomicReference<Runnable> latestEventReference = new AtomicReference<>();
    private final AtomicInteger activeTaskCount = new AtomicInteger(0);

    public LatestEventExecutorService(final Executor executor) {
        this.executor = executor;
    }

    @Override
    public void execute(final Runnable eventTask) {
        // update the latest event
        latestEventReference.set(eventTask);
        // read count _after_ updating event
        final int activeTasks = activeTaskCount.get();

        if (activeTasks == 0) {
            // there is definitely no other task to process this event, create a new task
            final Runnable customTask = new Runnable() {
                @Override
                public void run() {
                    // decrement the count for available tasks _before_ reading event
                    activeTaskCount.decrementAndGet();
                    // find the latest available event to process
                    final Runnable currentTask = latestEventReference.getAndSet(null);
                    if (currentTask != null) {
                        // if such an event exists, process it
                        currentTask.run();
                    } else {
                        // somebody stole away the latest event. Do nothing.
                    }
                }
            };
            // increment tasks count _before_ submitting task
            activeTaskCount.incrementAndGet();
            // submit the new task to the queue for processing
            executor.execute(customTask);
        }
    }

}
