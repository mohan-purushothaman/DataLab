/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.core.adx.misc;

import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.time.DateUtils;
import org.ai.datalab.core.misc.Type;

/**
 *
 * @author Mohan Purushothaman
 * @param <F> From object
 * @param <T> To Object
 */
public abstract class ValueConverter<F, T> {

    private final Type resultType;

    public ValueConverter() {
        this(Type.Object);
    }

    public ValueConverter(Type resultType) {
        this.resultType = resultType;
    }

    public abstract T convert(F obj) throws Exception;

    public Type getResultType() {
        return resultType;
    }

    public static final String[] AUTO_DETECT_DATE_FORMATS = new String[]{"yyyy-mm-dd"};
    public static final String[] AUTO_DETECT_TIMESTAMP_FORMATS = new String[]{"yyyy-mm-ddhh:mm:ssTZD"};

    public static final ValueConverter<Object, String> SIMPLE_STRING_CONVERTER = new ValueConverter<Object, String>(Type.String) {
        @Override
        public String convert(Object obj) {
            return ObjectUtils.toString(obj);
        }

    };
    public static final ValueConverter<Object, Boolean> BOOLEAN_CONVERTER = new ValueConverter<Object, Boolean>(Type.Boolean) {
        @Override
        public Boolean convert(Object obj) {
            return (obj == null || obj instanceof Boolean) ? (Boolean) obj : Boolean.parseBoolean(obj.toString());
        }
    };
    public static final ValueConverter<Object, Date> DATE_CONVERTER = new ValueConverter<Object, Date>(Type.Date) {
        @Override
        public Date convert(Object obj) {
            try {
                if (obj == null) {
                    return null;
                }
                return DateUtils.truncate((obj instanceof Date) ? (Date) obj : DateUtils.parseDate(obj.toString(), AUTO_DETECT_DATE_FORMATS), Calendar.DATE);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
    };

    public static final ValueConverter<Object, Date> TIMESTAMP_CONVERTER = new ValueConverter<Object, Date>(Type.Timestamp) {
        @Override
        public Date convert(Object obj) {
            try {
                if (obj == null) {
                    return null;
                }
                return (obj instanceof Date) ? (Date) obj : DateUtils.parseDate(obj.toString(), AUTO_DETECT_TIMESTAMP_FORMATS);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
    };

    public static final ValueConverter<Object, Double> DOUBLE_CONVERTER = new NumberConverter<Double>(Type.Double) {
        @Override
        public Double convert(Number num) {
            return num.doubleValue();
        }

    };

    public static final ValueConverter<Object, Long> LONG_CONVERTER = new NumberConverter<Long>(Type.Long) {

        @Override
        public Long convert(Number num) {
            return num.longValue();
        }

    };

    public static final ValueConverter<Object, Object> NO_CONVERTER = new ValueConverter<Object, Object>() {
        @Override
        public Object convert(Object obj) {
            return obj;
        }
    };

    public static final ValueConverter VALUE_DELETER = new ValueConverter() {
        @Override
        public Object convert(Object obj) {
            throw new UnsupportedOperationException("Not supported ");
        }

        @Override
        public Type getResultType() {
            throw new UnsupportedOperationException("Not supported ");
        }

    };
}

abstract class NumberConverter<N> extends ValueConverter<Object, N> {

    public NumberConverter(Type resultType) {
        super(resultType);
    }

    @Override
    public final N convert(Object obj) throws Exception {
        if (obj == null) {
            return null;
        }
        Number num;
        if (!(obj instanceof Number)) {
            if (obj instanceof String && ((String) obj).length() == 0) {
                return null;
            }
            num = NumberFormat.getInstance().parse(obj.toString());
        } else {
            num = (Number) obj;
        }
        return convert(num);
    }

    public abstract N convert(Number num);

}
