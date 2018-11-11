/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.visual.impl.widget;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import javax.swing.JProgressBar;
import java.awt.Color;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.widget.Scene;
import org.ai.datalab.core.resource.ResourceFactory;
import org.ai.datalab.core.resource.ResourcePool;
import org.ai.datalab.visual.DataLabVisualUtil;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.graph.GraphScene;
import org.netbeans.api.visual.vmd.VMDNodeWidget;
import org.netbeans.api.visual.widget.ComponentWidget;

/**
 *
 * @author Mohan Purushothaman
 */
public class ResourcesWidget extends VMDNodeWidget {

    private final Map<String, ResourceWidget> widgets = new ConcurrentHashMap<>();
    
    

    public ResourcesWidget(GraphScene scene) {
        super(scene);
        setNodeName("Resources Usage");
        //setLayout(LayoutFactory.createVerticalFlowLayout(LayoutFactory.SerialAlignment.LEFT_TOP, 5));
        getActions().addAction(ActionFactory.createMoveAction());
        getActions().addAction(scene.createObjectHoverAction());
    }

    public synchronized void addResource(String resource) {
        if (!widgets.containsKey(resource)) {
            ResourceWidget resourceWidget = new ResourceWidget(getScene(), resource,new JProgressBar());
            widgets.put(resource, resourceWidget);
            attachPinWidget(resourceWidget);
            DataLabVisualUtil.validateScene(getScene());
        }
    }

    public void updateResource() {
        for (Entry<String,ResourceWidget> r : widgets.entrySet()) {
            r.getValue().updateResource();
        }
    }

}

class ResourceWidget extends ComponentWidget {

    private final String resourceId;
    
    private final JProgressBar bar;
    

    public ResourceWidget(Scene scene, String resourceId,JProgressBar bar) {
        super(scene,bar);
        this.bar=bar;
        bar.setString("    "+resourceId+"    ");
        bar.setStringPainted(true);
        //bar.setForeground(Color.BLACK);
        bar.setBackground(Color.WHITE);
        bar.setOpaque(true);
        //AbbreviatedLabelWidget nameWidget = new AbbreviatedLabelWidget(scene, resourceId);
        //addChild(nameWidget);
//        resourceCount = new LabelWidget(scene, "? (?)");
//        resourceCount.setBorder(DataJobProgressWidget.successBorder);
//        resourceCount.setOpaque(true);
//        addChild(resourceCount);
        this.resourceId = resourceId;
        setLayout(LayoutFactory.createHorizontalFlowLayout(LayoutFactory.SerialAlignment.CENTER, 8));
    }

    void updateResource() {
        ResourcePool pool = ResourceFactory.getResourcePool(resourceId);
        int max=pool.getMaxCount();
        int cur=pool.getNumActive();
        bar.setMaximum(max);
        bar.setValue(cur);
        bar.setToolTipText(cur+" out of "+max +" used");
        //resourceCount.setLabel(pool.getNumActive() + " (" + pool.getMaxCount() + ")");
    }

}
