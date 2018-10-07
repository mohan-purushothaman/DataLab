/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.designer.graph.action;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.swing.AbstractAction;
import javax.swing.text.DefaultEditorKit;
import org.netbeans.api.visual.widget.Scene;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.datatransfer.ExClipboard;
import org.openide.util.lookup.Lookups;
import org.openide.util.lookup.ServiceProvider;
import org.openide.windows.TopComponent;
import org.ai.datalab.visual.impl.widget.DescriptiveAbstractNode;

/**
 *
 * @author Mohan Purushothaman
 */
public class CutAction extends AbstractAction {

    public static final CutAction INSTANCE = new CutAction();

    @Override
    public void actionPerformed(ActionEvent e) {
        Node[] currentNodes = TopComponent.getRegistry().getCurrentNodes();
        if(currentNodes.length>0){
            Node node=currentNodes[0];
            if(node instanceof DescriptiveAbstractNode){
                try {
                    Transferable t=((DescriptiveAbstractNode)node).clipboardCut();
                    Clipboard systemClipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    systemClipboard.setContents(t, null);
                    System.err.println(systemClipboard.getData(DataFlavor.stringFlavor));
                } catch (Exception ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
        }
        
        
        
        
        
        
    }

}
