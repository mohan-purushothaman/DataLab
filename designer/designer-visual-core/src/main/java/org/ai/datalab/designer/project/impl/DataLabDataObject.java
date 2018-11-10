/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.designer.project.impl;

import java.io.IOException;
import org.netbeans.api.actions.Savable;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.cookies.SaveCookie;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.MIMEResolver;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectExistsException;
import org.openide.loaders.MultiDataObject;
import org.openide.loaders.MultiFileLoader;
import org.openide.util.NbBundle.Messages;
import org.ai.datalab.designer.DataLabGraphDesigner;
import org.ai.datalab.designer.DataLabTree;

@Messages({
    "LBL_DataLab_LOADER=Files of DataLab"
})
@MIMEResolver.NamespaceRegistration(
        displayName = "#LBL_DataLab_LOADER",
        mimeType = DataLabDataObject.MIME_TYPE,
        elementNS = {DataLabTree.XMLNS}
)
@DataObject.Registration(
        mimeType = DataLabDataObject.MIME_TYPE,
        iconBase = "org/ai/datalab/designer/project/datalab.gif",
        displayName = "#LBL_DataLab_LOADER",
        position = 300
)
@ActionReferences({
    @ActionReference(
            path = "Loaders/text/datalab+xml/Actions",
            id = @ActionID(category = "System", id = "org.openide.actions.OpenAction"),
            position = 100,
            separatorAfter = 200
    ),
    @ActionReference(
            path = "Loaders/text/datalab+xml/Actions",
            id = @ActionID(category = "Edit", id = "org.openide.actions.CutAction"),
            position = 300
    ),
    @ActionReference(
            path = "Loaders/text/datalab+xml/Actions",
            id = @ActionID(category = "Edit", id = "org.openide.actions.CopyAction"),
            position = 400,
            separatorAfter = 500
    ),
    @ActionReference(
            path = "Loaders/text/datalab+xml/Actions",
            id = @ActionID(category = "Edit", id = "org.openide.actions.DeleteAction"),
            position = 600
    ),
    @ActionReference(
            path = "Loaders/text/datalab+xml/Actions",
            id = @ActionID(category = "System", id = "org.openide.actions.RenameAction"),
            position = 700,
            separatorAfter = 800
    ),
    @ActionReference(
            path = "Loaders/text/datalab+xml/Actions",
            id = @ActionID(category = "System", id = "org.openide.actions.SaveAsTemplateAction"),
            position = 900,
            separatorAfter = 1000
    ),
    @ActionReference(
            path = "Loaders/text/datalab+xml/Actions",
            id = @ActionID(category = "System", id = "org.openide.actions.FileSystemAction"),
            position = 1100,
            separatorAfter = 1200
    ),
    @ActionReference(
            path = "Loaders/text/datalab+xml/Actions",
            id = @ActionID(category = "System", id = "org.openide.actions.ToolsAction"),
            position = 1300
    ),
    @ActionReference(
            path = "Loaders/text/datalab+xml/Actions",
            id = @ActionID(category = "System", id = "org.openide.actions.PropertiesAction"),
            position = 1400
    )
})
public class DataLabDataObject extends MultiDataObject {

    public static final String MIME_TYPE = "text/datalab+xml";

    private SaveCookie savable;
    
    public DataLabDataObject(FileObject pf, MultiFileLoader loader) throws DataObjectExistsException, IOException {
        super(pf, loader);
        registerEditor(MIME_TYPE, true);
        
        //getCookieSet().assign(Openable.class, new DataLabOpenable(this));
    }

    @Override
    protected int associateLookup() {
        return 1;
    }

    public void setSavable(SaveCookie savable) {
        this.savable = savable;
    }
    
    
    

//    @MultiViewElement.Registration(
//            displayName = "#LBL_DataLab_EDITOR",
//            iconBase = "org/ai/datalab/designer/project/datalab.gif",
//            mimeType = "text/datalab+xml",
//            persistenceType = TopComponent.PERSISTENCE_ONLY_OPENED,
//            preferredID = "DataLab",
//            position = 1000
//    )
//    @Messages("LBL_DataLab_EDITOR=Source")
//    public static MultiViewEditorElement createEditor(Lookup lkp) {
//        return new MultiViewEditorElement(lkp);
//    }
    public void setUpdatedScene(DataLabGraphDesigner graphScene) {
        if(savable!=null){
        if (graphScene != null) {
            getCookieSet().assign(Savable.class, savable);
            setModified(true);
        } else {
            getCookieSet().remove(savable);
            setModified(false);
        }
        }
    }

}
