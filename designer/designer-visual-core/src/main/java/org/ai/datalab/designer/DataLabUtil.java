/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.designer;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.QNameMap;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import java.util.Collection;
import org.netbeans.api.actions.Editable;
import org.netbeans.api.visual.widget.Widget;
import org.openide.util.lookup.InstanceContent;
import org.ai.datalab.core.executor.ExecutorType;
import org.ai.datalab.designer.store.DataLabTreeNode;
import org.ai.datalab.visual.impl.FlowEdge;
import org.ai.datalab.visual.impl.theme.FlowChartTheme;
import org.ai.datalab.visual.impl.widget.DescriptiveExecutionUnit;


/**
 *
 * @author Mohan
 */
public class DataLabUtil {

    public static String toXml(DataLabGraphDesigner graph) {
        XStream xStream = getStream();
        return xStream.toXML(getTree(graph));

    }

    public static DataLabTree getTree(DataLabGraphDesigner graph) {
        DescriptiveExecutionUnit readerProvider = graph.findNode(ExecutorType.READER);
        DataLabTree tree = new DataLabTree();
        copyTree(graph, tree, readerProvider, null);
        return tree;
    }

    private static void copyTree(DataLabGraphDesigner graph, DataLabTree tree, DescriptiveExecutionUnit provider, DataLabTreeNode parent) {
        if (provider != null) {
            Widget w = graph.findWidget(provider);
            Boolean flowCondition = null;
            if (parent != null) {
                Collection<FlowEdge> edges = graph.findEdgesBetween(parent.getProvider(), provider);
                assert edges.size() == 1;
                flowCondition = edges.iterator().next().getFlowCondition();
            }
            DataLabTreeNode node = new DataLabTreeNode(provider, parent, w.getLocation(), flowCondition);
            if (parent == null) {
                tree.setRoot(node);
            }
            for (FlowEdge edge : graph.findNodeEdges(provider, true, false)) {
                DescriptiveExecutionUnit p = graph.getEdgeTarget(edge);
                copyTree(graph, tree, p, node);
            }
        }
    }

    public static DataLabGraphDesigner fromXml(InstanceContent lkpContent, Editable e, String content) throws Exception {
        try{
        DataLabTree tree = (DataLabTree) getStream().fromXML(content);
        DataLabGraphDesigner graph = new DataLabGraphDesigner(new FlowChartTheme() ,lkpContent, e);

        addToScene(graph, tree.getRoot(), null);
        if(tree.getRoot() ==null){
            graph.loadSampleGraph();
        }
        return graph;
        }catch(Exception ex){
            throw new Exception("Not able to read the file (create by old version)",ex);
        }
    }

    private static XStream getStream() {
        QNameMap qmap = new QNameMap();
        qmap.setDefaultNamespace(DataLabTree.XMLNS);

        XStream xStream = new XStream(new StaxDriver(qmap));
        xStream.alias("Tree", DataLabTree.class);
        xStream.alias("Node", DataLabTreeNode.class);
        return xStream;
    }

    private static void addToScene(DataLabGraphDesigner graph, DataLabTreeNode currentNode, DataLabTreeNode parentNode) {
        if (currentNode != null) {
            graph.createNode(currentNode.getProvider(), currentNode.getLocation().x, currentNode.getLocation().y, parentNode != null ? parentNode.getProvider() : null, currentNode.getFlowCondition());
            for (DataLabTreeNode c : currentNode.getChildList()) {
                addToScene(graph, c, currentNode);
            }
        }
    }
}
