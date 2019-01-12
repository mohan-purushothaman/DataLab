/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.visual.impl.widget;

import org.netbeans.api.visual.model.ObjectState;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import org.ai.datalab.core.builder.ExecutionUnit;
import org.ai.datalab.visual.DataLabTheme;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.TextFieldInplaceEditor;
import org.netbeans.api.visual.graph.GraphScene;

public class ExecutionUnitWidget extends Widget {

    private final DescriptiveExecutionUnit unit;

    private final DataLabTheme theme;

    private final LabelWidget labelWidget;

    public ExecutionUnitWidget(DescriptiveExecutionUnit unit, GraphScene scene, DataLabTheme theme) {
        super(scene);
        this.unit = unit;
        this.theme = theme;
        labelWidget = new AbbreviatedLabelWidget(scene, unit.getDescription());
        addChild(labelWidget);
        theme.installUI(this);
        setState(ObjectState.createNormal());
        labelWidget.getActions().addAction(ActionFactory.createInplaceEditorAction(new TextFieldInplaceEditor() {
            @Override
            public boolean isEnabled(Widget widget) {
                Object obj = scene.findObject(widget.getParentWidget());

                return obj instanceof DescriptiveExecutionUnit;
            }

            @Override
            public String getText(Widget widget) {
                Object obj = scene.findObject(widget.getParentWidget());
                if (obj instanceof DescriptiveExecutionUnit) {
                    return ((DescriptiveExecutionUnit) obj).getDescription();
                }
                return "failed to get name";
            }

            @Override
            public void setText(Widget widget, String text) {
                Object obj = scene.findObject(widget.getParentWidget());
                if (obj instanceof DescriptiveExecutionUnit) {
                    ((DescriptiveExecutionUnit) obj).setSuggestedDescription(text);
                } else {
                    throw new RuntimeException("not able to set name");
                }
            }
        }));

    }

    public ExecutionUnit getProvider() {
        return unit;
    }

    @Override
    protected void notifyStateChanged(ObjectState previousState, ObjectState state) {
        theme.updateUI(this, previousState, state);
    }

    public LabelWidget getLabelWidget() {
        return labelWidget;
    }

}
