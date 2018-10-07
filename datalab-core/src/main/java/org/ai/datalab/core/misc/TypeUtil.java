/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.core.misc;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import org.apache.commons.lang.time.DateUtils;

/**
 *
 * @author Mohan Purushothaman
 */
public class TypeUtil {

    public static Type detectType(Object obj) {
        if (obj == null) {
            return Type.String;
        }

        if (obj instanceof Number) {
            Number n = (Number) obj;

            if (obj.toString().contains(".")) {
                return Type.Double;
            }
            double d = n.doubleValue() - n.longValue();
            if (d == 0.0) {
                return Type.Long;
            }
            return Type.Double;

        }

        if (obj instanceof Date) {
            if (obj instanceof Timestamp) {
                return Type.Timestamp;
            }
            if (obj instanceof java.sql.Date) {
                return Type.Date;
            }
            return Type.Timestamp;
        }

        if (obj instanceof Boolean) {
            return Type.Boolean;
        }
        //if (obj instanceof String) {
        return Type.Object;
        //}
        //throw new UnsupportedOperationException("unsupported object");
    }

    private static final String[] autoDetectableDateFormats = new String[]{"yyyy-mm-dd"};
    private static final String[] autoDetectableTimeFormats = new String[]{"yyyy-mm-ddhh:mm:ssTZD"};

    public static Object parse(Object obj, Type type) throws RuntimeException {
        try {
            if (obj == null) {
                return null;
            }
            switch (type) {
                case Boolean: {
                    return (obj instanceof Boolean) ? (Boolean) obj : Boolean.parseBoolean(obj.toString());
                }
                case Date: { // improved to remove time values
                    return DateUtils.truncate((obj instanceof Date) ? (Date) obj : DateUtils.parseDate(obj.toString(), autoDetectableDateFormats), Calendar.DATE);
                }
                case Timestamp: { 
                    return (obj instanceof Date) ? (Date) obj : DateUtils.parseDate(obj.toString(), autoDetectableTimeFormats);
                }
                case Double: {
                    return (obj instanceof Number) ? ((Number) obj).doubleValue() : Double.parseDouble(obj.toString());
                }
                case Long: {
                    return (obj instanceof Number) ? ((Number) obj).longValue() : Long.parseLong(obj.toString());
                }
                case String: {
                    return obj.toString();
                }
                default: {
                    throw new Exception("can't covert as " + type);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
