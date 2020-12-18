package com.traffic.server.utils.http;

import com.alibaba.fastjson.TypeReference;

import java.util.Map;

@SuppressWarnings("WeakerAccess")
public class HttpUtil {
    public static String get(String url) {
        return get(url, null, null);
    }

    public static String get(String url, Object params) {
        return get(url, params, null);
    }

    public static String get(String url, Object params, Map<String, String> headers) {
        return new HttpClient().get(url, params, headers);
    }

    public static <T> T get(Class<T> clazz, String url) {
        return get(clazz, url, null, null);
    }

    public static <T> T get(Class<T> clazz, String url, Object params) {
        return get(clazz, url, params, null);
    }

    public static <T> T get(Class<T> clazz, String url, Object params, Map<String, String> headers) {
        return new HttpClient().get(clazz, url, params, headers);
    }

    public static <T> T get(TypeReference<T> type, String url) {
        return get(type, url, null, null);
    }

    public static <T> T get(TypeReference<T> type, String url, Object params) {
        return get(type, url, params, null);
    }

    public static <T> T get(TypeReference<T> type, String url, Object params, Map<String, String> headers) {
        return new HttpClient().get(type, url, params, headers);
    }

    public static String post(String url, Object data) {
        return post(url, data, null);
    }

    public static String post(String url, Object data, Map<String, String> headers) {
        return new HttpClient().post(url, data, headers);
    }

    public static <T> T post(Class<T> clazz, String url, Object data) {
        return post(clazz, url, data, null);
    }

    public static <T> T post(Class<T> clazz, String url, Object data, Map<String, String> headers) {
        return new HttpClient().post(clazz, url, data, headers);
    }

    public static <T> T post(TypeReference<T> type, String url, Object data) {
        return post(type, url, data, null);
    }

    public static <T> T post(TypeReference<T> type, String url, Object data, Map<String, String> headers) {
        return new HttpClient().post(type, url, data, headers);
    }
}
