package com.traffic.server.enums;

import com.traffic.server.constants.GlobalConstant;

/**
 * 短信类别
 * @author Monkey
 */
public enum SmsPhoneEnum implements CodeBaseEnum {
    SMS_LOGIN(1, GlobalConstant.SMS_PHONE_LOGIN),
    SMS_UPDATE_PHONE(2,GlobalConstant.SMS_UPDATE_PHONE),
    SMS_BINDING_PHONE(3,GlobalConstant.SMS_BINDING_PHONE);

    private int code;

    private String msg;

    SmsPhoneEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static SmsPhoneEnum getEnum(int code) {
        for (SmsPhoneEnum ele : SmsPhoneEnum.values()) {
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
