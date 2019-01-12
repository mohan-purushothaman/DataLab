/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.designer.graph.action;

import java.awt.Point;
import org.ai.datalab.visual.impl.widget.DescriptiveExecutionUnit;
import org.netbeans.api.visual.action.ConnectDecorator;
import org.netbeans.api.visual.action.ConnectProvider;
import org.netbeans.api.visual.action.ConnectorState;
import org.netbeans.api.visual.anchor.Anchor;
import org.netbeans.api.visual.anchor.AnchorFactory;
import org.netbeans.api.visual.anchor.AnchorShape;
import org.netbeans.api.visual.graph.GraphScene;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.modules.visual.action.ConnectAction;

/**
 *
 * @author Mohan Purushothaman
 */
public class PopupConnectAction extends ConnectAction {

    public PopupConnectAction(Widget interractionLayer,Widget sourceWidget) {
        super(new ConnectDecorator() {
            public ConnectionWidget createConnectionWidget(Scene scene) {
                ConnectionWidget widget = new ConnectionWidget(scene);
                widget.setTargetAnchorShape(AnchorShape.TRIANGLE_FILLED);
                return widget;
            }

            public Anchor createSourceAnchor(Widget sourceWidget) {
                return AnchorFactory.createCenterAnchor(sourceWidget);
            }

            public Anchor createTargetAnchor(Widget targetWidget) {
                return AnchorFactory.createCenterAnchor(targetWidget);
            }

            public Anchor createFloatAnchor(Point location) {
                return AnchorFactory.createFixedAnchor(location);
            }
        }, interractionLayer, new ConnectProvider() {
            private GraphScene scene = (GraphScene) interractionLayer.getScene();

            @Override
            public boolean isSourceWidget(Widget sourceWidget) {
                return true;
            }

            @Override
            public ConnectorState isTargetWidget(Widget sourceWidget, Widget targetWidget) {
                return ConnectorState.ACCEPT;
            }

            @Override
            public boolean hasCustomTargetWidgetResolver(Scene scene) {
                return false;
            }

            @Override
            public Widget resolveTargetWidget(Scene scene, Point sceneLocation) {
                return null;
            }

            @Override
            public void createConnection(Widget sourceWidget, Widget targetWidget) {
                DescriptiveExecutionUnit source = (DescriptiveExecutionUnit) scene.findObject(sourceWidget);
                DescriptiveExecutionUnit target = (DescriptiveExecutionUnit) scene.findObject(targetWidget);
                target.addParent(source);

            }
        });
    }

}
