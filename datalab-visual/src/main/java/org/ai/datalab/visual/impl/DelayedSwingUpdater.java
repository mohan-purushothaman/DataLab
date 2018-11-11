/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.visual.impl;

import java.awt.EventQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import org.ai.datalab.visual.impl.widget.misc.SwingUpdater;

/**
 *
 * @author Mohan Purushothaman
 */
public abstract class DelayedSwingUpdater extends SwingUpdater<Void> {

    private final AtomicBoolean isInterrupted = new AtomicBoolean(false);

    public void interrupt() {
        isInterrupted.set(true);
    }

    @Override
    protected final void process(AtomicReference<Void> latestValue) {
        processUpdates();
        if (!isInterrupted.get()) {
            EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    publish(null);
                }
            });
        }

    }

    public abstract void processUpdates();

}
