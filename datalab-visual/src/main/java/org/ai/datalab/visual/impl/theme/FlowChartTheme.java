/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.visual.impl.theme;

import org.ai.datalab.visual.DataLabTheme;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Stroke;
import org.netbeans.api.visual.anchor.AnchorShape;
import org.netbeans.api.visual.anchor.PointShape;
import org.netbeans.api.visual.border.Border;
import org.netbeans.api.visual.border.BorderFactory;
import org.netbeans.api.visual.model.ObjectState;
import org.netbeans.api.visual.widget.Widget;

import org.ai.datalab.core.JobState;
import org.ai.datalab.core.executor.ExecutorType;
import org.ai.datalab.visual.impl.widget.DataJobConnectionWidget;
import org.ai.datalab.visual.impl.widget.DataJobProgressWidget;
import org.ai.datalab.visual.impl.widget.ExecutionUnitConnectionWidget;
import org.ai.datalab.visual.impl.widget.ExecutionUnitWidget;

import org.ai.datalab.visual.impl.widget.misc.border.ParallelogramBorder;
import org.ai.datalab.visual.impl.widget.misc.border.RhombusBorder;

/**
 *
 * @author Mohan Purushothaman
 */
public class FlowChartTheme extends DataLabTheme {

    static final Color COLOR_NORMAL = new Color(0xBACDF0);
    public static final Color COLOR60_SELECT = new Color(0xFF8500);
    public static final Color COLOR60_HOVER = new Color(0x5B67B0);
    public static final Color COLOR60_HOVER_BACKGROUND = new Color(0xB0C3E1);

    static final Color COLOR1 = new Color(241, 249, 253);
    static final Color COLOR4 = new Color(200, 200, 246);

    private static final ParallelogramBorder NORMAL_READ_WRITE_BORDER = new ParallelogramBorder(10, 10, COLOR1, COLOR_NORMAL);
    private static final ParallelogramBorder HOVER_READ_WRITE_BORDER = new ParallelogramBorder(10, 10, COLOR4, COLOR60_HOVER);
    private static final ParallelogramBorder SELETED_READ_WRITE_BORDER = new ParallelogramBorder(10, 10, COLOR4, COLOR60_SELECT);

    private static final Border NORMAL_PROCESS_BORDER = BorderFactory.createRoundedBorder(20, 20, 10, 10, COLOR1, COLOR_NORMAL);
    private static final Border HOVER_PROCESS_BORDER = BorderFactory.createRoundedBorder(20, 20, 10, 10, COLOR4, COLOR60_HOVER);
    private static final Border SELETED_PROCESS_BORDER = BorderFactory.createRoundedBorder(20, 20, 10, 10, COLOR4, COLOR60_SELECT);

    private static final Border NORMAL_CONDITION_BORDER = new RhombusBorder(10, COLOR1, COLOR_NORMAL);
    private static final Border HOVER_CONDITION_BORDER = new RhombusBorder(10, COLOR4, COLOR60_HOVER);
    private static final Border SELECTED_CONDITION_BORDER = new RhombusBorder(10, COLOR4, COLOR60_SELECT);

//    @Override
//    public void installUI(AutomationWidget widget) {
//        widget.setBorder(getBorder(widget.getProvider().getProvidingType(), false, false));
//        widget.setOpaque(false);
//    }
//
//    @Override
//    public void updateUI(AutomationWidget widget, ObjectState previousState, ObjectState state) {
//        if (!previousState.isSelected() && state.isSelected()) {
//            widget.bringToFront();
//        } else if (!previousState.isHovered() && state.isHovered()) {
//            widget.bringToFront();
//        }
//        //widget.setOpaque(state.isSelected());
//        widget.setBorder(getBorder(widget.getProvider().getProvidingType(), (state.isFocused() || state.isHovered()), state.isSelected()));
//    }
    @Override
    public void installUI(DataJobConnectionWidget widget) {
        Color c = getFlowConditionColor(widget.getEdge().getFlowCondition());
        widget.setForeground(c);
        Widget label = widget.getLabelWidget();

        label.setForeground(c);
        label.setOpaque(true);
        widget.setSourceAnchorShape(AnchorShape.NONE);
        widget.setTargetAnchorShape(AnchorShape.TRIANGLE_FILLED);
        widget.setPaintControlPoints(true);
    }

    private static final Stroke normalConnectionStroke = new BasicStroke(2);
    private static final Stroke selectConnectionStroke = new BasicStroke(4);

    @Override
    public void updateUI(DataJobConnectionWidget widget, ObjectState previousState, ObjectState state) {
        if (state.isSelected()) {
            widget.setControlPointShape(PointShape.SQUARE_FILLED_SMALL);
            widget.setEndPointShape(PointShape.SQUARE_FILLED_SMALL);
            widget.setStroke(selectConnectionStroke);
        } else {
            widget.setControlPointShape(PointShape.NONE);
            widget.setEndPointShape(PointShape.NONE);
            widget.setStroke(normalConnectionStroke);
        }
    }

    private static Border getBorder(ExecutorType type, boolean isFocused, boolean isSelected) {
        switch (type) {
            case READER:
            case WRITER: {
                return isSelected ? SELETED_READ_WRITE_BORDER : isFocused ? HOVER_READ_WRITE_BORDER : NORMAL_READ_WRITE_BORDER;
            }
            case CONDITION: {
                return isSelected ? SELECTED_CONDITION_BORDER : isFocused ? HOVER_CONDITION_BORDER : NORMAL_CONDITION_BORDER;
            }
            case PROCESSOR: {
                return isSelected ? SELETED_PROCESS_BORDER : isFocused ? HOVER_PROCESS_BORDER : NORMAL_PROCESS_BORDER;
            }
            default:
                throw new UnsupportedOperationException("type not found");
        }

    }        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

    @Override
    public void installUI(DataJobProgressWidget widget) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //private static final Border DASH_BORDER_HOVER=BorderFactory.createDashedBorder(Color.BLACK, 1, 1);
    //private static final Border DASH_BORDER_SELECTED=BorderFactory.createDashedBorder(Color.BLACK, 2, 2);
    @Override
    public void updateUI(DataJobProgressWidget widget, ObjectState previousState, ObjectState state) {
        if (!previousState.isSelected() && state.isSelected()) {
            widget.bringToFront();
        } else if (!previousState.isHovered() && state.isHovered()) {
            widget.bringToFront();
        }
        //widget.setOpaque(state.isSelected());
        widget.setBorder(getBorder(widget.getProvider().getProvidingType(), (state.isFocused() || state.isHovered()), state.isSelected()));
    }

    private static final Color GREEN = new Color(79, 138, 16);
    private static final Color RED = new Color(216, 0, 12);

    public Color getColor(JobState state) {
        switch (state) {
            case PENDING:
            case INITIATING:
            case IN_PROGRESS:
            case SHUTDOWN_INITIATING:
                return Color.BLUE;

            case INITIATION_FAILED:
            case SHUTDOWN_FAILED:
                return RED;

            case COMPLETED:
                return GREEN;
            default:
                throw new AssertionError(state.name());
        }
    }

    @Override
    public void installUI(ExecutionUnitWidget widget) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateUI(ExecutionUnitWidget widget, ObjectState previousState, ObjectState state) {
        if (!previousState.isSelected() && state.isSelected()) {
            widget.bringToFront();
        } else if (!previousState.isHovered() && state.isHovered()) {
            widget.bringToFront();
        }
        //widget.setOpaque(state.isSelected());
        widget.setBorder(getBorder(widget.getProvider().getProvidingType(), (state.isFocused() || state.isHovered()), state.isSelected()));
    }

    @Override
    public void installUI(ExecutionUnitConnectionWidget widget) {
        Color c = getFlowConditionColor(widget.getEdge().getFlowCondition());
        widget.setForeground(c);
//        Widget label = widget.getLabelWidget();
//
//        label.setForeground(c);
//        label.setOpaque(true);
        widget.setSourceAnchorShape(AnchorShape.NONE);
        widget.setTargetAnchorShape(AnchorShape.TRIANGLE_FILLED);
        widget.setPaintControlPoints(true);
    }

    @Override
    public void updateUI(ExecutionUnitConnectionWidget widget, ObjectState previousState, ObjectState state) {
        if (state.isSelected()) {
            widget.setControlPointShape(PointShape.SQUARE_FILLED_SMALL);
            widget.setEndPointShape(PointShape.SQUARE_FILLED_SMALL);
            widget.setStroke(selectConnectionStroke);
        } else {
            widget.setControlPointShape(PointShape.NONE);
            widget.setEndPointShape(PointShape.NONE);
            widget.setStroke(normalConnectionStroke);
        }
    }

}
