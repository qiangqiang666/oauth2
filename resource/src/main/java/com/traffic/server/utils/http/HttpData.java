package com.traffic.server.utils.http;

import com.alibaba.fastjson.JSON;

public class HttpData {
    private Object data;

    public HttpData(Object data) {
        this.data = data;
    }

    public String toJson() {
        if (data == null)
            return null;
        return JSON.toJSONString(data);
    }

    public String toQs() {
        if (data == null)
            return null;
        return QsUtil.encode(data);
    }
}
