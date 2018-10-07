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

public class ExecutionUnitWidget extends Widget {

    private final DescriptiveExecutionUnit unit;

    private final DataLabTheme theme;

    private final LabelWidget labelWidget;

    public ExecutionUnitWidget(DescriptiveExecutionUnit unit, Scene scene, DataLabTheme theme) {
        super(scene);
        this.unit = unit;
        this.theme = theme;
        labelWidget = new AbbreviatedLabelWidget(scene, unit.getDescription());
        addChild(labelWidget);
        theme.installUI(this);
        setState(ObjectState.createNormal());
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
