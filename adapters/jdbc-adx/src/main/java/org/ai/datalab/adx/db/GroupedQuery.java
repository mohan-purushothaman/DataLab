/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.adx.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import org.apache.commons.lang.text.StrLookup;
import org.apache.commons.lang.text.StrMatcher;
import org.apache.commons.lang.text.StrSubstitutor;
import org.ai.datalab.core.Data;
import org.ai.datalab.core.adx.misc.MappingHelper;
import org.ai.datalab.core.adx.misc.SingleMapping;
import org.ai.datalab.core.adx.misc.ValueMapper;

/**
 *
 * @author Mohan Purushothaman
 */
public class GroupedQuery {

    public static final char DEFAULT_ESCAPE = '$';

    public static final StrMatcher DEFAULT_PREFIX = StrMatcher.stringMatcher("$[");

    public static final StrMatcher DEFAULT_SUFFIX = StrMatcher.stringMatcher("]");

    private final StrSubstitutor substitutor;

    private final String query;

    private final List<GroupVariable> varNames = new LinkedList<>();

    private final String groupDataKey;
    private final String groupAdapterKey;

    public GroupedQuery(String query, String groupDataKey, String groupAdapterKey) {
        this.query = query;
        substitutor = new StrSubstitutor(new StrLookup() {
            @Override
            public String lookup(String key) {
                int index = key.indexOf('(');
                String keyValue = key.substring(0, index);
                String prefix = Character.toString(key.charAt(++index));
                String suffix = Character.toString(key.charAt(++index));
                String separator = Character.toString(key.charAt(++index));
                varNames.add(new GroupVariable(keyValue, prefix, suffix, separator));
                return "";
            }
        }, DEFAULT_PREFIX, DEFAULT_SUFFIX, DEFAULT_ESCAPE);
        substitutor.replace(query);
        this.groupDataKey = groupDataKey;
        this.groupAdapterKey = groupAdapterKey;
    }

    public String getQuery(Data[] data) {

        ListIterator<GroupVariable> itr = varNames.listIterator();

        try {
            substitutor.setVariableResolver(new StrLookup() {
                private final StringBuilder tempStringBuilder = new StringBuilder();

                @Override
                public String lookup(String key) {
                    try {
                        GroupVariable g = itr.next();
                        boolean addSeparator = false;
                        for (Data d : data) {

                            if (addSeparator) {
                                tempStringBuilder.append(g.separator);
                            } else {
                                addSeparator = true;
                            }
                            tempStringBuilder.append(g.prefix).append(d.getValue(g.keyName)).append(g.suffix);
                        }

                        return tempStringBuilder.toString();
                    } finally {
                        tempStringBuilder.setLength(0);
                    }
                }
            });
            return substitutor.replace(query);
        } finally {
            substitutor.setVariableResolver(null);
        }
    }

    Map<String, GroupingHelper<String>> dbData = new HashMap<>();

    public void addRow(ResultSet set, MappingHelper<String> mapping) throws SQLException {
        String key = set.getString(groupAdapterKey);
        GroupingHelper<String> helper = dbData.get(key);
        for (SingleMapping<String> singleMapping : mapping.getIdList(null)) {

            if (helper == null) {
                helper = new GroupingHelper<>();
                dbData.put(key, helper);
            }
            helper.addValue(singleMapping.getAdapterID(), set.getObject(singleMapping.getAdapterID()));
        }
    }

    public ValueMapper<String> getValueMapper(Data data ) throws Exception {
        return dbData.get(String.valueOf(data.getValue(groupDataKey)));
    }

    public void reset() {
        dbData.clear();
    }

    private static class GroupVariable {

        private final String keyName;
        private final String prefix;
        private final String suffix;
        private final String separator;

        public GroupVariable(String keyName, String prefix, String suffix, String separator) {
            this.keyName = keyName;
            this.prefix = prefix;
            this.suffix = suffix;
            this.separator = separator;
        }

    }

}
