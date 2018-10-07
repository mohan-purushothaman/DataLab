/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.core;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import org.ai.datalab.core.misc.DataValueMissingException;
import org.ai.datalab.core.adx.misc.ValueConverter;
import org.ai.datalab.core.misc.SimpleData;

/**
 * Base interface to handle Data
 *
 * Data represents storing / retrival unit of DataLab
 *
 * @author Mohan Purushothaman
 */
public interface Data extends Serializable {

    /**
     * get value as Object for a given key in data
     *
     * @param key key to find value (case sensitive)
     * @return
     */
    public Object getValue(String key) throws DataValueMissingException;

    /**
     * Set value for a given key in data
     *
     * @param key
     * @param converter
     * @param value
     */
    public void setValue(String key, ValueConverter converter, Object value) throws Exception;

    /**
     * delete value of a given key in data
     *
     * @param key key to find value (case sensitive)
     * @return deleted object
     */
    public Object deleteKey(String key) throws DataValueMissingException;

    /**
     * clones current Data and create new data implementation can do cloning in
     * optimized way
     *
     * @return new data object which has same values like this
     */
    public Data cloneData() throws Exception;

    /**
     * get all keys present in this data
     *
     * @return set of key string
     */
    public Set<String> getKeyNames();

    /**
     * check whether provided key present in this data
     *
     * @param key key string to check
     * @return true if key present in this data, false if not
     */
    public boolean contains(String key);

    public Set<Map.Entry<String, Object>> getEntrySet();

    class DummyData implements Data {

        @Override
        public Object getValue(String key) throws DataValueMissingException {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void setValue(String key, ValueConverter converter, Object value) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Data cloneData() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Set<String> getKeyNames() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public boolean contains(String key) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Object deleteKey(String key) throws DataValueMissingException {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Set<Map.Entry<String, Object>> getEntrySet() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

    }

    public static final Data POISON_DATA = new DummyData();

    public static boolean isPoisonData(Data data) {
        return data instanceof DummyData;
    }

    public static Data newInstance() {
        return new SimpleData();
    }
}
