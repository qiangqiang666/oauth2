package com.traffic.server.utils.http;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class QsUtil {
    static String encode(Map<String, String> data) {
        return encode(data, data.keySet().toArray(new String[0]));
    }

    public static String encode(Map<String, String> data, String[] keys) {
        List<String> list = new ArrayList<>();
        try {
            String k, v;
            for (String key : keys) {
                k = URLEncoder.encode(key, "utf-8");
                v = URLEncoder.encode(data.get(key), "utf-8");
                list.add(String.format("%s=%s", k, v));
            }
        } catch (UnsupportedEncodingException ignored) {

        }
        return String.join("&", list);
    }

    public static String encode(Object data) {
        List<String> list = new ArrayList<>();
        String k, v;
        if (data instanceof Map) {
            try {
                for (Map.Entry<?, ?> entry : ((Map<?, ?>) data).entrySet()) {
                    k = URLEncoder.encode(entry.getKey().toString(), "utf-8");
                    v = URLEncoder.encode(entry.getValue().toString(), "utf-8");
                    list.add(String.format("%s=%s", k, v));
                }
            } catch (UnsupportedEncodingException ignored) {

            }
        } else {
            try {
                Field field;
                Method getter;
                Class supClazz;
                Class clazz = data.getClass();
                BeanInfo beanInfo  = Introspector.getBeanInfo(clazz, Object.class);
                PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();

                for (PropertyDescriptor descriptor : descriptors) {
                    getter = descriptor.getReadMethod();
                    field = null;
                    supClazz = clazz;
                    k = descriptor.getDisplayName();
                    while (field == null && supClazz != null && !supClazz.equals(Object.class)) {
                        try {
                            field = supClazz.getDeclaredField(k);
                        } catch (Exception ignored){

                        } finally {
                            supClazz = supClazz.getSuperclass();
                        }
                    }
                    if (getter == null || field == null) {
                        continue;
                    }
                    v = getter.invoke(data).toString();
                    SimpleHttp simpleHttp = field.getAnnotation(SimpleHttp.class);
                    if (simpleHttp != null) {
                        if(simpleHttp.ignore())
                            continue;
                        if (!"".equals(simpleHttp.value()))
                            k = simpleHttp.value();
                    }
                    k = URLEncoder.encode(k, "utf-8");
                    v = URLEncoder.encode(v, "utf-8");
                    list.add(String.format("%s=%s", k, v));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return String.join("&", list);
    }
}
