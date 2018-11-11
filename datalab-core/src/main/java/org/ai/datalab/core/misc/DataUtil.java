/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.core.misc;

import java.util.Random;
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
        boolean isFirst = true;
        for (char c : suggestedDesc.toCharArray()) {
            if (isFirst) {
                if (Character.isJavaIdentifierStart(c)) {
                    sb.append(Character.toUpperCase(c));
                }
            } else {
                if (Character.isJavaIdentifierPart(c)) {
                    sb.append(Character.toUpperCase(c));
                }
            }
            isFirst = false;
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
        return s + new Random().nextLong();
    }

    public static void validateVariableName(String variableName) throws RuntimeException {

        if (variableName.isEmpty()) {
            throw new RuntimeException("empty variable name is not allowed");
        }

        if (variableName.length() > 64) {
            throw new RuntimeException(variableName + " is more than allowed length of 64");
        }
        boolean isFirst = true;
        for (char c : variableName.toCharArray()) {
            if (isFirst) {
                if (!Character.isJavaIdentifierStart(c)) {
                    throw new RuntimeException(variableName + " is not a valid Java Identifier");
                }
            } else {
                if (!Character.isJavaIdentifierPart(c)) {
                    throw new RuntimeException(variableName + " is not a valid Java Identifier");
                }
            }
            isFirst = false;
        }
    }

    public static String getVariableString(String varName) {
        return "${" + varName + "}";
    }

    public static String substituteData(String s, Data data) throws Exception {
        return substituteData(s, data, ValueConverter.SIMPLE_STRING_CONVERTER);
    }

    public static String substituteData(String s, Data data, ValueConverter<Object, String> converter) throws Exception {
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
