/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.designer.project;

import java.io.IOException;
import org.netbeans.api.project.Project;
import org.netbeans.spi.project.ProjectFactory; 
import org.netbeans.spi.project.ProjectState;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Mohan
 */

@ServiceProvider(service = ProjectFactory.class)
public class DataLabProjectFactory implements ProjectFactory{

    
    
    @Override
    public boolean isProject(org.openide.filesystems.FileObject fo) {
        return fo.getFileObject(DataLabProject.PROJECT_FILE)!=null;
    }

    @Override
    public Project loadProject(final org.openide.filesystems.FileObject fo, ProjectState ps) throws IOException {
        return isProject(fo)?new DataLabProject(fo, ps):null;
    }

    @Override
    public void saveProject(Project prjct) throws IOException, ClassCastException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
