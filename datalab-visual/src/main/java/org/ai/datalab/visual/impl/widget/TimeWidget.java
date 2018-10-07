/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.visual.impl.widget;

import java.util.Date;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.Scene;

/**
 *
 * @author Mohan Purushothaman
 */
public class TimeWidget extends LabelWidget {

    private final long startTime;

    public TimeWidget(Scene scene) {
        super(scene);
        this.startTime = System.currentTimeMillis();
        setLabel("Started at "+ new Date(startTime));
    }
    
    public void complete(){
        setLabel("Completed in "+DurationFormatUtils.formatDurationWords(System.currentTimeMillis()-startTime, true, true));
    }
    
 

}
