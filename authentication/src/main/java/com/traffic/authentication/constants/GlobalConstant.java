package com.traffic.authentication.constants;

import org.springframework.stereotype.Component;

/**
 * @author lvhaibao
 * @description
 * @date 2018/12/26 0026 18:19
 */
@Component
public class GlobalConstant {
    /**
     * redis 有效期,5分钟
     */
    public static final int REDIS_SECONDS = 300;

    /**
     *  登录: 发送手机短信redis前缀
     */
    public static final String SMS_PHONE_LOGIN = "sms:phone:login:%s";







}
