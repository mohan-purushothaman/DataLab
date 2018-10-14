/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.adx.web.visual;

import javax.swing.JComponent;
import org.ai.datalab.adx.web.WebResourcePool;
import org.ai.datalab.adx.web.WebUrl;
import org.ai.datalab.core.resource.ResourcePool;
import org.ai.datalab.designer.visual.resource.ResourceCreator;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.Exceptions;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Mohan Purushothaman
 */
@ServiceProvider(service = ResourceCreator.class)
public class HttpResourceCreator extends ResourceCreator {

    @Override
    public JComponent getDetailsPanel(ResourcePool pool) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ResourcePool createResourcePool() {
        try {
            HttpResourcePoolCreatorPanel panel = new HttpResourcePoolCreatorPanel();
            DialogDescriptor dd = new DialogDescriptor(panel, "Create new web resource");
            Object result = DialogDisplayer.getDefault().notify(dd);
            if (result == NotifyDescriptor.OK_OPTION) {

                return new WebResourcePool(panel.getUrl(), panel.getMaxAllowed(), WebUrl.class);
            }
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }
        return null;

    }

    @Override
    public String getDisplayName() {
        return "Web Resource (http/https)";
    }

}
