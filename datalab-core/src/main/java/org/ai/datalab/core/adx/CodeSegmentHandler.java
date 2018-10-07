/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.core.adx;

/**
 *
 * @author Mohan Purushothaman
 */
public interface CodeSegmentHandler<V> {
    public V getCodeSegment(CodeSegment segment);
    public void setCodeSegment(CodeSegment segment,V content);
}
