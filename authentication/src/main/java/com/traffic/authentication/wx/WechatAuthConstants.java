package com.traffic.authentication.wx;

/**
 * @author rihong.g
 * @date 2020-01-11 12:12
 * 微信授权相关常量
 */
public interface WechatAuthConstants {

    /**
     * 微信小程序授权url
     */
    String MINI_AUTH_CHECK_URL = "https://api.weixin.qq.com/sns/jscode2session?appid=";

    /**
     * 微信H5登录授权url
     */
    String H5_AUTH_CHECK_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=";
    /**
     * 获取用户微信账号基本信息url
     */
    String USER_INFO_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=";


    /*** 用户的标识，对当前开发者帐号唯一
     * 一个微信开放平台账号可能由多个应用，这个用户在这多个应用下的openId不一样*/
    String OPENID = "openid";
    /**
     * 微信授权凭证
     */
    String ACCESS_TOKEN = "access_token";

    /**
     * 用户统一标识。针对一个微信开放平台帐号下的应用，同一用户的unionId是唯一的。
     * 一个微信开放平台账号可能由多个应用，这个用户在这多个应用下的unionId一样
     */
    String UNIONID = "unionid";

    /*** 用户微信昵称*/
    String NICKNAME = "nickname";

    /**
     * 小程序用户性别 性别 0：未知、1：男、2：女
     */
    String GENDER = "gender";

    /**
     * 网页端、移动端微信用户性别 1 为男性，2 为女性
     */
    String SEX = "sex";

    /**
     * 用户微信头像url
     */
    String HEAD_IMG_URL = "headimgurl";


    /**================h5==========*/
    /**
     * token缓存key
     */
    String H5_ACCESS_TOKEN_REDIS_KEY = "h5:accessToken:key";

    /**
     * jspTicket缓存key
     */
    String WECHAT_JSP_TICKET_REDIS_KEY = "wechat:jsp:ticket:redis:key";

    /**
     * 获取微信js跳转临时凭据url
     */
    String JSAPI_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=";

    /**
     * 获取token的url
     */
    String MINI_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";

    /**
     * token缓存key
     */
    String MINI_ACCESS_TOKEN_REDIS_KEY = "mini:accessToken:key";

}
