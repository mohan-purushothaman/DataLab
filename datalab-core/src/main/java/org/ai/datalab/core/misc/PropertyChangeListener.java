/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.core.misc;

/**
 *
 * @author Mohan Purushothaman
 */
public interface PropertyChangeListener<V> {
    public void valueUpdated(V newValue);
}
