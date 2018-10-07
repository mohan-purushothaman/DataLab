/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.designer.project;

import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.Properties;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectInformation;
import org.openide.util.ImageUtilities;

/**
 *
 * @author Mohan
 */
public class ProjectInfo implements ProjectInformation{

    private final DataLabProject project;
    private Properties props=new Properties();
    
    private static final ImageIcon PROJECT_ICON=new ImageIcon(ImageUtilities.loadImage("org/ai/datalab/designer/project/datalab.gif"));

    public ProjectInfo(DataLabProject project) throws IOException {
        this.project = project;
        props.load(project.getProjectProperties().getInputStream());
    }
    
    @Override
    public String getName() {
        return project.getProjectDirectory().getName();
    }

    @Override
    public String getDisplayName() {
        return getName();
    }

    @Override
    public ImageIcon getIcon() {
        return PROJECT_ICON;
    }

    @Override
    public Project getProject() {
        return project;
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener pl) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener pl) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
