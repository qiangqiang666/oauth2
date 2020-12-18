package com.traffic.server.oauth2.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import java.util.Map;

/**
 * 描述:
 * 〈Security获取当前登录信息工具类〉
 *
 * @author Monkey
 * @create 2020/7/9 15:20
 */
public class SecurityUserHelperUtil {

    /**
     * 获取当前token详细信息
     * @return
     */
    public static Map<String,Object> getTokenInfo(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
        return (Map<String,Object>)details.getDecodedDetails();
    }

    /**
     * 获取当前登录用户主键id
     * @return
     */
    public static Long getUserId(){
        Map<String, Object> tokenInfo = getTokenInfo();
        return Long.parseLong(tokenInfo.get("userId").toString());
    }

    /**
     * 获取当前登录用户手机号
     * @return
     */
    public static String getUserPhone(){
        Map<String, Object> tokenInfo = getTokenInfo();
        return tokenInfo.get("user_name").toString();
    }
}