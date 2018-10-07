/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.designer.project;

import java.io.IOException;
import org.netbeans.api.project.Project;
import org.netbeans.spi.project.ProjectState;
import org.openide.filesystems.FileObject;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.lookup.Lookups;

/**
 *
 * @author Mohan
 */
public class DataLabProject implements Project {

    private final FileObject fileObject;
    private final ProjectState projectState;
    private Lookup lkp;

    public static final String PROJECT_FILE = "datalab.properties";
    private ProjectInfo projectInfo;

    public DataLabProject(FileObject fileObject, ProjectState projectState) {
        this.fileObject = fileObject;
        this.projectState = projectState;
        try {
            this.projectInfo = new ProjectInfo(this);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    public ProjectInfo getProjectInfo() {
        return projectInfo;
    }
    
    
    

    @Override
    public FileObject getProjectDirectory() {
        return fileObject;
    }

    public FileObject getProjectProperties() {
        return getProjectDirectory().getFileObject(PROJECT_FILE);
    }

    @Override
    public Lookup getLookup() {
        if (lkp == null) {
            lkp = Lookups.fixed(new Object[]{
                projectInfo,
                    new ViewProvider(this)
            });

        }
        return lkp;
    }

}
