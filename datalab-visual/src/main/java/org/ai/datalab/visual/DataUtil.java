/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.visual;

import java.lang.reflect.InvocationTargetException;
import org.openide.nodes.PropertySupport;
import org.openide.nodes.Sheet;
import org.ai.datalab.core.Data;

/**
 *
 * @author Mohan Purushothaman
 */
public class DataUtil {
    public static String normalizeFieldKey(String suggestedDesc, Data availableData) {
        if (suggestedDesc.length() > 32) {
            suggestedDesc=suggestedDesc.substring(0, 32);
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
    
    
    public static Sheet.Set getDataSheet(final Data data, String displayName, String tabName) {
        if (data != null) {
            Sheet.Set set = Sheet.createPropertiesSet();

            for (final String d : data.getKeyNames()) {
                set.put(new PropertySupport.ReadOnly<String>(d, String.class, d, d) {

                    @Override
                    public String getValue() throws IllegalAccessException, InvocationTargetException {
                        return String.valueOf(data.getValue(d));
                    }
                });
            }
            set.setName(displayName);
            set.setDisplayName(displayName);
            set.setValue("tabName", tabName);
            return set;
        }
        return null;
    }
    
    
    public static String getVariableString(String varName){
        return "${"+varName+"}";
    }
}
