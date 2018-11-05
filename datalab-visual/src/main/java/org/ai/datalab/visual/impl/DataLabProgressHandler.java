/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.visual.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.openide.util.Exceptions;
import org.ai.datalab.core.Data;
import org.ai.datalab.core.DefaultExecutionUnit;
import org.ai.datalab.core.ExecutionResult;
import org.ai.datalab.core.JobState;
import org.ai.datalab.core.builder.ExecutionUnit;
import org.ai.datalab.visual.DataLabVisualUtil;
import org.ai.datalab.visual.GraphUtil;
import org.ai.datalab.visual.impl.widget.DataJobConnectionWidget;
import org.ai.datalab.visual.impl.widget.DataJobProgressWidget;
import org.ai.datalab.visual.impl.widget.misc.DataDisplayer;
import org.ai.datalab.visual.impl.widget.misc.SwingUpdater;

/**
 *
 * @author Mohan Purushothaman
 */
public class DataLabProgressHandler extends SwingUpdater<Void> {

    private final CumulativeResult result=new CumulativeResult();
    
    
    private final AtomicLong errorCount = new AtomicLong();

    private final DataJobProgressWidget nodeWidget;

    private final DataJobConnectionWidget inputNodeWidget;
    private final List<DataJobConnectionWidget> outputNodeWidget;

    private final List<Data> errorData = Collections.synchronizedList(new ArrayList<Data>());


    private final DataLabListenerGraph scene;

    private final ExecutionUnit unit;

    private final List<DataDisplayer> progressListeners=new LinkedList<>();
    
    
    
    
    public DataLabProgressHandler(DataLabListenerGraph scene, ExecutionUnit unit) {
        this.scene = scene;
        this.unit = unit;
        this.nodeWidget = (DataJobProgressWidget) scene.findWidget(unit);
        this.inputNodeWidget = (DataJobConnectionWidget) scene.findWidget(GraphUtil.getInputEdge(scene, unit));

        Collection<FlowEdge> outputEdges = scene.findNodeEdges(unit, true, false);
        this.outputNodeWidget = new ArrayList<>(outputEdges.size());
        for (FlowEdge edge : outputEdges) {
            outputNodeWidget.add((DataJobConnectionWidget) scene.findWidget(edge));
        }

    }

    public ExecutionUnit getUnit() {
        return unit;
    }

   

    public AtomicLong getErrorCount() {
        return errorCount;
    }

    public DataJobProgressWidget getNodeWidget() {
        return nodeWidget;
    }

    public ConnectionWidget getInputNodeWidget() {
        return inputNodeWidget;
    }

    public void jobStarted() {

//        executorService = Executors.newSingleThreadScheduledExecutor();
//
//        executorService.scheduleWithFixedDelay(new Runnable() {
//
//            @Override
//            public void run() {
//                publish(null);
//
//            }
//        }, UPDATE_TIME_IN_MILLIS, UPDATE_TIME_IN_MILLIS, TimeUnit.MILLISECONDS);
        if (inputNodeWidget != null) {
            inputNodeWidget.setQueue(((DefaultExecutionUnit) unit).getInputQueue());
        }
        //updateAsOutputQueue(inputNodeWidget, inputQueue);
        //rschedule draw only if any update present
        nodeWidget.init();
    }

    public void jobEnded() {
        publish(null);
        //executorService.shutdown();
    }
    
    

    public void updateProgress(ExecutionResult executionResult,Data... outputData) {
         result.update(executionResult);
         for (DataDisplayer d : progressListeners) {
            d.addData(outputData);
        }
         publish(null);
    }

   

    public void updateErrorProgress(int errorProgressedUnits, Throwable t, Data... data) {
        errorCount.addAndGet(errorProgressedUnits);
        publish(null);
        for (Data d : data) {
            try {
                d.setValue("#ERROR_OCCURED#", null,t.getMessage() == null ? "Nullpointer exception" : t.getMessage());
            } catch (Exception ex) {
                Exceptions.printStackTrace(ex);
            }
            errorData.add(d);
        }

        t.printStackTrace();
    }

    public List<Data> getErrorData() {
        return errorData;
    }

    private static final int UPDATE_TIME_IN_MILLIS = 1000;

    @Override
    protected void process(AtomicReference<Void> val) {
//        //System.out.println("running swing update");
        if (inputNodeWidget != null) {
            inputNodeWidget.update();
        }
        for (DataJobConnectionWidget out : outputNodeWidget) {
            out.update();
        }

        nodeWidget.setSuccessProgress(String.valueOf(result.getProcessedCount()));
        nodeWidget.setAvg(getTimeString(result.getAvgProcessTime()));
        nodeWidget.setErrorProgress(String.valueOf(errorCount.get()));
        //TODO resource update using more optimized approach (update based on SCHEDULE or some logic)
        scene.updateResource(result.getResourceSet());
        DataLabVisualUtil.validateScene(scene);
    }

//    private void updateAsOutputQueue(AutomationConnectionProgressWidget inputNodeWidget, AutomationBlockingQueue inputQueue) {
//        if (inputQueue != null) {
//            PrimitiveBlockProvider source = scene.getEdgeSource((DataEdge) scene.findObject(inputNodeWidget));
//            scene.getProgressHandler(source).addOutputWidget(inputNodeWidget);
//        }
//    }
    public void updateState(JobState state) {
        nodeWidget.updateState(state);
    }

    private String getTimeString(long nanoSeconds) {
        //TODO add two levels
        long ms=TimeUnit.NANOSECONDS.toMillis(nanoSeconds);
        if(ms==0){
            return nanoSeconds+" ns";
        }
        long sec=TimeUnit.MILLISECONDS.toSeconds(ms);
        if(sec==0){
            return ms+" ms";
        }
        
        long min=TimeUnit.SECONDS.toMinutes(sec);
        
        if(min==0){
            return sec+" s";
        }
        
        return min+" m "+(sec%60)+" s"; 
        
    }

    public void addProgressListener(DataDisplayer dataDisplayer) {
        progressListeners.add(dataDisplayer);
    }
}
