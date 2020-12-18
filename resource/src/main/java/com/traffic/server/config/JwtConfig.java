package com.traffic.server.config;
 
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.annotation.Resource;

@Configuration
public class JwtConfig {

    @Resource
    private CustomAccessTokenConverter customAccessTokenConverter;

    /**
     * 生成具有jwt的token
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
        accessTokenConverter.setAccessTokenConverter(customAccessTokenConverter);
        //设置JWT使用签名
        accessTokenConverter.setSigningKey("29568a368d5eff3ff");
        return accessTokenConverter;
    }
 
    /**
     * token存储在jwt中
     */
    @Bean
    public TokenStore jwtTokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }
 
}