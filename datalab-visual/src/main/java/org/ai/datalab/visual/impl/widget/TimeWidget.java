/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.visual.impl.widget;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicReference;
import javax.swing.JProgressBar;
import org.netbeans.api.visual.widget.ComponentWidget;
import org.netbeans.api.visual.widget.Scene;
import org.openide.util.Exceptions;

/**
 *
 * @author Mohan Purushothaman
 */
public class TimeWidget extends ComponentWidget {

    static {
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, ClassLoader.getSystemResourceAsStream("Digital-7.ttf"));
            font = font.deriveFont(Font.BOLD, 28);

            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final AtomicReference<Instant> startTime = new AtomicReference<>();

    private final AtomicReference<Instant> endTime = new AtomicReference<>();

    private final JProgressBar bar;

    public TimeWidget(Scene scene, JProgressBar bar) {
        super(scene, bar);
        this.bar = bar;
        startTime.set(Instant.now());
        //bar.setPreferredSize(new Dimension(200, 35));
        bar.setFont(new Font("", Font.BOLD, 22));
        bar.setBackground(Color.WHITE);
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
        String s = formatToTwoDigits(d.toHours()) + ":" + formatToTwoDigits(d.toMinutes() % 60) + ":" + formatToTwoDigits(d.getSeconds() % 60) + "." + formatToThreeDigits(d.toMillis() % 1000);
        bar.setString(s);
    }

    public void complete() {
        endTime.set(Instant.now());
        bar.setIndeterminate(false);
        bar.setMaximum(1);
        bar.setValue(1);
    }

    public String formatToTwoDigits(long l) {
        return (l < 10 ? "0" : "") + l;
    }

    public String formatToThreeDigits(long l) {
        return (l < 100 ? "0" : "") + formatToTwoDigits(l);
    }

}
