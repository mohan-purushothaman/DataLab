/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.adx.java.visual.simple;

import org.ai.datalab.adx.java.visual.*;
import org.openide.nodes.Node;
import org.ai.datalab.adx.java.core.JavaExecutorProvider;
import org.ai.datalab.core.Data;
import org.ai.datalab.visual.impl.widget.DescriptiveExecutionUnit;

/**
 *
 * @author Mohan Purushothaman
 */
public class SimpleJavaExecutionUnit extends DescriptiveExecutionUnit {

    public SimpleJavaExecutionUnit(String suggestedDescription, JavaExecutorProvider provider, Data usedInputFields) {
        super(suggestedDescription, provider, usedInputFields);
    }

    @Override
    protected void prepareNode(Node node) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
