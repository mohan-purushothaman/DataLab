/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.visual.impl.widget;

import java.awt.Font;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicReference;
import javax.swing.JProgressBar;
import org.netbeans.api.visual.widget.ComponentWidget;
import org.netbeans.api.visual.widget.Scene;

/**
 *
 * @author Mohan Purushothaman
 */
public class TimeWidget extends ComponentWidget {

    private final AtomicReference<Instant> startTime = new AtomicReference<>();

    private final AtomicReference<Instant> endTime = new AtomicReference<>();

    private final JProgressBar bar;

    public TimeWidget(Scene scene, JProgressBar bar) {
        super(scene, bar);
        this.bar = bar;
        startTime.set(Instant.now());
        setFont(new Font("", Font.BOLD, 28));
        bar.setStringPainted(true);
        bar.setIndeterminate(true);
        updateTime();
    }

    public final void updateTime() {
        Instant end = endTime.get();
        if (end == null) {
            end = Instant.now();
        }
        Instant start = startTime.get();
        Duration d = Duration.between(start, end);
        String s = d.toHours() + ":" + (d.toMinutes() % 60) + ":" + (d.getSeconds() % 60) + "." + (d.toMillis() % 1000);
        bar.setString(s);
    }

    public void complete() {
        endTime.set(Instant.now());
        bar.setIndeterminate(false);
        bar.setMaximum(1);
        bar.setValue(1);
    }

}
