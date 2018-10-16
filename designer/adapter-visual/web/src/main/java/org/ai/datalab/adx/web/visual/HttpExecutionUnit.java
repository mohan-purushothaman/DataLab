/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.adx.web.visual;

import org.ai.datalab.core.AbstractExecutorProvider;
import org.ai.datalab.core.Data;
import org.ai.datalab.visual.impl.widget.DescriptiveExecutionUnit;
import org.openide.nodes.Node;

/**
 *
 * @author Mohan Purushothaman
 */
public class HttpExecutionUnit extends DescriptiveExecutionUnit {

    public HttpExecutionUnit(String suggestedDescription, AbstractExecutorProvider provider, Data usedInputFields) {
        super(suggestedDescription, provider, usedInputFields);
    }

    @Override
    protected void prepareNode(Node node) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
