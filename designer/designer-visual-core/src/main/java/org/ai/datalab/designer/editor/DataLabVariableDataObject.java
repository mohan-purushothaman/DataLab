/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.designer.editor;

import java.io.IOException;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.MIMEResolver;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectExistsException;
import org.openide.loaders.MultiDataObject;
import org.openide.loaders.MultiFileLoader;
import org.openide.util.NbBundle.Messages;

@Messages({
    "LBL_DataLabVariable_LOADER=Files of DataLabVariable"
})
@MIMEResolver.ExtensionRegistration(
        displayName = "#LBL_DataLabVariable_LOADER",
        mimeType = SimpleEditor.LANG_MIME_TYPE,
        extension = {SimpleEditor.TMP_EXTN}
)
@DataObject.Registration(
        mimeType = SimpleEditor.LANG_MIME_TYPE,
        displayName = "#LBL_DataLabVariable_LOADER",
        position = 300
)

public class DataLabVariableDataObject extends MultiDataObject {

    public DataLabVariableDataObject(FileObject pf, MultiFileLoader loader) throws DataObjectExistsException, IOException {
        super(pf, loader);
        registerEditor(SimpleEditor.LANG_MIME_TYPE, false);
    }

    @Override
    protected int associateLookup() {
        return 1;
    }

}
