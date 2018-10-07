/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.core.adx.impl;

import java.util.HashMap;
import java.util.Map;
import org.ai.datalab.core.adx.CodeSegment;
import org.ai.datalab.core.adx.CodeSegmentHandler;

/**
 *
 * @author Mohan Purushothaman
 */
public abstract class AbstractCodeSegmentHandler<V> implements CodeSegmentHandler<V>{

    private final Map<CodeSegment,V> segments=new HashMap<>();
    
    @Override
    public V getCodeSegment(CodeSegment segment) {
        return segments.get(segment);
        
    }

    @Override
    public void setCodeSegment(CodeSegment segment, V content) {
        if(isEnabled(segment)){
            segments.put(segment, content);
        }else{
            throw new UnsupportedOperationException(segment+" is not enabled");
        }
    }
    
    public boolean isEnabled(CodeSegment segment){
        return true;
    }
}