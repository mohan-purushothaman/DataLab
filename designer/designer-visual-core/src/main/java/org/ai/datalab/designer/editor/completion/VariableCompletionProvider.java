/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.designer.editor.completion;

import java.util.Map.Entry;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import org.netbeans.api.editor.mimelookup.MimeRegistration;
import org.netbeans.spi.editor.completion.CompletionProvider;
import org.netbeans.spi.editor.completion.CompletionResultSet;
import org.netbeans.spi.editor.completion.CompletionTask;
import org.netbeans.spi.editor.completion.support.AsyncCompletionQuery;
import org.netbeans.spi.editor.completion.support.AsyncCompletionTask;
import org.ai.datalab.core.Data;
import org.ai.datalab.designer.editor.SimpleEditor;

/**
 *
 * @author Mohan Purushothaman
 */
@MimeRegistration(mimeType = SimpleEditor.LANG_MIME_TYPE, service = CompletionProvider.class)

public class VariableCompletionProvider implements CompletionProvider {

    @Override
    public CompletionTask createTask(int queryType, JTextComponent jtc) {
        if (queryType != CompletionProvider.COMPLETION_QUERY_TYPE) {
            return null;
        }

        return new AsyncCompletionTask(new AsyncCompletionQuery() {
            @Override
            protected void query(CompletionResultSet completionResultSet, Document document, int caretOffset) {
                Data d = (Data) document.getProperty(SimpleEditor.SAMPLE_INPUT);

                for (Entry<String, Object> e : d.getEntrySet()) {
                    completionResultSet.addItem(new VariableCompletionItem(e.getKey(), caretOffset));
                }

                completionResultSet.finish();
            }
        },jtc);
    }

    @Override
    public int getAutoQueryTypes(JTextComponent component, String typedText) {
        return 0;
    }

}
