/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.visual.impl.widget;

import org.apache.commons.lang.StringUtils;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.Scene;

/**
 *
 * @author Mohan Purushothaman
 */
public class AbbreviatedLabelWidget extends LabelWidget {

    public static final int MAX_SIZE = 32;

    public AbbreviatedLabelWidget(Scene scene, String label) {
        super(scene, label);
    }

    @Override
    public final void setLabel(String label) {
        super.setLabel(StringUtils.abbreviate(label, MAX_SIZE));
    }
     

}
