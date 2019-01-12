/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.visual;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.Future;
import java.util.logging.Level;
import javax.swing.JComponent;
import org.ai.datalab.core.Data;
import org.netbeans.api.visual.widget.Scene;
import org.ai.datalab.core.DataJob;
import org.ai.datalab.visual.impl.DataLabListenerGraph;
import org.ai.datalab.visual.impl.RunFrame;
import org.ai.datalab.visual.impl.theme.FlowChartTheme;
import org.openide.nodes.PropertySupport;
import org.openide.nodes.Sheet;
import org.openide.util.Exceptions;

/**
 *
 * @author Mohan Purushothaman
 */
public class DataLabVisualUtil {

    public static void visualize(DataJob job) throws Exception {
        startVisualize(job).get();
    }

    public static Future<?> startVisualize(DataJob job) throws Exception {
        DataLabListenerGraph l = new DataLabListenerGraph(job, new FlowChartTheme(), "Test Theme ");
        
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
            //Exceptions.printStackTrace(Exceptions.attachSeverity(e, Level.INFO));
        }
    }

    
    public static Sheet.Set getDataSheet(final Data data, String displayName, String tabName) {
        if (data != null) {
            Sheet.Set set = Sheet.createPropertiesSet();

            for (final String d : data.getKeyNames()) {
                set.put(new PropertySupport.ReadOnly<String>(d, String.class, d, d) {

                    @Override
                    public String getValue() throws IllegalAccessException, InvocationTargetException {
                        return String.valueOf(data.getValue(d));
                    }
                });
            }
            set.setName(displayName);
            set.setDisplayName(displayName);
            set.setValue("tabName", tabName);
            return set;
        }
        return null;
    }

}
