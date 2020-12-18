package com.traffic.authentication.wx;

/**
 * @author rihong.g
 * @date 2020-02-21 13:25
 */
public interface MiniMsgConstants {
    /**
     * token缓存key
     */
    String MINI_ACCESS_TOKEN_REDIS_KEY = "mini:accessToken:key";


    /**
     * 获取token的url
     */
    String MINI_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
    /**
     * 小程序发送短信
     */
    String MINI_SEND_MSG_URL = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=";

    /**
     * 小程序通知页面跳转url
     */
    String ALARM_TRIGGER_URL = "ALARM_TRIGGER_URL";


    /**
     * 小程序通知页面跳转url
     */
    String ALARM_UPGRADE_URL = "ALARM_UPGRADE_URL";

    /**
     * 小程序通知页面跳转url
     */
    String ALARM_TIMEOUT_URL = "ALARM_TIMEOUT_URL";

    /**
     * 小程序通知页面跳转url
     */
    String ALARM_TIMEOUT_DESC = "已超时";

    String ALARM_HANDLE_DESC = "未处理";


}
