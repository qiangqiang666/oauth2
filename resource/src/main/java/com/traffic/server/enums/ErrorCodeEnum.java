package com.traffic.server.enums;


import com.traffic.server.exception.ApiException;

public enum ErrorCodeEnum implements CodeBaseEnum {
    /**
     * 全局通用
     */
    SC0(0, "成功"),
    FL1(1, "失败"),
    FL2(2, "网络繁忙,请稍后重试"),
    FL3(3, "操作过于频繁，请稍候再试"),
    FL4(4, "手机号格式不正确"),
    FL5(5, "参数异常"),

    U1(401, "未获得凭证"),
    U2(402, "用户名或密码错误"),
    U3(403,"token无效或已过期！"),
    U4(401,"权限不足，不允许访问！"),
    U5(401,"请求的scope无效或超出应用许可范围"),

    BF101(101, "用户已存在"),
    BF102(102, "已经实名认证了"),

    SMS1000(1000,"发送短信异常"),
    SMS1001(1001,"发送短信失败"),
    SMS1002(1002,"短信类别,参数异常"),
    SMS1003(1003,"未获取到验证码"),
    SMS1004(1004,"验证码错误"),
    SMS1005(1005,"手机号或验证码不能为空"),
    SMS1006(1006,"手机号不符合发送规则"),

    CAR2000(2000,"车辆已被绑定"),
    CAR2001(2001,"最多绑定3辆车"),

    UNION01(10001,"银联访问网络异常"),
    UNION02(10002,"获取签名失败"),
    UNION03(10003,"数据加密异常"),

    ;

    private int code;

    private String msg;

    ErrorCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ApiException ThrowParamsException(String feildName) {
        throw new ApiException(ErrorCodeEnum.FL1.code(), String.format(ErrorCodeEnum.FL1.msg(), feildName));
    }

    /**
     * Gets enum.
     *
     * @param code the code
     * @return the enum
     */
    public static ErrorCodeEnum getEnum(int code) {
        for (ErrorCodeEnum ele : ErrorCodeEnum.values()) {
            if (ele.code() == code) {
                return ele;
            }
        }
        return null;
    }

    /**
     * Msg string.
     *
     * @return the string
     */
    public String msg() {
        return msg;
    }

    /**
     * Code int.
     *
     * @return the int
     */
    public int code() {
        return code;
    }
}
