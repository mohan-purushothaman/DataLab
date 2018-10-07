/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.designer.project.impl;

import org.netbeans.api.actions.Openable;
import org.netbeans.core.api.multiview.MultiViews;
import org.openide.windows.CloneableTopComponent;

/**
 *
 * @author Mohan Purushothaman
 */
public class DataLabOpenable implements Openable {

    private final DataLabDataObject dob;

    private CloneableTopComponent tp;

    public DataLabOpenable(DataLabDataObject dob) {
        this.dob = dob;
    }

    @Override
    public void open() {
        if (tp == null) {
            tp = MultiViews.createCloneableMultiView(DataLabDataObject.MIME_TYPE, dob);
        }
        tp.open();
        tp.requestActive();
    }
    
    public void editorClosed(){
        tp=null;
    }

}
