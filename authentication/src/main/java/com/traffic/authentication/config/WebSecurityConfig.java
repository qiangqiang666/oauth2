package com.traffic.authentication.config;

import com.traffic.authentication.FromLoginConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private UserDetailsServiceImpl userDetailsService;

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    /**
     * 配置用户登录验证服务
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //String secret = passwordEncoder().encode("123456");
        //System.out.println(secret);
        auth.userDetailsService(userDetailsService);
    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                //表单登录,loginPage为登录请求的url,loginProcessingUrl为表单登录处理的URL
//                .formLogin().loginPage("/authentication/require").loginProcessingUrl(FromLoginConstant.LOGIN_PROCESSING_URL).failureForwardUrl("/erroraa")
//                //允许访问
//                .and().authorizeRequests().antMatchers(
//                "/user/hello",
//                FromLoginConstant.LOGIN_PROCESSING_URL,
//                FromLoginConstant.LOGIN_PAGE,
//                "/oauthLogin",
//                "/grant","/erroraa").permitAll().anyRequest().authenticated()
//                //禁用跨站伪造
//                .and().csrf();
//
//    }
    @Override
    public void configure(WebSecurity web) {
        //"oauth/check_token"是校验token的地址
        web.ignoring().antMatchers("/doc.html","/swagger-resources/**", "/webjars/**", "/v2/**","/mini/auth/**");
    }

    /**
     * 必须注入，验证密码需要使用
     * @return
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
 