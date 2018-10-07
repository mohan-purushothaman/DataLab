/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.adx.db;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.text.StrLookup;
import org.apache.commons.lang.text.StrMatcher;
import org.apache.commons.lang.text.StrSubstitutor;
import org.ai.datalab.core.Data;

/**
 *
 * @author Mohan Purushothaman
 */
public class StringSubstituteQuery {

    public static final char DEFAULT_ESCAPE = '$';

    public static final StrMatcher DEFAULT_PREFIX = StrMatcher.stringMatcher("$(");

    public static final StrMatcher DEFAULT_SUFFIX = StrMatcher.stringMatcher(")");

    private final StrSubstitutor substitutor;

    private final String query;

    public StringSubstituteQuery(String query) {
        this.query = query;
        substitutor = new StrSubstitutor((StrLookup) null, DEFAULT_PREFIX, DEFAULT_SUFFIX, DEFAULT_ESCAPE);
    }

    public String getQuery(Data data) {
        try {
            substitutor.setVariableResolver(new StrLookup() {
                @Override
                public String lookup(String key) {
                    return ObjectUtils.toString(data.getValue(key));
                }
            });
            return substitutor.replace(query);
        } finally {
            substitutor.setVariableResolver(null);
        }
    }

}
