/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.designer.project;

import org.netbeans.spi.project.ui.LogicalViewProvider;
import org.openide.*;
import org.openide.loaders.DataFolder;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;

/**
 *
 * @author Mohan
 */
public class ViewProvider implements LogicalViewProvider{

    private final DataLabProject project;

    public ViewProvider(DataLabProject project) {
        this.project = project;
    }
    
    
    
    
    
    @Override
    public Node createLogicalView() {
        try {
            return new DataLabProjectNode(DataFolder.find(project.getProjectDirectory()).getNodeDelegate(),project);
        } catch (DataObjectNotFoundException ex) {
            Exceptions.printStackTrace(ex);
            return new AbstractNode(Children.LEAF);
        }
    }

    @Override
    public Node findPath(org.openide.nodes.Node node, Object o) {
        return null;
    }
    
}
