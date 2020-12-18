package com.traffic.authentication.config;

import com.traffic.authentication.config.jwt.Oauth2JwtTokenEnhancer;
import com.traffic.authentication.mapper.MemberMapper;
import com.traffic.authentication.oauth2.password.PasswordCodeTokenGranter;
import com.traffic.authentication.oauth2.sms.SMSCodeTokenGranter;
import com.traffic.authentication.oauth2.wx.WXCodeTokenGranter;
import com.traffic.authentication.redis.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableAuthorizationServer
public class Oauth2AuthenticationServer extends AuthorizationServerConfigurerAdapter {

    @Resource
    public DataSource dataSource;

    @Resource
    private UserDetailsServiceImpl userDetailsService;

    @Resource
    private TokenStore tokenStore;

    @Resource
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Resource
    private Oauth2JwtTokenEnhancer oauth2JwtTokenEnhancer;

    @Resource
    private WebResponseExceptionTranslator webResponseExceptionTranslator;

    @Bean
    public ClientDetailsService jdbcClientDetailsService() {
        return new JdbcClientDetailsService(dataSource);
    }

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Resource
    private MemberMapper memberMapper;

    @Resource
    private CacheManager cacheManager;

    /**
     * 配置客户端
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(jdbcClientDetailsService());

    }

    /**
     * 用来配置授权（authorization）以及令牌（token）的访问端点和令牌服务(token services)
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> enhancerList = new ArrayList<>();
        //配置JWT的内容增强器
        enhancerList.add(oauth2JwtTokenEnhancer);
        enhancerList.add(jwtAccessTokenConverter);
        enhancerChain.setTokenEnhancers(enhancerList);

        //初始化
        endpoints.accessTokenConverter(jwtAccessTokenConverter)
                //设置将token存储在JWT中
                .tokenStore(tokenStore)
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                //认证异常处理器
                .exceptionTranslator(webResponseExceptionTranslator)
                .reuseRefreshTokens(true)
                // 允许 GET、POST 请求获取 token，即访问端点：oauth/token
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
                .tokenEnhancer(enhancerChain);

        /*
         * 默认获取token的路径是/oauth/token，通过pathMapping方法，可改变默认路径
         * pathMapping用来配置端点URL链接，有两个参数，都将以 "/" 字符为开始的字符串
         * defaultPath：这个端点URL的默认链接
         * customPath：你要进行替代的URL链接
         */
        endpoints.pathMapping("/oauth/token", "/oauth/token");

        /*
         * 除oauth2默认自带的模式外,额外添加自定义模式
         */
        SMSCodeTokenGranter smsCodeTokenGranter = new SMSCodeTokenGranter(endpoints.getTokenServices(),
                endpoints.getClientDetailsService(), endpoints.getOAuth2RequestFactory(), userDetailsService, cacheManager);
        WXCodeTokenGranter vxCodeTokenGranter = new WXCodeTokenGranter(endpoints.getTokenServices(),
                endpoints.getClientDetailsService(), endpoints.getOAuth2RequestFactory(), memberMapper, cacheManager);
        PasswordCodeTokenGranter passwordCodeTokenGranter = new PasswordCodeTokenGranter(endpoints.getTokenServices(),
                endpoints.getClientDetailsService(), endpoints.getOAuth2RequestFactory(), userDetailsService, cacheManager, bCryptPasswordEncoder);
        CompositeTokenGranter compositTokenGrant =
                new CompositeTokenGranter(Arrays.asList(endpoints.getTokenGranter(), smsCodeTokenGranter, vxCodeTokenGranter, passwordCodeTokenGranter));
        endpoints.tokenGranter(compositTokenGrant);

    }

    /**
     * 配置资源服务器向认证服务器请求及验证token的规则：默认允许获取token，但是需要授权后才能获取到
     * 过来验令牌有效性的请求，不是谁都能验的，必须要是经过身份认证的。
     * 所谓身份认证就是，必须携带clientId，clientSecret，否则随便一请求过来验token是不验的
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {

        //默认允许获取token，但是需要授权后才能获取到 ，所以可以去掉 tokenKeyAccess()
        security.checkTokenAccess("isAuthenticated()");
        //允许客户端使用表单方式发送请求token的认证（因为表单一般是POST请求，所以使用POST方式发送获取token，但必须携带clientId，clientSecret，否则随便一请求过来验token是不验的）
        security.allowFormAuthenticationForClients();
    }

}
 
 
 