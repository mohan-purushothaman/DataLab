/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.visual.impl;

import java.lang.reflect.InvocationTargetException;
import org.netbeans.api.visual.graph.GraphScene;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.nodes.PropertySupport;
import org.openide.nodes.Sheet;
import org.ai.datalab.core.Data;
import org.ai.datalab.core.misc.DataUtil;
import org.ai.datalab.visual.DataLabVisualUtil;

/**
 *
 * @author Mohan Purushothaman
 */
public class FlowEdge {

    private final Boolean flowCondition;
    
    private Data data;

    public FlowEdge(Boolean flowCondition) {
        this.flowCondition = flowCondition;
    }

    public FlowEdge(Boolean flowCondition, Data data) {
        this.flowCondition = flowCondition;
        this.data = data;
    }
    
    public Boolean getFlowCondition() {
        return flowCondition;
    }

    public Data getData() {
        return data;
    }
    
    
    private transient Node node;

    private transient GraphScene graphScene;

    public Node getNode(GraphScene graph) {
        if (node == null) {
            node = createNode(graph);
            prepareNode(node);
        }

        return node;
    }

    private Node createNode(GraphScene graph) {

        Node newNode = new AbstractNode(Children.LEAF) {
            @Override
            protected Sheet createSheet() {
                Sheet sheet = Sheet.createDefault();
                Sheet.Set set = Sheet.createPropertiesSet();

                Data data = FlowEdge.this.getData();
                Sheet.Set fieldSheet = DataLabVisualUtil.getDataSheet(data, "Fields Available", "Properties");
                if (FlowEdge.this.getFlowCondition() != null) {
                    set.put(new PropertySupport.ReadOnly<Boolean>("Execute if Condition is True", Boolean.class, "Execute if Condition is True", "if ticked it would be true flow, If now it would be false flow") {

                        @Override
                        public Boolean getValue() throws IllegalAccessException, InvocationTargetException {
                            return FlowEdge.this.getFlowCondition();
                        }
                    });
                }

                set.setName("Sample Data");
                set.setDisplayName("Sample Data");
                set.setValue("tabName", "Properties");
                set.setPreferred(true);

                sheet.put(set);
                sheet.put(fieldSheet);
                return sheet;
            }

        };
        newNode.setDisplayName("Edge Details");
        this.graphScene = graph;
        return newNode;
    }

    private void prepareNode(Node node) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
