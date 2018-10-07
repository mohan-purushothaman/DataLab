/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.designer.project;

import java.awt.Image;
import javax.swing.Action;
import org.netbeans.spi.project.ui.support.CommonProjectActions;
import org.openide.nodes.FilterNode;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.util.lookup.Lookups;
import org.openide.util.lookup.ProxyLookup;

/**
 *
 * @author Mohan
 */
public class DataLabProjectNode extends FilterNode{
    private final DataLabProject project;

    public DataLabProjectNode(Node node,DataLabProject project) {
        super(node, new FilterNode.Children(node),new ProxyLookup(new Lookup[]{Lookups.fixed(node,project),node.getLookup()}));
        this.project=project;
    }

    @Override
    public Action[] getActions(boolean context) {
        return new Action[]{
            CommonProjectActions.newFileAction(),
            CommonProjectActions.copyProjectAction(),
            CommonProjectActions.deleteProjectAction(),
            CommonProjectActions.closeProjectAction()
        };
    }

   
    

    @Override
    public Image getOpenedIcon(int type) {
        return getIcon(type);
    }

    @Override
    public Image getIcon(int type) {
       return project.getProjectInfo().getIcon().getImage();
    }

    @Override
    public String getDisplayName() {
        return project.getProjectInfo().getDisplayName();
    }

    
    
    
    
}
