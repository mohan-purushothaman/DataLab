/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.visual.impl;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.EnumSet;
import java.util.concurrent.Future;
import javax.swing.Action;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.progress.ProgressHandleFactory;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.anchor.Anchor;
import org.netbeans.api.visual.graph.GraphScene;
import org.netbeans.api.visual.graph.layout.GraphLayout;
import org.netbeans.api.visual.graph.layout.GraphLayoutFactory;
import org.netbeans.api.visual.graph.layout.GraphLayoutSupport;
import org.netbeans.api.visual.router.Router;
import org.netbeans.api.visual.router.RouterFactory;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Widget;
import org.openide.util.Cancellable;
import org.openide.util.ImageUtilities;
import org.openide.windows.IOColorLines;
import org.openide.windows.IOProvider;
import org.openide.windows.IOSelect;
import org.openide.windows.InputOutput;
import org.ai.datalab.core.Data;

import org.ai.datalab.core.DataJob;
import org.ai.datalab.core.DataLabJobListener;
import org.ai.datalab.core.DefaultExecutionUnit;
import org.ai.datalab.core.ExecutionConfig;
import org.ai.datalab.core.ExecutionResult;
import org.ai.datalab.core.JobState;
import org.ai.datalab.core.builder.ExecutionUnit;
import org.ai.datalab.core.resource.ResourcePool;
import org.ai.datalab.visual.DataLabTheme;
import org.ai.datalab.visual.DataLabVisualUtil;
import org.ai.datalab.visual.GraphUtil;
import org.ai.datalab.visual.impl.widget.DataJobConnectionWidget;
import org.ai.datalab.visual.impl.widget.DataJobProgressWidget;
import org.ai.datalab.visual.impl.widget.ResourcesWidget;
import org.ai.datalab.visual.impl.widget.TimeWidget;

/**
 *
 * @author Mohan Purushothaman
 */
public final class DataLabListenerGraph extends GraphScene<ExecutionUnit, FlowEdge> implements DataLabJobListener {

    private final LayerWidget mainLayer = new LayerWidget(this);
    private final LayerWidget connectionLayer = new LayerWidget(this);
    private final LayerWidget backgroundLayer = new LayerWidget(this);
    private final DataJob job;
    private final Router router;

    private final GraphLayout<ExecutionUnit, FlowEdge> sceneLayout;

    private final DataLabTheme theme;

    private static Paint PAINT_BACKGROUND;

    private Future<?> runningJob;

    private final ProgressHandle handle;

    private final String jobName;

    private final TimeWidget timeWidget;

    private final ResourcesWidget resourceWidgets;

    private final InputOutput io;

    static {
        try {
            Image sourceImage = ImageUtilities.loadImage("paper_grid_progress.png");
            int width = sourceImage.getWidth(null);
            int height = sourceImage.getHeight(null);

            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics = image.createGraphics();
            graphics.drawImage(sourceImage, 0, 0, null);
            graphics.dispose();
            PAINT_BACKGROUND = new Color(255, 250, 250);//new TexturePaint(image, new Rectangle(0, 0, width, height));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public DataLabListenerGraph(DataJob job, DataLabTheme theme, String jobName) throws Exception {
        this(job, theme, job.getName(), null);
    }

    //private final static WidgetAction moveAction = ActionFactory.createMoveAction();
    public DataLabListenerGraph(DataJob job, DataLabTheme theme, String jobName, Action openAction) throws Exception {
        this.job = job;
        this.theme = theme;
        this.jobName = jobName;

        setOpaque(true);
        setBackground(PAINT_BACKGROUND);

        router = RouterFactory.createDirectRouter();//RouterFactory.createOrthogonalSearchRouter(mainLayer, connectionLayer);
        sceneLayout = GraphLayoutFactory.createTreeGraphLayout(100, 100, 50, 100, true);
        resourceWidgets = new ResourcesWidget(this);
        init(job);
        io = IOProvider.getDefault().getIO(getJobName(), true);
        IOSelect.select(io, EnumSet.of(IOSelect.AdditionalOperation.OPEN));

        addListeners();
        getActions().addAction(createObjectHoverAction());
        getActions().addAction(ActionFactory.createRectangularSelectAction(this, backgroundLayer));
        handle = ProgressHandleFactory.createHandle(jobName, new Cancellable() {

            @Override
            public boolean cancel() {
                interruptJob();
                return true;
            }
        }, openAction);
        addChild(mainLayer);
        addChild(connectionLayer);
        handle.start();
        handle.switchToIndeterminate();

        timeWidget = new TimeWidget(this);

        mainLayer.addChild(timeWidget);
        mainLayer.addChild(resourceWidgets);
        timeWidget.setPreferredLocation(new Point(10, 10));
        resourceWidgets.setPreferredLocation(new Point(200, 10));
        DataLabVisualUtil.validateScene(this);
        EventQueue.invokeLater(() -> {
            sceneLayout.layoutGraph(DataLabListenerGraph.this);
        });

    }

    public String getJobName() {
        return jobName;
    }

    public void interruptJob() {
        runningJob.cancel(true);
        handle.finish();
    }

    private void init(DataJob job) {
        copyToScene(job, (DefaultExecutionUnit) job.getReaderUnit());
    }

    @Override
    protected Widget attachNodeWidget(ExecutionUnit node) {
        DataJobProgressWidget widget = new DataJobProgressWidget(node, this, theme);
        mainLayer.addChild(widget);
        widget.getActions().addAction(createObjectHoverAction());
        widget.getActions().addAction(createSelectAction());
        return widget;
    }

    @Override
    protected Widget attachEdgeWidget(FlowEdge edge) {
        DataJobConnectionWidget connectionWidget = new DataJobConnectionWidget(theme, this, edge, router);
        connectionLayer.addChild(connectionWidget);
        connectionWidget.getActions().addAction(createSelectAction());

        return connectionWidget;
    }

    @Override
    protected void attachEdgeSourceAnchor(FlowEdge edge, ExecutionUnit oldSourceNode, ExecutionUnit sourceNode) {
        DataJobProgressWidget sourceNodeWidget = (DataJobProgressWidget) findWidget(sourceNode);
        Anchor sourceAnchor = GraphUtil.getSourceAnchor(edge, sourceNodeWidget);
        ConnectionWidget edgeWidget = (ConnectionWidget) findWidget(edge);
        edgeWidget.setSourceAnchor(sourceAnchor);
    }

    @Override
    protected void attachEdgeTargetAnchor(FlowEdge edge, ExecutionUnit oldTargetNode, ExecutionUnit targetNode) {
        DataJobProgressWidget targetNodeWidget = (DataJobProgressWidget) findWidget(targetNode);
        Anchor targetAnchor = GraphUtil.getTargetAnchor(edge, targetNodeWidget);
        ConnectionWidget edgeWidget = (ConnectionWidget) findWidget(edge);
        edgeWidget.setTargetAnchor(targetAnchor);
    }

    private void copyToScene(DataJob job, DefaultExecutionUnit currentNode) {
        //System.out.println("copying currentNode=" + currentNode + " , parentNodeId=" + parent + " , edgeId=" + edge);

        DataJobProgressWidget progressNode = createNode(currentNode, 100, 100);

        if (currentNode.getParent() == null) {
            GraphLayoutSupport.setTreeGraphLayoutRootNode(sceneLayout, currentNode);
        }

        for (ExecutionUnit unit : currentNode.getChilds()) {
            copyToScene(job, (DefaultExecutionUnit) unit);
        }

        progressNode.setProgressHandler(new DataLabProgressHandler(this, currentNode));
    }

    public DataJobProgressWidget createNode(ExecutionUnit unit, int x, int y) {
        //System.out.println("creating node nodeID="+nodeID+" , parentPinId="+parentPinId+" , edgeId="+edgeId);
        DataJobProgressWidget widget = (DataJobProgressWidget) addNode(unit);
        //widget.setNodeName(visualNode.getNodeDescription());
        widget.setPreferredLocation(new Point(x, y));
        ExecutionUnit parentNode = unit.getParent();
        if (parentNode != null) {
            FlowEdge edge = new FlowEdge(((DefaultExecutionUnit) unit).getInputQueue().getFlowCondition());
            addEdge(edge);
            setEdgeSource(edge, parentNode);
            setEdgeTarget(edge, unit);
        }
        //widget.getActions().addAction(moveAction); 
        //NOT A GOOD IDEA adding move action, dirty redraws
        return widget;
    }

    private DataLabProgressHandler getProgressHandler(ExecutionUnit primitive) {
        return ((DataJobProgressWidget) findWidget(primitive)).getProgressHandler();
    }

    @Override
    public void jobStarting(ExecutionUnit provider, ExecutionConfig config) {
        getProgressHandler(provider).jobStarted();
        ResourcePool resourcePool = config.getResourcePool();
        if (resourcePool != null) {
            resourceWidgets.addResource(resourcePool.getResourceId());
        }

    }

    @Override
    public void jobEnded(ExecutionUnit primitive) {
        getProgressHandler(primitive).jobEnded();
    }

    @Override
    public void jobInterrupted(ExecutionUnit primitive, int threadNo, Throwable t) {
        try {
            IOColorLines.println(io, primitive + "(" + threadNo + ") : error occured ", Color.RED);
        } catch (IOException ex) {
            ex.printStackTrace(getErrorWriter());
        }

        t.printStackTrace(getErrorWriter());

    }

    @Override
    public void updateProgress(ExecutionUnit primitive, int threadNo, ExecutionResult result,Data... outputData) {
        getProgressHandler(primitive).updateProgress(result,outputData);
    }

    @Override
    public void updateErrorProgress(ExecutionUnit primitive, int threadNo, Throwable t, Data... data) {
        getProgressHandler(primitive).updateErrorProgress(data.length, t, data);
        t.printStackTrace();
    }

    @Override
    public void updateState(ExecutionUnit primitive, int threadNo, JobState state) {
        getProgressHandler(primitive).updateState(state);

        try {
            IOColorLines.println(io, primitive + "(" + threadNo + ") : state updated to " + state, theme.getColor(state));
        } catch (IOException ex) {
            ex.printStackTrace(getErrorWriter());
        }

    }

    public void updateResource(String[] resourceId) {
        resourceWidgets.updateResource(resourceId);
    }

    private void addListeners() {

    }

    @Override
    public void jobEnded() {
        handle.finish();
        timeWidget.complete();

    }

    @Override
    public void jobInterrupted(Throwable e) {

        e.printStackTrace(getErrorWriter());

        jobEnded();
    }

    public PrintWriter getWriter() {
        return io.getOut();
    }

    public PrintWriter getErrorWriter() {
        return io.getErr();
    }

    public Future<?> startJob() throws Exception {
        runningJob = job.startJob(this);
        return runningJob;
    }

    @Override
    public void initCompleted() {
        sceneLayout.layoutGraph(DataLabListenerGraph.this);
    }

}
