/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.core.adx.misc;

import org.ai.datalab.core.Data;
import org.ai.datalab.core.misc.DataUtil;

/**
 *
 * @author Mohan Purushothaman
 * @param <ID> result to return
 */
public class SingleMapping<ID> {

    private final ID adapterID;
    private String fieldKey;
    private ValueConverter converter;
    private Object sampleValue;

    private final MappingHelper parent;

    public SingleMapping(ID adapterID, String fieldKey, ValueConverter converter, Object sampleValue, MappingHelper parent) throws RuntimeException {
        this.adapterID = adapterID;
        this.fieldKey = fieldKey;
        DataUtil.validateVariableName(fieldKey);
        this.converter = converter == null ? ValueConverter.NO_CONVERTER : converter;
        this.sampleValue = sampleValue;
        this.parent = parent;
    }

    public Object getSampleValue() {
        return sampleValue;
    }

    public void setSampleValue(Object sampleValue) {
        this.sampleValue = sampleValue;
    }

    public ID getAdapterID() {
        return adapterID;
    }

    public String getFieldKey() {
        return fieldKey;
    }

    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }

    public ValueConverter getConverter() {
        return converter;
    }

    public void setConverter(ValueConverter converter) {
        this.converter = converter;
    }

    public MappingHelper getParent() {
        return parent;
    }

    public void map(ValueMapper<ID> val, Data data) throws Exception {

        if (val == ValueConverter.VALUE_DELETER) {
            data.deleteKey(fieldKey);
        } else {
            data.setValue(fieldKey, converter, val.getValue(adapterID));
        }
    }

   }
