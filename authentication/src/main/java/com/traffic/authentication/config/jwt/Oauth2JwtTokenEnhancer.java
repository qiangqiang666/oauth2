package com.traffic.authentication.config.jwt;

import com.traffic.authentication.model.LoginModel;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

public class Oauth2JwtTokenEnhancer implements TokenEnhancer {

    /**
     * 扩展JWT中的内容，oAuth2Authentication中存储有登陆成功后的用户信息
     * @param oAuth2AccessToken
     * @param oAuth2Authentication
     * @return
     */
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
        Map<String, Object> info = new HashMap<>();
        //扩展JWT内容
        LoginModel loginModel = (LoginModel) oAuth2Authentication.getPrincipal();
        info.put("userId", loginModel.getMemberEntity().getUserId());
        ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(info);
        return oAuth2AccessToken;
    }


}