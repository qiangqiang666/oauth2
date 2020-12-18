package com.traffic.server.utils.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("WeakerAccess")
public class HttpGo extends HttpUtil {
    private HttpClient client;
    private String baseUrl;
    private String resBody;

    public static HttpGo goWithUrl(String url) {
        return new HttpGo(url);
    }

    public static HttpGo create(String baseUrl) {
        while (baseUrl.endsWith("/") && baseUrl.length() > 1){
            baseUrl = baseUrl.substring(0, baseUrl.length() -1);
        }

        HttpGo httpGo = new HttpGo();
        httpGo.baseUrl = baseUrl;
        return httpGo;
    }

    public HttpGo() {

    }

    public HttpGo(String url) {
        this.client = new HttpClient();
        client.setUrl(url);
    }

    public HttpGo go(String url) {
        if (this.baseUrl != null) {
            if (!url.startsWith("/"))
                url = "/" + url;
            url = this.baseUrl + url;
        }
        return new HttpGo(url);
    }

    public HttpGo header(String key, String value) {
        Map<String, String> headers = this.client.getHeaders();
        if (headers == null) {
            headers = new HashMap<>();
            this.client.setHeaders(headers);
        }
        headers.put(key, value);
        return this;
    }

    public HttpGo param(Object param) {
        this.client.setParam(new HttpData(param));
        return this;
    }

    public HttpGo body(Object body) {
        this.client.setBody(new HttpData(body));
        return this;
    }

    public HttpGo jsonContent() {
        this.client.setContentType(ContentType.JSON_UTF8);
        return this;
    }

    public HttpGo formContent() {
        this.client.setContentType(ContentType.FORM_DATA);
        return this;
    }

    public HttpGo content(String contentType) {
        this.client.setContentType(contentType);
        return this;
    }

    public HttpGo get() {
        this.client.setMethod(HttpMethod.GET);
        this.resBody = this.client.request();
        return this;
    }

    public HttpGo post() {
        this.client.setMethod(HttpMethod.POST);
        this.resBody = this.client.request();
        return this;
    }

    public String res() {
        return this.resBody;
    }

    public <T> T res(Class<T> clazz) {
        return JSON.parseObject(this.resBody, clazz);
    }

    public <T> T res(TypeReference<T> type) {
        return JSON.parseObject(this.resBody, type);
    }
}
