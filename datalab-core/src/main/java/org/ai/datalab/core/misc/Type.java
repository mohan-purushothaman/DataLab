/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.core.misc;

import java.util.Date;
import java.sql.Timestamp;
import org.ai.datalab.core.adx.misc.ValueConverter;

/**
 *
 * @author Mohan Purushothaman
 */
public enum Type {
//TODO need to improve to have list of acceptable types

    Boolean(Boolean.class),
    Long(Long.class),
    Double(Double.class),
    String(String.class),
    Date(Date.class),
    Timestamp(Timestamp.class),
    Object(Object.class);

    private final Class supportedClass;

    private Type(Class supportedClass) {
        this.supportedClass = supportedClass;
    }

    public Class getClazz() {
        return supportedClass;
    }

    public ValueConverter getConverter() {
        switch (this) {
            case Boolean:
                return ValueConverter.BOOLEAN_CONVERTER;
            case Date:
                return ValueConverter.DATE_CONVERTER;
            case Double:
                return ValueConverter.DOUBLE_CONVERTER;
            case Long:
                return ValueConverter.LONG_CONVERTER;
            case String:
                return ValueConverter.SIMPLE_STRING_CONVERTER;
            case Timestamp:
                return ValueConverter.TIMESTAMP_CONVERTER;
            default:
                return ValueConverter.NO_CONVERTER;
        }
    }

    public Object getSampleValue() {
        switch (this) {
            case Boolean:
                return true;
            case Date:
                return new Date();
            case Double:
                return 1.01;
            case Long:
                return 9876543210L;
            case String:
                return "sample String";
            case Timestamp:
                return new Timestamp(System.currentTimeMillis());
            default:
                return new Object();
        }
    }

}
