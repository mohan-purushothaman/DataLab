/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.core.misc;

import java.util.Map;
import org.ai.datalab.core.Data;
import org.ai.datalab.core.adx.misc.ValueConverter;

/**
 *
 * @author Mohan Purushothaman
 */
public class FixedData extends SimpleData {

    @Override
    public Object deleteKey(String key) throws DataValueMissingException {
        throw new UnsupportedOperationException("changing not allowed");
    }

    @Override
    public void setValue(String key, ValueConverter converter, Object value) throws Exception {
        throw new UnsupportedOperationException("changing not allowed");
    }

    @Override
    public void setValue(String key, Object value) {
        throw new UnsupportedOperationException("changing not allowed");
    }

    public void setValueOverride(String key, Object value) {
        super.setValue(key, value);
    }

    @Override
    public FixedData cloneData() throws Exception {
        FixedData d = new FixedData();
        for (Map.Entry<String, Object> e : getEntrySet()) {
            d.setValueOverride(e.getKey(), e.getValue());
        }
        return d;
    }

    public static FixedData getFixedData(Data data) {

        if (data == null) {
            return null;
        }

        FixedData d = new FixedData();
        for (Map.Entry<String, Object> e : data.getEntrySet()) {
            d.setValueOverride(e.getKey(), e.getValue());
        }
        return d;
    }

}
