/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.core.misc;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.ai.datalab.core.Data;
import org.ai.datalab.core.adx.misc.ValueConverter;

/**
 *
 * @author Mohan Purushothaman
 */
public class SimpleData implements Data {

    private final Map<String, Object> map = new LinkedHashMap<String, Object>();

    @Override
    public Data cloneData() throws Exception {
        Data d = new SimpleData();
        for (Map.Entry<String, Object> e : map.entrySet()) {
            d.setValue(e.getKey(), null, e.getValue());
        }
        return d;
    }

    @Override
    public Object getValue(String key) throws DataValueMissingException {
        Object value = map.get(key);

        if (value == null && !map.containsKey(key)) {
            throw new DataValueMissingException(key + " not present in data");
        }
        return value;
    }

    private void setValue(String key, Object value) {
        map.put(key, value);
    }

    @Override
    public void setValue(String key, ValueConverter converter, Object value) throws Exception{
        setValue(key, (value==null||converter == null) ? value : converter.convert(value));
    }

    @Override
    public Set<String> getKeyNames() {
        return map.keySet();
    }

    @Override
    public boolean contains(String key) {
        return map.containsKey(key);
    }

    @Override
    public String toString() {
        return map.toString();
    }

    @Override
    public Object deleteKey(String key) throws DataValueMissingException {
        if (map.containsKey(key)) {
            return map.remove(key);
        }
        throw new DataValueMissingException(key + " not present in data");
    }

    public Set<Entry<String, Object>> getEntrySet() {
        return new LinkedHashSet<>(map.entrySet());
    }

}
