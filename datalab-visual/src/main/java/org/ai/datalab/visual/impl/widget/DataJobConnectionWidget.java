/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.visual.impl.widget;

import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.model.ObjectScene;
import org.netbeans.api.visual.model.ObjectState;
import org.netbeans.api.visual.router.Router;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.LabelWidget;
import org.ai.datalab.core.DataLabQueue;

import org.ai.datalab.visual.DataLabTheme;
import org.ai.datalab.visual.impl.FlowEdge;

/**
 *
 * @author Mohan Purushothaman
 */
public class DataJobConnectionWidget extends ConnectionWidget {

    private final LabelWidget labelWidget;
    private final DataLabTheme theme;
    private final FlowEdge edge;

    public DataJobConnectionWidget(DataLabTheme theme, final ObjectScene scene, final FlowEdge edge, Router router) {
        super(scene);
        this.theme = theme;
        this.edge = edge;
        this.labelWidget = new LabelWidget(scene, "awaiting input");

        setRouter(router);

        addChild(this.labelWidget);
        setConstraint(this.labelWidget, LayoutFactory.ConnectionWidgetLayoutAlignment.CENTER, 0.5f);
        
        
        theme.installUI(this);
        setState(ObjectState.createNormal());
    }
    
    private DataLabQueue queue;


    public void setQueue(DataLabQueue queue) {
        this.queue = queue;
    }

    public void update() {
        if (queue != null) {
            setLabel(String.valueOf(queue.size()));
        }
    }

    public FlowEdge getEdge() {
        return edge;
    }

    public LabelWidget getLabelWidget() {
        return labelWidget;
    }

    public void setLabel(String label) {
        labelWidget.setLabel(label);
    }

    @Override
    public void notifyStateChanged(ObjectState previousState, ObjectState state) {
        theme.updateUI(this, previousState, state);
    }

}
