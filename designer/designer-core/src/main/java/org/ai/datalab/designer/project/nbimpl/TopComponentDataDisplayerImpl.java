/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.designer.project.nbimpl;

import org.ai.datalab.visual.impl.widget.misc.DataDisplayer;
import org.ai.datalab.visual.impl.widget.misc.DataDisplayerCreator;
import org.openide.util.lookup.ServiceProvider;
import org.openide.windows.Mode;
import org.openide.windows.WindowManager;

@ServiceProvider(service = DataDisplayerCreator.class)
public class TopComponentDataDisplayerImpl implements DataDisplayerCreator {

    @Override
    public DataDisplayer displayData(String tabName) {
        DataDisplayerTopComponent c = new DataDisplayerTopComponent();

        Mode mode = WindowManager.getDefault().findMode("output");

        c.setName(tabName);
        c.open();
        mode.dockInto(c);
        c.requestActive();
        return c.getDataDisplayer();
    }

}
