package cc.newex.dax.extra.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liutiejun
 * @date 2018-08-07
 */
@Slf4j
public class ReflectUtils {

    /**
     * 获取所有属性的值
     *
     * @param object
     * @return
     */
    public static Map<String, String> getAllFieldValue(final Object object) {
        if (object == null) {
            return null;
        }

        final Field[] fields = object.getClass().getDeclaredFields();
        if (ArrayUtils.isEmpty(fields)) {
            return null;
        }

        final Map<String, String> fieldValueMap = new HashMap<>();

        for (final Field field : fields) {
            final String value = getFieldValue(object, field);

            fieldValueMap.put(field.getName(), value);
        }

        return fieldValueMap;
    }

    /**
     * 获取某一个属性的值
     *
     * @param object
     * @param field
     * @return
     */
    private static String getFieldValue(final Object object, final Field field) {
        makeAccessible(field);

        Object value = null;
        try {
            value = field.get(object);
        } catch (final IllegalAccessException e) {
            log.error(e.getMessage(), e);
        }

        if (value != null) {
            return String.valueOf(value);
        }

        return null;
    }

    /**
     * 强制转换field可访问
     *
     * @param field
     */
    private static void makeAccessible(final Field field) {
        if (!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers())) {
            field.setAccessible(true);
        }
    }

}
