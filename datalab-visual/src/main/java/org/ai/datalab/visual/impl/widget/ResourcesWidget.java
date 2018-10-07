/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.visual.impl.widget;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.swing.SwingUtilities;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import org.ai.datalab.core.resource.ResourceFactory;
import org.ai.datalab.core.resource.ResourcePool;
import org.ai.datalab.visual.DataLabVisualUtil;

/**
 *
 * @author Mohan Purushothaman
 */
public class ResourcesWidget extends Widget {

    private final Map<String, ResourceWidget> widgets = new ConcurrentHashMap<>();

    public ResourcesWidget(Scene scene) {
        super(scene);
        setLayout(LayoutFactory.createVerticalFlowLayout(LayoutFactory.SerialAlignment.LEFT_TOP, 5));

    }

    public synchronized void addResource(String resource) {
        if (!widgets.containsKey(resource)) {
            ResourceWidget resourceWidget = new ResourceWidget(getScene(), resource);
            widgets.put(resource, resourceWidget);
            addChild(resourceWidget);
            DataLabVisualUtil.validateScene(getScene());
        }
    }

    public void updateResource(String... resourceId) {
        for (String r : resourceId) {
            widgets.get(r).updateResource();
        }
    }

}

class ResourceWidget extends Widget {

    //private final AbbreviatedLabelWidget nameWidget;
    private final LabelWidget resourceCount;
    private final String resourceId;

    public ResourceWidget(Scene scene, String resourceId) {
        super(scene);
        AbbreviatedLabelWidget nameWidget = new AbbreviatedLabelWidget(scene, resourceId);
        addChild(nameWidget);
        resourceCount = new LabelWidget(scene, "? (?)");
        resourceCount.setBorder(DataJobProgressWidget.successBorder);
        resourceCount.setOpaque(true);
        addChild(resourceCount);
        this.resourceId = resourceId;
        setLayout(LayoutFactory.createHorizontalFlowLayout(LayoutFactory.SerialAlignment.CENTER, 8));
    }

    void updateResource() {
        ResourcePool pool = ResourceFactory.getResourcePool(resourceId);
        resourceCount.setLabel(pool.getNumActive() + " (" + pool.getMaxCount() + ")");
    }

}
