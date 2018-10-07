/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.visual.impl.widget;

import java.awt.Point;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.SelectProvider;
import org.netbeans.api.visual.graph.GraphScene;
import org.netbeans.api.visual.model.ObjectScene;
import org.netbeans.api.visual.model.ObjectState;
import org.netbeans.api.visual.router.Router;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.Widget;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.nodes.PropertySupport;
import org.openide.nodes.Sheet;
import org.ai.datalab.core.Data;
import org.ai.datalab.visual.DataLabTheme;
import org.ai.datalab.visual.DataUtil;
import org.ai.datalab.visual.impl.FlowEdge;

/**
 *
 * @author Mohan Purushothaman
 */
public class ExecutionUnitConnectionWidget extends ConnectionWidget {

    // private final LabelWidget labelWidget;
    private final DataLabTheme theme;
    private final FlowEdge edge;

    public ExecutionUnitConnectionWidget(DataLabTheme theme, final ObjectScene scene, final FlowEdge edge, Router router) {
        super(scene);
        this.theme = theme;
        this.edge = edge;
        //this.labelWidget = new LabelWidget(scene,"");

        setRouter(router);

        //  addChild(labelWidget);
        // setConstraint(labelWidget, LayoutFactory.ConnectionWidgetLayoutAlignment.CENTER, 0.5f);
        getActions().addAction(ActionFactory.createSelectAction(new SelectProvider() {

            @Override
            public boolean isAimingAllowed(Widget widget, Point localLocation, boolean invertSelection) {
                return true;
            }

            @Override
            public boolean isSelectionAllowed(Widget widget, Point localLocation, boolean invertSelection) {
                return true;
            }

            @Override
            public void select(Widget widget, Point localLocation, boolean invertSelection) {
                scene.setFocusedObject(edge);
                if (edge != null) {
                    if (!invertSelection && scene.getSelectedObjects().contains(edge)) {
                        return;
                    }
                    scene.userSelectionSuggested(Collections.singleton(edge), invertSelection);
                } else {
                    scene.userSelectionSuggested(Collections.emptySet(), invertSelection);
                }
            }
        }, true));
        theme.installUI(this);
        setState(ObjectState.createNormal());
    }

    public FlowEdge getEdge() {
        return edge;
    }
//
//    public LabelWidget getLabelWidget() {
//        return labelWidget;
//    }
//
//    public void setLabel(String label) {
//        labelWidget.setLabel(label);
//    }

    @Override
    public void notifyStateChanged(ObjectState previousState, ObjectState state) {
        theme.updateUI(this, previousState, state);
    }

}
