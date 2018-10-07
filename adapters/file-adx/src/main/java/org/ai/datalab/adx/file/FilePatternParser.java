/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.adx.file;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import org.apache.commons.lang.text.StrLookup;
import org.apache.commons.lang.text.StrSubstitutor;
import org.ai.datalab.core.Data;
import org.ai.datalab.core.adx.misc.MappingHelper;
import org.ai.datalab.core.misc.SimpleData;

/**
 *
 * @author Mohan Purushothaman
 */
public class FilePatternParser {

    private final String[] splitter;
    private final String[] var;
    private final String quote;

    public FilePatternParser(String format, String quoteString) {
        this.quote = quoteString;
        String temp = "/*@!~*/";
        while (format.contains(temp)) {
            temp = temp + "_";   //need to create concreate logic 
        }
        final String delim = temp;
        final List<String> vars = new LinkedList<>();
        StrSubstitutor ss = new StrSubstitutor(new StrLookup() {

            @Override
            public String lookup(String key) {
                vars.add(key);
                return delim;
            }
        });

        splitter = ss.replace(format).split(Pattern.quote(delim));
        var = vars.toArray(new String[vars.size()]);
    }

    public Data parse(String line, MappingHelper<String> mapping) throws Exception {

        Map<String, Object> map = new HashMap<>();
        if (splitter.length == 0) {
            map.put(var[0], replaceQuote(line));
        } else {
            int splitIndex = 0;
            String pre = splitter[splitIndex++];
            int startIndex = line.indexOf(pre, 0) + pre.length();

            for (String variable : var) {

                int endIndex;
                String post;
                if (splitIndex >= splitter.length) {
                    endIndex = line.length();
                    post = "";
                } else {
                    post = splitter[splitIndex];
                    endIndex = line.indexOf(post, startIndex);
                }
                if (endIndex == -1) {
                    throw new Exception(post + " not found");
                }
                String val = replaceQuote(line.substring(startIndex, endIndex));
                map.put(variable, val);

                startIndex = endIndex + post.length();
                splitIndex++;
            }
        }
        Data newData = new SimpleData();
        if (mapping == null) {
            for (Entry<String, Object> e : map.entrySet()) {
                newData.setValue(e.getKey(), null, e.getValue());
            }
        } else {

            mapping.map((String id) -> {
                return new Object[]{map.get(id)};
            }, newData);
        }

        return newData;
    }

    private String replaceQuote(String val) {
        if (quote == null || quote.isEmpty() || !val.startsWith(quote)) {
            return val;
        }

        val = val.substring(quote.length());
        if (val.endsWith(quote)) {
            val = val.substring(0, val.length() - quote.length());
        }
        return val;

    }

}
