/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.designer.store;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;
import javax.swing.text.Position;
import javax.swing.tree.TreeNode;
import org.ai.datalab.core.builder.ExecutionUnit;
import org.ai.datalab.visual.impl.widget.DescriptiveExecutionUnit;

/**
 *
 * @author Mohan
 */
public class DataLabTreeNode {

    private final DescriptiveExecutionUnit provider;
    private final DataLabTreeNode parent;
    private final List<DataLabTreeNode> childList = new LinkedList<>();  //order matters in condition flow , do not use unordered lists
    private final Point location;
    /*
     null - no flow based condition
     true - positive flow
     false - negative flow
     */

    private final Boolean flowCondition;

    

    public DataLabTreeNode(DescriptiveExecutionUnit provider, DataLabTreeNode parent, Point location, Boolean flowCondition) {
        this.provider = provider;
        this.parent = parent;
        this.location = location;
        if (parent != null) {
            parent.addChild(this);
        }
        this.flowCondition = flowCondition;
    }

    public boolean addChild(DataLabTreeNode e) {
        return childList.add(e);
    }

    public DescriptiveExecutionUnit getProvider() {
        return provider;
    }

    public DataLabTreeNode getParent() {
        return parent;
    }

    public List<DataLabTreeNode> getChildList() {
        return childList;
    }

    public int getChildCount() {
        return childList.size();
    }

    public Point getLocation() {
        return location;
    }

    public Boolean getFlowCondition() {
        return flowCondition;
    }
    
    

}
