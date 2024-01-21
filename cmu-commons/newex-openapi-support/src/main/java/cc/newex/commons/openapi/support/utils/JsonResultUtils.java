package cc.newex.commons.openapi.support.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.NumberFormat;
import java.util.*;

/**
 * Json Result Utils  <br/>
 * Created by newex-team on 2018/2/9 18:54. <br/>
 * The return value is uniformly formatted. <br/>
 * 1.Return value order. <br/>
 * 2.Decimal numbers are returned as strings to preserve full precision across platforms. <br/>
 * 3.Use {@link JsonResultUtils#result(Object, Class)} ，Ensure the order of attributes in the class，consistent. <br/>
 */
@Slf4j
public class JsonResultUtils {

    public static final String DEFAULT_DOUBLE = "0.00";
    public static final String DEFAULT_LONG = "0";
    public static final String DEFAULT_INT = "0";
    public static final String DOT = ".";
    public static final String DOUBLE_END_ZERO = ".0";
    public static final String BOOLEAN = "boolean";
    public static final String IS = "is";
    public static final String GET = "get";
    public static final String E = "E";
    public static final String e = "e";
    public static final int DEFAULT_SCALE = 2;
    /**
     * Support precision types
     */
    private static final List<String> toStringTypeArray = Arrays.asList(
        "class java.lang.Long",
        "class java.lang.Integer",
        "long",
        "int");
    private static final List<String> toStringTypeDoubleArray = Arrays.asList(
        "class java.lang.Double",
        "double");
    private static final String DATE = "class java.util.Date";

    /**
     * The java object is converted to a JSONObject，ensure return value order，and precision (as strings)
     *
     * @param t   java object (VO)
     * @param tC  class
     * @param <T> paradigm flag
     * @return If null, return {} ，Otherwise the normal
     */
    public static final <T> JSONObject result(final T t, final Class<T> tC) {
        if (t == null) {
            return new JSONObject();
        }
        final Field[] fields = tC.getDeclaredFields();
        final Map<String, Object> map = new LinkedHashMap<>();
        try {
            for (final Field field : fields) {
                final String name = field.getName();
                final String type = field.getType().toString();
                final String methodName = JsonResultUtils.getMethodName(type, name);
                final Method[] methods = tC.getMethods();
                for (final Method method : methods) {
                    if (methodName.equals(method.getName())) {
                        final Object result = method.invoke(t);
                        if (JsonResultUtils.toStringTypeArray.contains(type)) {
                            map.put(field.getName(), JsonResultUtils.toString(result));
                        } else if (JsonResultUtils.toStringTypeDoubleArray.contains(type) && result != null) {
                            map.put(field.getName(), JsonResultUtils.toString((Double) result));
                        } else if (JsonResultUtils.DATE.contains(type) && result != null) {
                            map.put(field.getName(), ((Date)result).getTime());
                        } else {
                            map.put(field.getName(), result);
                        }
                    }
                }
            }
        } catch (final Exception e) {
            JsonResultUtils.log.error("Java biz bean change JSONObject is error: " + e.getMessage(), e);
        }
        return new JSONObject(map);
    }

    /**
     * The java object list is converted to a JSONArray，ensure return value order，and precision (as strings)
     *
     * @param list java object list (VO)
     * @param tC   class
     * @param <T>  paradigm flag
     * @return If null, return [] ，Otherwise the normal
     */
    public static final <T> JSONArray resultList(final List<T> list, final Class<T> tC) {
        final JSONArray array = new JSONArray();
        if (CollectionUtils.isEmpty(list)) {
            return array;
        }
        try {
            for (final T t : list) {
                array.add(JsonResultUtils.result(t, tC));
            }
        } catch (final Exception e) {
            JsonResultUtils.log.error("Java biz bean list change JSONArray is error: " + e.getMessage(), e);
        }
        return array;
    }

    public static final String getMethodName(final String type, final String field) {
        final StringBuilder methodName = new StringBuilder();
        if (type.equals(JsonResultUtils.BOOLEAN)) {
            methodName.append(JsonResultUtils.IS);
        } else {
            methodName.append(JsonResultUtils.GET);
        }
        return methodName.append(JsonResultUtils.startUpperCase(field)).toString();
    }

    public static final String startUpperCase(final String name) {
        final char[] cs = name.toCharArray();
        if (cs[0] >= 'a' && cs[0] <= 'z') {
            cs[0] -= 32;
        }
        return String.valueOf(cs);
    }

    public static final String toString(final Object object) {
        return object == null ? JsonResultUtils.DEFAULT_INT : object.toString();
    }

    /**
     * double to string
     */
    private static String toString(final Double arg) {
        if (arg == null) {
            return JsonResultUtils.DEFAULT_DOUBLE;
        }
        NumberFormat format = null;
        final String argStr = arg.toString();
        if (argStr.contains(JsonResultUtils.E) || argStr.contains(JsonResultUtils.e)) {
            final int scale = Integer.parseInt(argStr.toUpperCase().split(JsonResultUtils.E)[1]);
            if (scale < 0) {
                format = JsonResultUtils.getNumberFormat(Math.abs(scale));
            } else {
                format = JsonResultUtils.getNumberFormat(scale);
            }
        } else {
            format = JsonResultUtils.getNumberFormat(JsonResultUtils.DEFAULT_SCALE);
        }
        String result = format.format(arg);
        if (StringUtils.isNotEmpty(result)) {
            if (result.contains(JsonResultUtils.DOT) && result.endsWith(JsonResultUtils.DEFAULT_INT)) {
                result = StringUtils.removeEnd(result, "0");
            }
            if (result.endsWith(JsonResultUtils.DOUBLE_END_ZERO)) {
                result = StringUtils.removeEnd(result, JsonResultUtils.DOUBLE_END_ZERO);
            }
        }
        return result;
    }

    private static NumberFormat getNumberFormat(final int scale) {
        final NumberFormat format = NumberFormat.getInstance();
        // No comma
        format.setGroupingUsed(false);
        // Set the number of decimal places
        format.setMinimumFractionDigits(scale);
        return format;
    }
}
