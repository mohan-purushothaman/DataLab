/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.visual;

import java.util.Collection;
import org.netbeans.api.visual.anchor.Anchor;
import org.netbeans.api.visual.graph.GraphScene;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.layout.LayoutFactory.ConnectionWidgetLayoutAlignment;
import org.netbeans.api.visual.widget.Widget;
import org.ai.datalab.core.Data;
import org.ai.datalab.core.ExecutorProvider;
import org.ai.datalab.core.builder.ExecutionUnit;
import org.ai.datalab.visual.impl.FlowEdge;
import org.ai.datalab.visual.impl.widget.misc.DataLabAnchor;


/**
 *
 * @author Mohan Purushothaman
 */
public class GraphUtil {

    public static FlowEdge getInputEdge(GraphScene<ExecutionUnit, FlowEdge> scene, ExecutionUnit node) {
        Collection<FlowEdge> inputEdges = scene.findNodeEdges(node, false, true);
        assert inputEdges.size() < 2;
        for (FlowEdge inputEdge : inputEdges) {
            return inputEdge;
        }
        return null;
    }

//    
//     public static DataEdge getOutputEdge(GraphScene<ExecutionUnit,DataEdge> scene,ExecutionUnit node) {
//        Collection<DataEdge> outputEdges = scene.findNodeEdges(node, true, false);
//        for (DataEdge outputEdge : outputEdges) {
//            return outputEdge;
//        }
//        return null;
//    }
//     

    public static Anchor getSourceAnchor(FlowEdge edge, Widget widget) {
        return new DataLabAnchor(widget,
                (edge.getFlowCondition() != null && !edge.getFlowCondition()) ? ConnectionWidgetLayoutAlignment.CENTER_RIGHT : ConnectionWidgetLayoutAlignment.BOTTOM_CENTER);

    }

    public static Anchor getTargetAnchor(FlowEdge edge, Widget widget) {

        return new DataLabAnchor(widget, ConnectionWidgetLayoutAlignment.TOP_CENTER);
    }
}
