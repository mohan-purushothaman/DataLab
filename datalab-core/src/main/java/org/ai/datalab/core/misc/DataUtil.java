/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.core.misc;

import org.ai.datalab.core.Data;
import org.ai.datalab.core.adx.misc.ValueConverter;
import org.apache.commons.lang.text.StrLookup;
import org.apache.commons.lang.text.StrSubstitutor;

/**
 *
 * @author Mohan Purushothaman
 */
public class DataUtil {

    public static String normalizeFieldKey(String suggestedDesc, Data availableData) {
        if (suggestedDesc.length() > 32) {
            suggestedDesc = suggestedDesc.substring(0, 32);
        }
        StringBuilder sb = new StringBuilder();
        for (char c : suggestedDesc.toCharArray()) {
            if (Character.isJavaIdentifierPart(c) || (Character.isJavaIdentifierStart(c) && sb.length() == 0)) {
                sb.append(Character.toUpperCase(c));
            }
        }
        if (sb.length() == 0) {
            sb.append("NONAME_");
        }

        if (sb.length() > 32) {
            sb.setLength(32);
        }

        String s = sb.toString();
        if (!availableData.contains(s)) {
            return s;
        }

        int i = 0;
        while (++i < 100) {
            String tempS = s + i;
            if (!availableData.contains(tempS)) {
                return tempS;
            }
        }
        return s + Math.round(Math.random());
    }

   

    public static String getVariableString(String varName) {
        return "${" + varName + "}";
    }
    
    public static String substituteData(String s, Data data) throws Exception{
        return substituteData(s, data,ValueConverter.SIMPLE_STRING_CONVERTER);
    }

    public static String substituteData(String s, Data data, ValueConverter<Object, String> converter) throws Exception{
        if (data == null) {
            return s;
        }
   
        StrSubstitutor sup = new StrSubstitutor(new StrLookup() {
            @Override
            public String lookup(String key) {
                try {
                    return converter.convert(data.getValue(key));
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
}
            }
        });

        return sup.replace(s);
    }
}
