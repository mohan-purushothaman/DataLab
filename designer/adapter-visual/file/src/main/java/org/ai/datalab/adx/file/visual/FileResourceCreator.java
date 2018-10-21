/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.adx.file.visual;

import java.io.File;
import javax.swing.JComponent;
import org.openide.filesystems.FileChooserBuilder;
import org.openide.util.Exceptions;
import org.openide.util.lookup.ServiceProvider;
import org.ai.datalab.adx.file.FileResourcePool;
import org.ai.datalab.core.resource.ResourcePool;
import org.ai.datalab.designer.visual.resource.ResourceCreator;

/**
 *
 * @author Mohan Purushothaman
 */
@ServiceProvider(service = ResourceCreator.class)
public class FileResourceCreator extends ResourceCreator<File> {

    @Override
    public JComponent getDetailsPanel(ResourcePool pool) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ResourcePool createResourcePool() {
        File f = new FileChooserBuilder(ResourceCreator.class).showSaveDialog();
        if (f != null) {
            try {
                return new FileResourcePool(f);
            } catch (Exception ex) {
                Exceptions.printStackTrace(ex);

            }
        }
        return null;
    }

    @Override
    public String getDisplayName() {
        return "File Resource";
    }

    @Override
    public Class<File> getResourceClass() {
        return File.class;
    }

}
