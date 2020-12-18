package com.traffic.server.config;

import com.traffic.server.oauth2.RestAuthAccessDeniedHandler;
import com.traffic.server.oauth2.TokenExceptionEntryPoint;
import com.traffic.server.oauth2.TokenFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.annotation.Resource;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Resource
    private TokenFilter tokenFilter;
    @Resource
    private TokenExceptionEntryPoint tokenExceptionEntryPoint;
    @Resource
    private RestAuthAccessDeniedHandler restAuthAccessDeniedHandler;

    /**
     * 处理异常
     * @param resources
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.authenticationEntryPoint(tokenExceptionEntryPoint);
        resources.accessDeniedHandler(restAuthAccessDeniedHandler);
    }

    /**
     * 配置资源服务器
     *
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        // 验证所有请求
        http.authorizeRequests()
                // 允许 路径地址 不需要通过oauth2登录授权,配合com.traffic.server.oauth2.TokenFilter类,来排查不需要验证的路由
                .antMatchers("/doc.html","/swagger-resources/**", "/webjars/**", "/v2/**","/sms/**","/swagger-resources","/common/**").permitAll()
                // 限制第三方授权码登录的token,请求路由的范围
                .antMatchers("/open/**").access("#oauth2.hasScope('read')")
                // 全部请求,必须走scope验证
                .antMatchers("/**").access("#oauth2.hasScope('all')")
                .and()
                .httpBasic()
                .and()
                .csrf().disable();
        //处理未带凭证
        http.addFilterBefore(tokenFilter, BasicAuthenticationFilter.class);

    }

}
 