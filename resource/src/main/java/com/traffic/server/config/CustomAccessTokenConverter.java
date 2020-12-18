package com.traffic.server.config;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Cheng Jun
 * @version 1.0
 * @Description: 定制 AccessToken 转换器，为添加额外信息在服务器端获取做准备
 * @date 2018/12/24 16:05
 */
@Component
public class CustomAccessTokenConverter extends DefaultAccessTokenConverter {

    @Override
    public OAuth2Authentication extractAuthentication(Map<String, ?> claims) {
        OAuth2Authentication authentication
                = super.extractAuthentication(claims);
        authentication.setDetails(claims);
        return authentication;
    }
}

