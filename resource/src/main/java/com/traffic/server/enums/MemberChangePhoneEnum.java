package com.traffic.server.enums;

/**
 * @author Monkey
 */
public enum MemberChangePhoneEnum implements CodeBaseEnum{
    TO_AUDIT(0, "待审核"),
    AUDIT_NO(1,"审核未通过"),
    AUDIT_YES(2,"审核已通过");

    private int code;

    private String msg;

    MemberChangePhoneEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static MemberChangePhoneEnum getEnum(int code) {
        for (MemberChangePhoneEnum ele : MemberChangePhoneEnum.values()) {
            if (ele.code() == code) {
                return ele;
            }
        }
        return null;
    }

    @Override
    public String msg() {
        return msg;
    }

    @Override
    public int code() {
        return code;
    }
}