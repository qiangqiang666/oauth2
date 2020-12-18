package com.traffic.server.utils.http;

import com.alibaba.fastjson.TypeReference;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@SuppressWarnings("WeakerAccess")
@Getter
@Setter
public abstract class AbstractHttpClient {
    protected String url = null;
    protected String charset = "utf-8";
    protected int connectionTimeout = 15 * 1000;
    protected int readTimeout = 30 * 1000;
    protected String contentType = ContentType.FORM_DATA;
    protected String method = "get";
    protected Map<String, String> headers;
    protected HttpData body;
    protected HttpData param;


    public abstract String request();

    public abstract <T> T request(Class<T> clazz);

    public abstract <T> T request(TypeReference<T> type);

    public String get(String url) {
        return get(url, null, null);
    }

    public String get(String url, Object params) {
        return get(url, params, null);
    }

    public String get(String url, Object params, Map<String, String> headers) {
        build(HttpMethod.GET, url, params, null, headers);
        return request();
    }

    public <T> T get(Class<T> clazz, String url) {
        return get(clazz, url, null, null);
    }

    public <T> T get(Class<T> clazz, String url, Object params) {
        return get(clazz, url, params, null);
    }

    public <T> T get(Class<T> clazz, String url, Object params, Map<String, String> headers) {
        build(HttpMethod.GET, url, params, null, headers);
        return request(clazz);
    }

    public <T> T get(TypeReference<T> type, String url) {
        return get(type, url, null, null);
    }

    public <T> T get(TypeReference<T> type, String url, Object params) {
        return get(type, url, params, null);
    }

    public <T> T get(TypeReference<T> type, String url, Object params, Map<String, String> headers) {
        build(HttpMethod.GET, url, params, null, headers);
        return request(type);
    }

    public String post(String url, Object data) {
        return post(url, data, null);
    }

    public String post(String url, Object data, Map<String, String> headers) {
        build(HttpMethod.POST, url, null, data, headers);
        return request();
    }

    public <T> T post(Class<T> clazz, String url, Object data) {
        return post(clazz, url, data, null);
    }

    public <T> T post(Class<T> clazz, String url, Object data, Map<String, String> headers) {
        build(HttpMethod.POST, url, null, data, headers);
        return request(clazz);
    }

    public <T> T post(TypeReference<T> type, String url, Object data) {
        return post(type, url, data, null);
    }

    public <T> T post(TypeReference<T> type, String url, Object data, Map<String, String> headers) {
        build(HttpMethod.POST, url, null, data, headers);
        return request(type);
    }

    private void build(String method, String url, Object params, Object data, Map<String, String> headers) {
        setMethod(method);
        setUrl(url);
        setParam(new HttpData(params));
        setBody(new HttpData(data));
        setHeaders(headers);
    }
}