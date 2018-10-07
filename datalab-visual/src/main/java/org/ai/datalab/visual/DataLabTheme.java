/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.visual;

import java.awt.Color;
import org.netbeans.api.visual.model.ObjectState;
import org.ai.datalab.core.JobState;
import org.ai.datalab.visual.impl.widget.DataJobConnectionWidget;
import org.ai.datalab.visual.impl.widget.DataJobProgressWidget;
import org.ai.datalab.visual.impl.widget.ExecutionUnitConnectionWidget;
import org.ai.datalab.visual.impl.widget.ExecutionUnitWidget;


/**
 *
 * @author Mohan Purushothaman
 */
public abstract class DataLabTheme {

    public abstract void installUI(DataJobConnectionWidget widget);

    public abstract void updateUI(DataJobConnectionWidget widget, ObjectState previousState, ObjectState state);

    public abstract void installUI(DataJobProgressWidget widget);

    public abstract void updateUI(DataJobProgressWidget widget, ObjectState previousState, ObjectState state);

    public static final Color green = new Color(0, 128, 0);
    public static final Color red = new Color(128, 0, 0);

    public Color getFlowConditionColor(Boolean condition) {
        return condition == null ? Color.BLACK : (condition ? green : red);
    }

    public abstract Color getColor(JobState state);

    public abstract void installUI(ExecutionUnitWidget widget);

    public abstract void updateUI(ExecutionUnitWidget widget, ObjectState previousState, ObjectState state);

    public abstract void installUI(ExecutionUnitConnectionWidget widget);

    public abstract void updateUI(ExecutionUnitConnectionWidget widget, ObjectState previousState, ObjectState state);

}
