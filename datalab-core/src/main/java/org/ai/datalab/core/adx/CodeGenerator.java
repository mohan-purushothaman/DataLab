/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.core.adx;

/**
 *
 * @author Mohan Purushothaman
 * @param <R> type to generate
 */
public interface CodeGenerator<R,V> {
    public R generate();
    public CodeSegmentHandler<V> getCodeSegmentHandler();
}
