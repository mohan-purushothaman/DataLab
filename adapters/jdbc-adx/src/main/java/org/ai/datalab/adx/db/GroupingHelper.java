/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.adx.db;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.ai.datalab.core.adx.misc.ValueMapper;

/**
 *
 * @author Mohan Purushothaman
 * @param <ID>
 */
public class GroupingHelper<ID> implements ValueMapper<ID>{

    private final Map<ID, List<Object>> values = new HashMap<>();

    public void addValue(ID id, Object value) {
        List<Object> list = values.get(id);
        if (list == null) {
            list = new LinkedList<>();
            values.put(id, list);
        }
        list.add(value);
    }
    
    @Override
    public Object[] getValue(ID id){
        List<Object> list = values.get(id);
        if(list==null){
            return new Object[0];
        }
        return list.toArray();
    }

}
