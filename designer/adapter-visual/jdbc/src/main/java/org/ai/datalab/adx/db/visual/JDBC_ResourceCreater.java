/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.adx.db.visual;

import java.sql.Connection;
import javax.swing.JComponent;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.lookup.ServiceProvider;
import org.ai.datalab.core.resource.ResourcePool;
import org.ai.datalab.adx.db.visual.pool.JDBCPanel;
import org.ai.datalab.designer.visual.resource.ResourceCreator;

/**
 *
 * @author Mohan Purushothaman
 */
@ServiceProvider(service = ResourceCreator.class)
public class JDBC_ResourceCreater extends ResourceCreator<Connection> {

    @Override
    public ResourcePool<Connection> createResourcePool() {

        JDBCPanel panel = new JDBCPanel();
        NotifyDescriptor n = new DialogDescriptor(panel, "Create new JDBC Resource");
        if (NotifyDescriptor.YES_OPTION.equals(DialogDisplayer.getDefault().notify(n))) {
            return panel.getResourcePool();
        }
        return null;
    }

    @Override
    public String getDisplayName() {
        return "JDBC resources";
    }

    @Override
    public JComponent getDetailsPanel(ResourcePool pool) {
        return new JDBCPanel(true, pool);
    }

    @Override
    public Class<Connection> getResourceClass() {
        return Connection.class;
    }

}
