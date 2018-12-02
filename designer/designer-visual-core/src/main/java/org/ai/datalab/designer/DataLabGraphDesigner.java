/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.designer;

import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.event.UndoableEditEvent;
import org.netbeans.api.actions.Editable;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.EditProvider;
import org.netbeans.api.visual.action.PopupMenuProvider;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.anchor.Anchor;
import org.netbeans.api.visual.graph.GraphScene;
import org.netbeans.api.visual.graph.layout.GraphLayout;
import org.netbeans.api.visual.graph.layout.GraphLayoutFactory;
import org.netbeans.api.visual.graph.layout.GraphLayoutSupport;
import org.netbeans.api.visual.model.ObjectSceneEvent;
import org.netbeans.api.visual.model.ObjectSceneEventType;
import org.netbeans.api.visual.model.ObjectSceneListener;
import org.netbeans.api.visual.model.ObjectState;
import org.netbeans.api.visual.router.Router;
import org.netbeans.api.visual.router.RouterFactory;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import org.openide.actions.PropertiesAction;
import org.openide.awt.UndoRedo;
import org.openide.nodes.Node;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;
import org.openide.util.actions.SystemAction;
import org.openide.util.lookup.InstanceContent;
import org.ai.datalab.core.Data;
import org.ai.datalab.core.DataJob;
import org.ai.datalab.core.adx.misc.MappingHelper;
import org.ai.datalab.core.adx.misc.SingleMapping;
import org.ai.datalab.core.adx.misc.ValueConverter;
import org.ai.datalab.core.builder.ConditionExecutionUnit;
import org.ai.datalab.core.builder.ExecutionUnit;
import org.ai.datalab.core.executor.ExecutorType;
import org.ai.datalab.core.misc.Configuration;
import org.ai.datalab.core.misc.FixedData;
import org.ai.datalab.core.misc.PropertyHandler;
import org.ai.datalab.designer.core.ConnectorWizardIteratorInterface;
import org.ai.datalab.visual.DataLabTheme;
import org.ai.datalab.visual.GraphUtil;
import org.ai.datalab.visual.impl.widget.DescriptiveExecutionUnit;
import org.ai.datalab.visual.impl.widget.ExecutionUnitConnectionWidget;
import org.ai.datalab.visual.impl.widget.ExecutionUnitWidget;
import org.ai.datalab.visual.impl.FlowEdge;
import org.ai.datalab.designer.misc.UndoableDeleteEdit;
import org.ai.datalab.designer.misc.UndoableMoveStrategy;
import org.ai.datalab.visual.DataLabVisualUtil;

/**
 *
 * @author Mohan Purushothaman
 */
public class DataLabGraphDesigner extends GraphScene<DescriptiveExecutionUnit, FlowEdge> implements Editable{

    private final LayerWidget backgroundLayer = new LayerWidget(this);
    private final LayerWidget mainLayer = new LayerWidget(this);
    private final LayerWidget connectionLayer = new LayerWidget(this);

    private final static WidgetAction moveControlPointAction = ActionFactory.createOrthogonalMoveControlPointAction();

    private final UndoRedo.Manager manager;

    private final WidgetAction moveAction;

    private final InstanceContent lkpContent;

    private final Router router;

    private final GraphLayout sceneLayout;

    private final DataLabTheme theme;

    private final static Paint PAINT_BACKGROUND;

    static {
        Image sourceImage = ImageUtilities.loadImage("paper_grid.png"); // NOI18N
        int width = sourceImage.getWidth(null);
        int height = sourceImage.getHeight(null);
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();
        graphics.drawImage(sourceImage, 0, 0, null);
        graphics.dispose();
        PAINT_BACKGROUND = new TexturePaint(image, new Rectangle(0, 0, width, height));
    }

    private final Editable editable;

    public DataLabGraphDesigner(DataLabTheme theme, InstanceContent lkpContent, Editable editable) {
        this.lkpContent = lkpContent;
        this.editable = editable;
        this.theme = theme;
        setOpaque(true);
        setBackground(PAINT_BACKGROUND);
        addChild(backgroundLayer);
        addChild(mainLayer);
        addChild(connectionLayer);
        manager = new UndoRedo.Manager();
        UndoableMoveStrategy str = new UndoableMoveStrategy(manager);
        moveAction = ActionFactory.createMoveAction(str, str);
        //router = RouterFactory.createDirectRouter();
        router = RouterFactory.createOrthogonalSearchRouter(mainLayer, connectionLayer);

        getActions().addAction(ActionFactory.createZoomAction());
        getActions().addAction(ActionFactory.createPanAction());
        getActions().addAction(ActionFactory.createRectangularSelectAction(this, backgroundLayer));
        sceneLayout = GraphLayoutFactory.createTreeGraphLayout(100, 100, 50, 10, true);
        addActions();
        addListeners();
    }

    public void setModified() {
        edit();
    }
    
    public void edit(){
        editable.edit();
    }
    

    public UndoRedo getUndoRedo() {
        return manager;
    }

    @Override
    protected Widget attachNodeWidget(DescriptiveExecutionUnit node) {
        ExecutionUnitWidget widget = new ExecutionUnitWidget(node, this, theme);
        mainLayer.addChild(widget);
        addActions(widget, node);
        return widget;
    }

    @Override
    protected Widget attachEdgeWidget(FlowEdge edge) {
        ExecutionUnitConnectionWidget connectionWidget = new ExecutionUnitConnectionWidget(theme, this, edge, router);
        connectionLayer.addChild(connectionWidget);
        connectionWidget.getActions().addAction(createObjectHoverAction());
        connectionWidget.getActions().addAction(createSelectAction());
        connectionWidget.getActions().addAction(moveControlPointAction);
        return connectionWidget;
    }

    @Override
    protected void attachEdgeSourceAnchor(FlowEdge edge, DescriptiveExecutionUnit oldSourceNode, DescriptiveExecutionUnit sourceNode) {
        Widget sourceNodeWidget = findWidget(sourceNode);
        Anchor sourceAnchor = GraphUtil.getSourceAnchor(edge, sourceNodeWidget);
        ConnectionWidget edgeWidget = (ConnectionWidget) findWidget(edge);
        edgeWidget.setSourceAnchor(sourceAnchor);
        DescriptiveExecutionUnit edgeTarget = getEdgeTarget(edge);
        if(edgeTarget!=null){
            edgeTarget.setParent(sourceNode);
        }
    }

    @Override
    protected void attachEdgeTargetAnchor(FlowEdge edge, DescriptiveExecutionUnit oldTargetNode, DescriptiveExecutionUnit targetNode) {
        Widget targetNodeWidget = findWidget(targetNode);
        Anchor targetAnchor = GraphUtil.getTargetAnchor(edge, targetNodeWidget);
        ConnectionWidget edgeWidget = (ConnectionWidget) findWidget(edge);
        edgeWidget.setTargetAnchor(targetAnchor);
    }

    public ExecutionUnitWidget createNode(DescriptiveExecutionUnit executionUnit, int x, int y, DescriptiveExecutionUnit parent, Boolean flowCondition) {

        if (executionUnit.getProvidingType() == ExecutorType.READER) {
            assert findNode(ExecutorType.READER) == null : "Reader already present";
            GraphLayoutSupport.setTreeGraphLayoutRootNode(sceneLayout, executionUnit);
        }

        ExecutionUnitWidget widget = (ExecutionUnitWidget) addNode(executionUnit);
        widget.setPreferredLocation(new Point(x, y));
        //visualNode.prepareWidget(this, widget);
        if (parent != null) {
            executionUnit.setParent(parent);
            FlowEdge edge = new FlowEdge(flowCondition, parent.getFinalOutputData());
            addEdge(edge);
            setEdgeSource(edge, parent);
            setEdgeTarget(edge, executionUnit);
        }
        return widget;
    }

    public void layoutScene() {
        DataLabVisualUtil.validateScene(this);
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                sceneLayout.layoutGraph(DataLabGraphDesigner.this);
            }
        });

    }

    private final String PROCESS_TEXT = "Process using";
    private final String WRITE_TEXT = "Write to";
    private final String CONDITION_TEXT = "Add Condition";

    private void addActions(ExecutionUnitWidget w, final DescriptiveExecutionUnit node) {

        w.getActions().addAction(ActionFactory.createPopupMenuAction(new PopupMenuProvider() {

            @Override
            public JPopupMenu getPopupMenu(Widget widget, Point localLocation) {
                JPopupMenu popup = new JPopupMenu();

                if (node.getProvidingType() == ExecutorType.READER || node.getProvidingType() == ExecutorType.PROCESSOR) {
                    popup.add(new WidgetMenuItem(DataLabGraphDesigner.this, PROCESS_TEXT, localLocation, createAddProcessorAction(node, localLocation, null)));
                    popup.add(new WidgetMenuItem(DataLabGraphDesigner.this, CONDITION_TEXT, localLocation, createAddConditionAction(node, localLocation, null)));
                    popup.add(new WidgetMenuItem(DataLabGraphDesigner.this, WRITE_TEXT, localLocation, createAddWriterAction(node, localLocation, null)));
                } else if (node.getProvidingType() == ExecutorType.CONDITION) {
                    JMenu truemenu = new JMenu("If True");
                    truemenu.add(new WidgetMenuItem(DataLabGraphDesigner.this, PROCESS_TEXT, localLocation, createAddProcessorAction(node, localLocation, Boolean.TRUE)));
                    truemenu.add(new WidgetMenuItem(DataLabGraphDesigner.this, CONDITION_TEXT, localLocation, createAddConditionAction(node, localLocation, Boolean.TRUE)));
                    truemenu.add(new WidgetMenuItem(DataLabGraphDesigner.this, WRITE_TEXT, localLocation, createAddWriterAction(node, localLocation, Boolean.TRUE)));

                    JMenu falsemenu = new JMenu("If False");
                    falsemenu.add(new WidgetMenuItem(DataLabGraphDesigner.this, PROCESS_TEXT, localLocation, createAddProcessorAction(node, localLocation, Boolean.FALSE)));
                    falsemenu.add(new WidgetMenuItem(DataLabGraphDesigner.this, CONDITION_TEXT, localLocation, createAddConditionAction(node, localLocation, Boolean.FALSE)));
                    falsemenu.add(new WidgetMenuItem(DataLabGraphDesigner.this, WRITE_TEXT, localLocation, createAddWriterAction(node, localLocation, Boolean.FALSE)));

                    popup.add(truemenu);
                    popup.add(falsemenu);

                }
                popup.add(new WidgetMenuItem(DataLabGraphDesigner.this, "Delete", localLocation, createDeleteAction(node, localLocation)));
                // TODO  enable only after full redesign, enabled :)
                popup.add(new WidgetMenuItem(DataLabGraphDesigner.this, "Edit", localLocation, createEditAction(node, localLocation)));
                popup.add(new WidgetMenuItem(DataLabGraphDesigner.this, "Properties", localLocation, SystemAction.get(PropertiesAction.class)));

                return popup;
            }

        }));

        w.getActions().addAction(createObjectHoverAction());
        w.getActions().addAction(createSelectAction());
        w.getActions().addAction(moveAction);
    }

    private void addActions() {

        getActions().addAction(ActionFactory.createPopupMenuAction(new PopupMenuProvider() {

            @Override
            public JPopupMenu getPopupMenu(Widget widget, Point localLocation) {
//                if (AutomationGraph.this.findNode(Reader.class) != null) {
//                    return null;
//                }
                JPopupMenu popup = new JPopupMenu();
                WidgetMenuItem widgetMenuItem = new WidgetMenuItem(DataLabGraphDesigner.this, "Read from ", localLocation, createAddReaderAction(localLocation));
                popup.add(widgetMenuItem);
                widgetMenuItem.setEnabled(findNode(ExecutorType.READER) == null);
                return popup;
            }
        }
        ));

        getActions().addAction(ActionFactory.createEditAction(new EditProvider() {
            @Override
            public void edit(Widget widget) {
                DataLabGraphDesigner.this.layoutScene();
            }
        }));
    }

    private ActionListener createAddReaderAction(final Point localLocation) {

        return new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                DescriptiveExecutionUnit visualNode = Lookup.getDefault().lookup(ConnectorWizardIteratorInterface.class).getVisualNode(ExecutorType.READER, null);
                if (visualNode != null) {
                    ExecutionUnitWidget widget = DataLabGraphDesigner.this.createNode(visualNode, localLocation.x, localLocation.y, null, null);
                    GraphLayoutSupport.setTreeGraphLayoutRootNode(sceneLayout, visualNode);
                    layoutScene();
                }
            }
        };
    }

    private ActionListener createAddProcessorAction(final DescriptiveExecutionUnit node, final Point localLocation, final Boolean flowCondition) {
        return new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                //TODO
                //GraphUtil.getWholeDataAfter(DataLabGraphDesigner.this, node)
                DescriptiveExecutionUnit visualNode = Lookup.getDefault().lookup(ConnectorWizardIteratorInterface.class)
                        .getVisualNode(ExecutorType.PROCESSOR, getFullOutputMapping(node));
                if (visualNode != null) {
                    ExecutionUnitWidget widget = DataLabGraphDesigner.this.createNode(visualNode, localLocation.x, localLocation.y + 100, node, flowCondition);
                    layoutScene();
                }
            }
        };
    }

    private ActionListener createAddWriterAction(final DescriptiveExecutionUnit node, final Point localLocation, final Boolean flowCondition) {
        return new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                DescriptiveExecutionUnit visualNode = Lookup.getDefault().lookup(ConnectorWizardIteratorInterface.class)
                        .getVisualNode(ExecutorType.WRITER, getFullOutputMapping(node));
                if (visualNode != null) {
                    ExecutionUnitWidget widget = DataLabGraphDesigner.this.createNode(visualNode, localLocation.x, localLocation.y + 100, node, flowCondition);
                    layoutScene();
                }
            }
        };
    }

    private ActionListener createAddConditionAction(final DescriptiveExecutionUnit node, final Point localLocation, final Boolean flowCondition) {
        return new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                DescriptiveExecutionUnit visualNode = Lookup.getDefault().lookup(ConnectorWizardIteratorInterface.class)
                        .getVisualNode(ExecutorType.CONDITION, getFullOutputMapping(node));
                if (visualNode != null) {
                    ExecutionUnitWidget widget = DataLabGraphDesigner.this.createNode(visualNode, localLocation.x, localLocation.y + 100, node, flowCondition);
                    layoutScene();
                }
            }
        };
    }

    private ActionListener createDeleteAction(final DescriptiveExecutionUnit node, Point localLocation) {

        return new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                List<UndoableDeleteEdit> editEvents = new LinkedList<UndoableDeleteEdit>();
                removeNodeWithDependency(node, editEvents, true);

                for (UndoableDeleteEdit editEvent : editEvents) {
                    manager.undoableEditHappened(new UndoableEditEvent(node, editEvent));
                }

                DataLabVisualUtil.validateScene(DataLabGraphDesigner.this);
            }
        };
    }

    private ActionListener createEditAction(final DescriptiveExecutionUnit node, final Point localLocation) {
        return new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                DescriptiveExecutionUnit parent = (DescriptiveExecutionUnit) node.getParent();

                DescriptiveExecutionUnit visualNode = Lookup.getDefault().lookup(ConnectorWizardIteratorInterface.class).getVisualNode(node.getProvidingType(), getFullOutputMapping(parent), node);
                if (visualNode != null) {
                    replaceNode(node, visualNode);
                    layoutScene();
                }
            }

        };
    }

    private MappingHelper getFullOutputMapping(DescriptiveExecutionUnit node) {
        if (node == null) {
            return null;
        }

        Map<String, SingleMapping> mapping = new HashMap<>();
        DescriptiveExecutionUnit currentNode = node;
        while (currentNode != null) {

            MappingHelper m = currentNode.getMapping();

            if (m != null) {
                for (Object temp : m.getIdList(null)) {
                    SingleMapping s = (SingleMapping) temp;
                    if (!mapping.containsKey(s.getFieldKey())) {
                        mapping.put(s.getFieldKey(), s);
                    }
                }
            }

            currentNode = (DescriptiveExecutionUnit) currentNode.getParent();
        }
        MappingHelper mappingHelper = new MappingHelper();

        for (SingleMapping s : mapping.values()) {
            mappingHelper.addIdMap("", s.getFieldKey(), s.getConverter(), s.getSampleValue());
        }
        return mappingHelper;
    }

    private void replaceNode(DescriptiveExecutionUnit node, DescriptiveExecutionUnit visualNode) {
        Point l = findWidget(node).getLocation();
        Collection<FlowEdge> outputEdges = findNodeEdges(node, true, false);
        Collection<FlowEdge> inputEdges = findNodeEdges(node, false, true);
        ExecutionUnitWidget widget = (ExecutionUnitWidget) addNode(visualNode);
        widget.setPreferredLocation(l);
        for (FlowEdge inputEdge : inputEdges) {
            setEdgeTarget(inputEdge, visualNode);
        }

        Data oldData = node.getFinalOutputData();
        Data newData = visualNode.getFinalOutputData();
//TODO assert old and new data are same

        for (FlowEdge outputEdge : outputEdges) {
            setEdgeSource(outputEdge, visualNode);
        }

        removeNode(node);

        if (visualNode.getProvidingType() == ExecutorType.READER) {
            assert findNode(ExecutorType.READER) == null || node.getProvidingType() == ExecutorType.READER : "Reader already present";
            GraphLayoutSupport.setTreeGraphLayoutRootNode(sceneLayout, visualNode);
        }

        //replace All dataedges data
    }

    public void removeNodeWithDependency(DescriptiveExecutionUnit node, List<UndoableDeleteEdit> list, boolean remove) {

        //System.out.println("called with " + nodeID);
        Point location = findWidget(node).getPreferredLocation();
        for (FlowEdge edge : findNodeEdges(node, true, false)) {
            removeNodeWithDependency(getEdgeTarget(edge), list, remove);
        }
        //System.out.println("removing node " + nodeID);
        Collection<FlowEdge> inputEdges = findNodeEdges(node, false, true);
        DescriptiveExecutionUnit parent = null;
        for (FlowEdge edge : inputEdges) {
            parent = getEdgeSource(edge);
            if (remove) {
                removeEdge(edge);
            }
        }
        if (remove) {
            removeNode(node);
        }
        if (list != null) {
            list.add(new UndoableDeleteEdit(this, node, parent, location, inputEdges.isEmpty() ? null : inputEdges.iterator().next().getFlowCondition()));
        }
    }

    public DescriptiveExecutionUnit findNode(ExecutorType type) {
        for (DescriptiveExecutionUnit node : getNodes()) {
            if (node.getProvidingType() == type) {
                return node;
            }
        }
        return null;
    }

    public DataLabTheme getTheme() {
        return theme;
    }

    public DataJob createDataJob(String jobName) {
        DataJob job = DataJob.getJob(jobName, new Configuration());

        DescriptiveExecutionUnit reader = findNode(ExecutorType.READER);
        if (reader != null) {
            addToJob(job, reader, null, null);
        }

        return job;
    }

    private void addToJob(DataJob job, DescriptiveExecutionUnit currentNode, ExecutionUnit parent, FlowEdge edgeFromParent) {
        ExecutionUnit unit;
        switch (currentNode.getProvidingType()) {
            case READER:
                unit = job.setReader(currentNode.getDescription(), currentNode.getExecutorProvider());
                break;
            default:
                if (parent instanceof ConditionExecutionUnit) {
                    unit = ((ConditionExecutionUnit) parent).addExecutor(currentNode.getDescription(), currentNode.getExecutorProvider(), edgeFromParent.getFlowCondition());
                } else {
                    unit = parent.addExecutor(currentNode.getDescription(), currentNode.getExecutorProvider());
                }
                break;
        }

        if (unit instanceof PropertyHandler) {
            ((PropertyHandler) unit).copyProperties(currentNode);
        }

        for (FlowEdge outEdge : findNodeEdges(currentNode, true, false)) {
            addToJob(job, getEdgeTarget(outEdge), unit, outEdge);
        }

    }

    private void addListeners() {

        addObjectSceneListener(new ObjectSceneListener() {

            @Override
            public void objectAdded(ObjectSceneEvent event, Object addedObject) {
                setModified();
            }

            @Override
            public void objectRemoved(ObjectSceneEvent event, Object removedObject) {
                setModified();
            }

            @Override
            public void objectStateChanged(ObjectSceneEvent event, Object changedObject, ObjectState previousState, ObjectState newState) {

            }

            @Override
            public void selectionChanged(ObjectSceneEvent event, Set<Object> previousSelection, Set<Object> newSelection) {
                List<Object> list = new LinkedList<Object>();
                for (Object o : newSelection) {

                    if (o instanceof DescriptiveExecutionUnit) {
                        DescriptiveExecutionUnit visualNode = (DescriptiveExecutionUnit) o;
                        Node node = visualNode.getNode(DataLabGraphDesigner.this);
                        list.add(node);
                    }

                    if (o instanceof FlowEdge) {
                        FlowEdge edge = (FlowEdge) o;
                        Node node = edge.getNode(DataLabGraphDesigner.this);
                        list.add(node);
                    }
                }

                lkpContent.set(list, null);
            }

            @Override
            public void highlightingChanged(ObjectSceneEvent event, Set<Object> previousHighlighting, Set<Object> newHighlighting) {
            }

            @Override
            public void hoverChanged(ObjectSceneEvent event, Object previousHoveredObject, Object newHoveredObject) {
            }

            @Override
            public void focusChanged(ObjectSceneEvent event, Object previousFocusedObject, Object newFocusedObject) {
            }
        }, ObjectSceneEventType.OBJECT_SELECTION_CHANGED, ObjectSceneEventType.OBJECT_ADDED, ObjectSceneEventType.OBJECT_REMOVED);
    }

    public void loadSampleGraph() {
//        int size = 50000;
//        NumberReader numberReader = new NumberReader(size);
//
//        NumberProcessor numberProcessor1 = new NumberProcessor(); 
//        NumberProcessor numberProcessor12 = new NumberProcessor();
//        NumberProcessor numberProcessor2 = new NumberProcessor();
//        NumberProcessor numberProcessor21 = new NumberProcessor();
//        NumberWriter numberWriter = new NumberWriter(size, ".01.01");
//        NumberWriter numberWriter1 = new NumberWriter(size, ".01.01");
//
//        AutomationWidget readerNode = AutomationGraph.this.createNode(numberReader, 200, 200, null, null);
//
//        AutomationGraph.this.createNode(numberProcessor1, 100, 300, numberReader, null);
//        AutomationGraph.this.createNode(numberProcessor12, 500, 300, numberReader, null);
//
//        AutomationGraph.this.createNode(numberProcessor2, 100, 400, numberProcessor1, null);
//        AutomationGraph.this.createNode(numberProcessor21, 500, 400, numberProcessor12, null);
//
//        AutomationGraph.this.createNode(numberWriter, 100, 500, numberProcessor2, null);
//        AutomationGraph.this.createNode(numberWriter1, 500, 500, numberProcessor21, null);
//        layoutScene();
    }
//
//    private void mergeDiffs(FlowEdge edge, Data diffData) {
//        if (edge != null) {
//            //Data.mergeData(edge.getData(), diffData)
//            //TODO 
//            //edge.setData(new SimpleData(), this);
//            ((AutomationConnectionWidget) findWidget(edge)).setLabel(DataEdge.getDataFieldName(edge.getData().getFieldsNames()));
//            PrimitiveBlockProvider target = getEdgeTarget(edge);
//            if (target != null) {
//                for (DataEdge outEdge : findNodeEdges(target, true, false)) {
//                    mergeDiffs(outEdge, diffData);
//                }
//            }
//        }
//    }

    public List<UndoableDeleteEdit> cut(DescriptiveExecutionUnit node) {
        List<UndoableDeleteEdit> editEvents = new LinkedList<>();
        removeNodeWithDependency(node, editEvents, true);
        return editEvents;
    }

    public List<UndoableDeleteEdit> copy(DescriptiveExecutionUnit node) {
        List<UndoableDeleteEdit> editEvents = new LinkedList<>();
        removeNodeWithDependency(node, editEvents, false);
        return editEvents;
    }

    class WidgetMenuItem extends JMenuItem {

        public WidgetMenuItem(final Scene scene, final String name, final Point loc, ActionListener listener) {
            super(name);
            if (listener != null) {
                addActionListener(listener);
            }
        }

    }

}
