/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.visual.impl;

import org.ai.datalab.core.adx.misc.MappingHelper;
import org.ai.datalab.core.adx.misc.SingleMapping;
import org.ai.datalab.core.adx.misc.ValueConverter;
import org.ai.datalab.core.adx.misc.ValueGroupingStrategy;

/**
 *
 * @author Mohan Purushothaman
 * @param <ID> mapping result type
 */
public class DescriptiveSingleMapping<ID> extends SingleMapping<ID> {

    private final boolean isChangable;
    
    public DescriptiveSingleMapping(boolean isChangable, ID adapterID, String fieldKey, ValueConverter converter, ValueGroupingStrategy groupingStrategy, Object sampleValue, MappingHelper parent) {
        super(adapterID, fieldKey, converter, groupingStrategy, sampleValue, parent);
        this.isChangable=isChangable;
    }

    public boolean isChangable() {
        return isChangable;
    }

  
    
}
