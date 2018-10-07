/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.designer.project.impl;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import org.openide.cookies.CloseCookie;
import org.openide.cookies.SaveCookie;
import org.ai.datalab.designer.DataLabGraphDesigner;
import org.ai.datalab.designer.DataLabUtil;

/**
 *
 * @author Mohan Purushothaman
 */
public class DataLabSavable implements SaveCookie {

    private final DataLabGraphDesigner graphScene;
    private final DataLabDataObject dob;

    public DataLabSavable(DataLabGraphDesigner graphScene, DataLabDataObject dob) {
        this.graphScene = graphScene;
        this.dob = dob;
    }

    @Override
    public void save() throws IOException {
        try (BufferedWriter bf = new BufferedWriter(new OutputStreamWriter(dob.getPrimaryFile().getOutputStream()))) {
            bf.write(DataLabUtil.toXml(graphScene));
        }
        dob.setUpdatedScene(null);
    }

}
