/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.designer;

import org.ai.datalab.designer.store.DataLabTreeNode;

/**
 *
 * @author Mohan
 */
public class DataLabTree {

    public final static String XMLNS = "org.ai.datalab.designer.DataLabTree";

    private DataLabTreeNode root;

    public DataLabTree() {
    }

    public DataLabTreeNode getRoot() {
        return root;
    }

    public void setRoot(DataLabTreeNode root) {
        this.root = root;
    }


}
