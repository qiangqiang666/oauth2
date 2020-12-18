package com.traffic.server.utils.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;

public class JsonUtil {
    private static final SimplePropertyPreFilter filter = new SimplePropertyPreFilter();

    static {
        filter.getExcludes().add("apiMethod");
        filter.getExcludes().add("responseClass");
    }

    public static String toJson(Object o) {

        try {
            return JSON.toJSONString(o, filter);
        } catch (Exception e) {
            return "";
        }
    }

    public static JSONObject fromJson(String json) {
        return JSON.parseObject(json);
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return JSON.parseObject(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException("paser json error " + json, e);
        }
    }

}
