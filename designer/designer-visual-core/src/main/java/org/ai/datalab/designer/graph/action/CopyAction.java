/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.designer.graph.action;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

/**
 *
 * @author Mohan Purushothaman
 */
public class CopyAction extends AbstractAction {

    public static final CopyAction INSTANCE = new CopyAction();

    @Override
    public void actionPerformed(ActionEvent e) {
            System.err.println(e);
    }

}
