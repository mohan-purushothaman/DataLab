/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.visual.impl.widget;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.AbstractList;
import java.util.List;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import org.netbeans.api.visual.border.Border;
import org.netbeans.api.visual.border.BorderFactory;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.model.ObjectState;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import org.ai.datalab.core.ExecutorProvider;
import org.ai.datalab.core.JobState;
import org.ai.datalab.core.builder.ExecutionUnit;
import org.ai.datalab.visual.DataLabTheme;
import org.ai.datalab.visual.impl.DataLabProgressHandler;
import org.ai.datalab.visual.impl.widget.misc.DataDisplayer;
import org.ai.datalab.visual.impl.widget.misc.DataDisplayerCreater;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.PopupMenuProvider;

public class DataJobProgressWidget extends Widget {

    public final static Border successBorder = BorderFactory.createLineBorder(3, 3, 3, 3, DataLabTheme.green);
    public final static Border failureBorder = BorderFactory.createLineBorder(3, 3, 3, 3, DataLabTheme.red);

    private final ExecutionUnit unit;
    private final LabelWidget labelWidget;
    private final LabelWidget successLabel;
    private final LabelWidget failureLabel;
    private final LabelWidget avgLabel;
    private final LabelWidget statusWidget;

    private final DataLabTheme theme;

    private DataLabProgressHandler progressHandler;

    public DataJobProgressWidget(ExecutionUnit unit, final Scene scene, DataLabTheme theme) {
        super(scene);
        this.theme = theme;
        this.unit = unit;

        labelWidget = new AbbreviatedLabelWidget(scene, unit.getDescription());
        successLabel = new LabelWidget(scene, "0");
        successLabel.setToolTipText("Completed Count");
        successLabel.setBorder(successBorder);
        successLabel.setOpaque(true);

        avgLabel = new LabelWidget(scene, "0");
        avgLabel.setToolTipText("Average time taken");
        avgLabel.setBorder(successBorder);
        avgLabel.setOpaque(true);

        failureLabel = new LabelWidget(scene, "0");
        failureLabel.setToolTipText("Failure Count");
        failureLabel.setBorder(failureBorder);
        failureLabel.setOpaque(true);

        statusWidget = new LabelWidget(scene, "Yet to Start");
        statusWidget.setToolTipText("status");

        addChild(labelWidget);
        addChild(successLabel);
        addChild(avgLabel);
        addChild(failureLabel);
        failureLabel.getActions().addAction(ActionFactory.createPopupMenuAction(new PopupMenuProvider() {

            @Override
            public JPopupMenu getPopupMenu(Widget widget, Point localLocation) {
                JPopupMenu popup = new JPopupMenu();
                popup.add(new WidgetMenuItem(scene, "details", new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {

                        DataDisplayer dataDisplayer = DataDisplayerCreater.getDataDisplayer("Error Details : " + unit.getDescription());
                        dataDisplayer.addData(progressHandler.getErrorData());
                    }
                }));
                return popup;
            }
        }));
        addChild(statusWidget);
        theme.installUI(this);
        setLayout(LayoutFactory.createHorizontalFlowLayout(LayoutFactory.SerialAlignment.CENTER, 8));
    }

    public LabelWidget getSuccessLabel() {
        return successLabel;
    }

    public LabelWidget getFailureLabel() {
        return failureLabel;
    }

    public void setSuccessProgress(String progress) {
        successLabel.setLabel(progress);
    }

    public void setAvg(String avg) {
        avgLabel.setLabel(avg);
    }

    public void setErrorProgress(String progress) {
        failureLabel.setLabel(progress);
    }

    public ExecutorProvider getProvider() {
        return unit.getExecutorProvider();
    }

    @Override
    protected void notifyStateChanged(ObjectState previousState, ObjectState state) {
        theme.updateUI(this, previousState, state);
    }

    public void setProgressHandler(DataLabProgressHandler progressHandler) {
        this.progressHandler = progressHandler;
    }

    public DataLabProgressHandler getProgressHandler() {
        return progressHandler;
    }

    public void updateState(JobState state) {
        statusWidget.setLabel(state.toString());
        statusWidget.setForeground(theme.getColor(state));

    }

    public void init() {
        //added to avoid size issues on other widgets 
        for (Widget w : getChildren()) {
            w.revalidate();
        }
    }

}

class WidgetMenuItem extends JMenuItem {

    public WidgetMenuItem(final Scene scene, final String name, ActionListener listener) {
        super(name);
        if (listener != null) {
            addActionListener(listener);
        }
    }

}
