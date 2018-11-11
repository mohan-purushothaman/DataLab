/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.visual.impl.widget.misc;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import javax.swing.SwingUtilities;

/**
 *
 * @author Mohan Purushothaman
 * @param <V> value type to receive
 */
public abstract class SwingUpdater<V> implements Runnable {

    private final AtomicReference<V> ref = new AtomicReference<>();

    private final AtomicBoolean scheduleExecution = new AtomicBoolean(true);

   

    public final void publish(V value) {
        ref.set(value);
        if (scheduleExecution.get()) {
            scheduleExecution.set(false);
            SwingUtilities.invokeLater(this);
        }
    }

    @Override
    public final void run() {
        try {
            process(ref);
        } finally {
            scheduleExecution.set(true);
        }
    }

    /**
     * Receives data chunks from the {@code publish} method asynchronously on
     * the
     * <i>Event Dispatch Thread</i>.
     *
     * <p>
     * Please refer to the {@link #publish} method for more details.
     *
     * @param latestValue latestValue to process in eventQueue thread
     *
     * @see #publish
     *
     */
    protected abstract void process(AtomicReference<V> latestValue);

}
