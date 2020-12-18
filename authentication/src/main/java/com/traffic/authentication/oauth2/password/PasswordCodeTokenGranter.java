package com.traffic.authentication.oauth2.password;

import com.traffic.authentication.config.UserDetailsServiceImpl;
import com.traffic.authentication.enums.ErrorCodeEnum;
import com.traffic.authentication.exception.ApiException;
import com.traffic.authentication.model.LoginModel;
import com.traffic.authentication.redis.CacheManager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.LinkedHashMap;
import java.util.Map;

public class PasswordCodeTokenGranter extends AbstractTokenGranter {
    private static final String GRANT_TYPE = "password_code";
    private UserDetailsServiceImpl userDetailsService;
    private CacheManager cacheManager;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public PasswordCodeTokenGranter(AuthorizationServerTokenServices tokenServices,
                               ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory,
                               UserDetailsServiceImpl userDetailsService, CacheManager cacheManager,BCryptPasswordEncoder bCryptPasswordEncoder) {
        super(tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
        this.userDetailsService = userDetailsService;
        this.cacheManager = cacheManager;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client,
                                                           TokenRequest tokenRequest) {

        Map<String, String> parameters = new LinkedHashMap<String, String>(tokenRequest.getRequestParameters());
        //客户端提交的用户名
        String username = parameters.get("username").trim();
        //客户端提交的密码
        String password = parameters.get("password").trim();

        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
            throw new ApiException(ErrorCodeEnum.U6);
        }

        // 从库里查用户
        LoginModel loginModel = userDetailsService.loadUserByUsername(username);

        //校验密码
        if (!bCryptPasswordEncoder.matches(password, loginModel.getMemberEntity().getPassword())){
            throw new ApiException(ErrorCodeEnum.U2);
        }


        Authentication userAuth = new UsernamePasswordAuthenticationToken(loginModel, null,loginModel.getAuthorities());
        // 关于user.getAuthorities(): 我们的自定义用户实体是实现了
        // org.springframework.security.core.userdetails.UserDetails 接口的, 所以有 user.getAuthorities()
        // 当然该参数传null也行
        ((AbstractAuthenticationToken) userAuth).setDetails(parameters);

        OAuth2Request storedOAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);
        return new OAuth2Authentication(storedOAuth2Request, userAuth);
    }

}
