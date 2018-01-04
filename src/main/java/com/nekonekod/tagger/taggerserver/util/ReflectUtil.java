package com.nekonekod.tagger.taggerserver.util;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * Created by duwenjun on 30/08/2017.
 */
public class ReflectUtil extends ReflectionUtils {

    /**
     * get field value of target
     *
     * @param fieldName
     * @param target
     * @return
     */
    public static Object getField(String fieldName, Object target) {
        Field field = findField(target.getClass(), fieldName);
        makeAccessible(field);
        return getField(field, target);
    }

    /**
     * set field value for target
     *
     * @param fieldName
     * @param target
     * @param value
     */
    public static void setField(String fieldName, Object target, Object value) {
        Field field = findField(target.getClass(), fieldName);
        makeAccessible(field);
        setField(field, target, value);
    }

    /**
     * check clazz has field named fieldName , including super class
     *
     * @param fieldName
     * @param clazz
     * @return
     */
    public static boolean hasField(String fieldName, Class clazz) {
        try {
            return clazz.getDeclaredField(fieldName) != null;
        } catch (Exception ignored) {
        }
        if (clazz != Object.class) {
            return hasField(fieldName, clazz.getSuperclass());
        }
        return false;
    }

    /**
     * 判断clazz的field类型是否是target
     *
     * @param fieldName
     * @param clazz
     * @param target    must be class , not interface
     * @return
     */
    public static boolean validateType(String fieldName, Class clazz, Class target) {
        Field field = findField(clazz, fieldName);
        if (field == null) return false;
        Class type = field.getType();
        //判断type
        if (type.equals(target)) return true;
        //判断 type 父类
        while (!type.equals(Object.class)) {
            type = type.getSuperclass();
            if (type.equals(target)) return true;
        }
        return false;
    }

}
