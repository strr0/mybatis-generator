package com.strr.code.common;

import java.lang.reflect.Field;

/**
 * 实体工具
 */
public class EntityUtil {
    public static void setValue(Object entity, String key, Object value) {
        Class<?> clazz = entity.getClass();
        try {
            Field field = clazz.getDeclaredField(key);
            field.setAccessible(true);
            field.set(entity, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
