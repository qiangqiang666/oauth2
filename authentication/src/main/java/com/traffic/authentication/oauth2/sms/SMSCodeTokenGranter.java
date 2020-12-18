package com.traffic.authentication.oauth2.sms;

import com.traffic.authentication.config.UserDetailsServiceImpl;
import com.traffic.authentication.constants.GlobalConstant;
import com.traffic.authentication.enums.ErrorCodeEnum;
import com.traffic.authentication.exception.ApiException;
import com.traffic.authentication.model.LoginModel;
import com.traffic.authentication.redis.CacheManager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.LinkedHashMap;
import java.util.Map;

public class SMSCodeTokenGranter extends AbstractTokenGranter {
    private static final String GRANT_TYPE = "sms_code";
    private UserDetailsServiceImpl userDetailsService;
    private CacheManager cacheManager;

    public SMSCodeTokenGranter(AuthorizationServerTokenServices tokenServices,
                               ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory,
                               UserDetailsServiceImpl userDetailsService, CacheManager cacheManager) {
        super(tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
        this.userDetailsService = userDetailsService;
        this.cacheManager = cacheManager;
    }

    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client,
                                                           TokenRequest tokenRequest) {

        Map<String, String> parameters = new LinkedHashMap<String, String>(tokenRequest.getRequestParameters());
        //客户端提交的用户名
        String userMobileNo = parameters.get("username").trim();
        //客户端提交的验证码
        String smscode = parameters.get("smscode").trim();

        if (StringUtils.isEmpty(userMobileNo) || StringUtils.isEmpty(smscode)) {
            throw new ApiException(ErrorCodeEnum.U7);
        }

        // 从库里查用户
        LoginModel user = userDetailsService.loadUserByUsername(userMobileNo);

        // 验证验证码
        String redisKey = String.format(GlobalConstant.SMS_PHONE_LOGIN, userMobileNo);
        try {
            String smsCodeCached = (String) cacheManager.get(redisKey);
            if (StringUtils.isBlank(smsCodeCached)) {
                throw new ApiException(ErrorCodeEnum.U4);
            }
            if (!smscode.equals(smsCodeCached)) {
                throw new ApiException(ErrorCodeEnum.U3);
            } else {
                cacheManager.remove(redisKey);
            }
        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            throw new ApiException(ErrorCodeEnum.FL1);
        }

        Authentication userAuth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        // 关于user.getAuthorities(): 我们的自定义用户实体是实现了
        // org.springframework.security.core.userdetails.UserDetails 接口的, 所以有 user.getAuthorities()
        // 当然该参数传null也行
        ((AbstractAuthenticationToken) userAuth).setDetails(parameters);

        OAuth2Request storedOAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);
        return new OAuth2Authentication(storedOAuth2Request, userAuth);
    }

}
