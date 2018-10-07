/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.visual;

import java.util.concurrent.Future;
import java.util.logging.Level;
import javax.swing.JComponent;
import org.netbeans.api.visual.widget.Scene;
import org.openide.util.Exceptions;
import org.ai.datalab.core.DataJob;
import org.ai.datalab.visual.impl.DataLabListenerGraph;
import org.ai.datalab.visual.impl.RunFrame;
import org.ai.datalab.visual.impl.theme.FlowChartTheme;

/**
 *
 * @author Mohan Purushothaman
 */
public class DataLabVisualUtil {

    public static void visualize(DataJob job) throws Exception {
        startVisualize(job).get();
    }
    
    public static Future<?> startVisualize(DataJob job) throws Exception {
        DataLabListenerGraph l = new DataLabListenerGraph(job, new FlowChartTheme(), "Test Threme ");
        
        JComponent view = l.getView();
        if (view == null) {
            view = l.createView();
        }
        RunFrame r = new RunFrame();
        r.setView(view);
        r.setVisible(true);
        validateScene(l);

        return l.startJob();
    }

//    public static void createNewDesigner(){
//        DataLabGraphDesigner l = new DataLabGraphDesigner(new FlowChartTheme(), new InstanceContent(), new EditCookie() {
//            @Override
//            public void edit() {
//                System.err.println("edit");
//            }
//        });
//        JComponent view = l.getView();
//        if (view == null) {
//            view = l.createView();
//        }
//        RunFrame r = new RunFrame();
//        r.setView(view);
//
//        r.setVisible(true);
//       
//    }
    public static void validateScene(Scene scene) {
        try {
            scene.validate();
        } catch (Exception e) {
            Exceptions.printStackTrace(Exceptions.attachSeverity(e, Level.INFO));
        }
    }

}
