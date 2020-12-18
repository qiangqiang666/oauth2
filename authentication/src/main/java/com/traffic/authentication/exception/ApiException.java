package com.traffic.authentication.exception;


import com.traffic.authentication.enums.CodeBaseEnum;

public class ApiException extends RuntimeException {
    private int code;

    public static ApiException fromCode(CodeBaseEnum ee) {
        return new ApiException(ee);
    }

    public static void thr(CodeBaseEnum ee) {
        throw new ApiException(ee);
    }

    public static void touch(boolean touch, CodeBaseEnum ee) {
        if (touch) {
            thr(ee);
        }

    }

    public ApiException(int code, String msg) {
        super(msg);
        this.code = code;
    }

    public ApiException(CodeBaseEnum ee) {
        super(ee.msg());
        this.code = ee.code();
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
