/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.core.adx.misc;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ai.datalab.core.Data;
import org.ai.datalab.core.misc.SimpleData;

/**
 *
 * @author Mohan Purushothaman
 */
public class MappingHelper<ID> {

    private final List<SingleMapping<ID>> idList = new LinkedList<>();

    public final void addIdMap(ID adapterID, String fieldKey, ValueConverter converter, Object sampleValue) {
        addIdMap(new SingleMapping<>(adapterID, fieldKey, converter, sampleValue, this));
    }

    public final void addIdMap(SingleMapping<ID> idMapping) {
        assert idMapping.getParent() == this;
        idList.add(idMapping);
    }

    public final void delete(SingleMapping id) {
        idList.remove(id);
    }

    public void map(ValueMapper<ID> val, Data data) throws Exception {
        for (SingleMapping<ID> s : idList) {
            s.map(val, data);
        }
    }

    public List<SingleMapping<ID>> getIdList(Comparator<SingleMapping<ID>> comparator) {

        List<SingleMapping<ID>> list = new LinkedList<>(idList);
        if (comparator != null) {
            Collections.sort(list, comparator);
        }
        return list;
    }

    public Data getSampleData() {
        Data data = new SimpleData();

        for (SingleMapping<ID> singleMapping : idList) {
            try {
                data.setValue(singleMapping.getFieldKey(), ValueConverter.NO_CONVERTER, singleMapping.getSampleValue());
            } catch (Exception ex) {
                Logger.getLogger(MappingHelper.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return data;
    }

    

}
