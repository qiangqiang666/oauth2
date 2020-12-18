package com.traffic.server.enums;


public enum DelFlagEnum {
    nomal(0, "正常"),
    unnomal(1, "故障"),
    ;

    private int code;

    private String msg;

    public String msg() {
        return msg;
    }

    public int code() {
        return code;
    }

    DelFlagEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String getName(int code) {
        for (DelFlagEnum ele : DelFlagEnum.values()) {
            if (ele.code() == code) {
                return ele.msg;
            }
        }
        return "";
    }

    public static DelFlagEnum getEnum(int code) {
        for (DelFlagEnum ele : DelFlagEnum.values()) {
            if (ele.code() == code) {
                return ele;
            }
        }
        return null;
    }
}
