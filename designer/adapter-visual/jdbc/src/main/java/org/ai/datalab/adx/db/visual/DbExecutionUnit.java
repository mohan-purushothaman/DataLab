/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.adx.db.visual;

import org.openide.nodes.Node;
import org.ai.datalab.adx.db.DB_Provider;
import org.ai.datalab.adx.db.DB_Reader;
import org.ai.datalab.core.AbstractExecutorProvider;
import org.ai.datalab.core.Data;
import org.ai.datalab.core.misc.Property;
import org.ai.datalab.core.misc.PropertyChangeListener;
import org.ai.datalab.visual.impl.widget.DescriptiveExecutionUnit;

/**
 *
 * @author Mohan Purushothaman
 */
public class DbExecutionUnit extends DescriptiveExecutionUnit {

    public static final Property QUERY = new Property("Query", "query", "query used", String.class);

    public DbExecutionUnit(String suggestedDescription, AbstractExecutorProvider provider, Data usedInputFields) {
        super(suggestedDescription, provider, usedInputFields);

        if (provider instanceof DB_Reader) {
            DB_Reader r = (DB_Reader) provider;
        }
        if (provider instanceof DB_Provider) {
            DB_Provider r = (DB_Provider) provider;
            setProperty(QUERY, r.getQuery(), null);

        }

    }

    @Override
    protected void prepareNode(Node node) {
        //TODO add node details
    }

}
