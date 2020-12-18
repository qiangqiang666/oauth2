package com.traffic.server.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class EnumUtils {
    public static final String CODE = "code";

    public EnumUtils() {
    }

    public static Field findCodeField(Class clazz, Class... codeAnnotations) {
        Field codeField = null;
        Field[] var3 = clazz.getDeclaredFields();
        int var4 = var3.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            Field field = var3[var5];
            if (field.getName().equals("code")) {
                codeField = field;
            }

            Field annotationField = getField(field, codeAnnotations);
            if (annotationField != null) {
                codeField = annotationField;
            }
        }

        return codeField;
    }

    private static Field getField(Field field, Class[] codeAnnotations) {
        if (codeAnnotations != null && codeAnnotations.length > 0) {
            Class[] var2 = codeAnnotations;
            int var3 = codeAnnotations.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                Class codeAnnotation = var2[var4];
                Annotation annotation = field.getAnnotation(codeAnnotation);
                if (annotation != null) {
                    return field;
                }
            }
        }

        return null;
    }

    public static Map<Object, Enum> getCodeToEnumMap(Field field) {
        Object[] enums = field.getDeclaringClass().getEnumConstants();
        Map<Object, Enum> codeToEnum = new HashMap();
        field.setAccessible(true);
        Object[] var3 = enums;
        int var4 = enums.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            Object enumObject = var3[var5];

            try {
                Object code = field.get(enumObject);
                codeToEnum.put(code, (Enum)enumObject);
            } catch (IllegalAccessException var8) {
                throw new IllegalArgumentException(var8);
            }
        }

        return codeToEnum;
    }

    public static Map<String, Enum> getStringCodeToEnumMap(Field field) {
        Object[] enums = field.getDeclaringClass().getEnumConstants();
        Map<String, Enum> codeToEnum = new HashMap();
        field.setAccessible(true);
        Object[] var3 = enums;
        int var4 = enums.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            Object enumObject = var3[var5];

            try {
                Object code = field.get(enumObject);
                if (code == null) {
                    codeToEnum.put(null, (Enum)enumObject);
                } else {
                    codeToEnum.put(String.valueOf(code), (Enum)enumObject);
                }
            } catch (IllegalAccessException var8) {
                throw new IllegalArgumentException(var8);
            }
        }

        return codeToEnum;
    }
}
