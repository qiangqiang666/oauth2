package com.traffic.authentication.oauth2.wx;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.traffic.authentication.entity.MemberEntity;
import com.traffic.authentication.entity.WechatInfoEntity;
import com.traffic.authentication.enums.ErrorCodeEnum;
import com.traffic.authentication.exception.ApiException;
import com.traffic.authentication.mapper.MemberMapper;
import com.traffic.authentication.model.LoginModel;
import com.traffic.authentication.redis.CacheManager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class WXCodeTokenGranter extends AbstractTokenGranter {
    private static final String GRANT_TYPE = "vx_code";
    private MemberMapper memberMapper;
    private CacheManager cacheManager;

    public WXCodeTokenGranter(AuthorizationServerTokenServices tokenServices,
                              ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory,
                              MemberMapper memberMapper, CacheManager cacheManager) {
        super(tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
        this.memberMapper = memberMapper;
        this.cacheManager = cacheManager;
    }

    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client,
                                                           TokenRequest tokenRequest) {

        Map<String, String> parameters = new LinkedHashMap<String, String>(tokenRequest.getRequestParameters());
        //客户端提交的openid
        String vxopenid = parameters.get("vxopenid").trim();

        if(StringUtils.isEmpty(vxopenid)){
            throw new ApiException(ErrorCodeEnum.U8);
        }

        // 从库里查用户
        QueryWrapper<MemberEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(WechatInfoEntity.COL.OPEN_ID.getColName(), vxopenid);
        MemberEntity wechatInfoEntity = memberMapper.selectOne(queryWrapper);
        if (null == wechatInfoEntity) {
            throw new ApiException(ErrorCodeEnum.U5);
        }
        if (wechatInfoEntity.getDelFlag()){
            throw new ApiException(ErrorCodeEnum.U9);
        }
        LoginModel loginModel = new LoginModel(wechatInfoEntity, null);

        Authentication userAuth = new UsernamePasswordAuthenticationToken(loginModel, null,new ArrayList<>());
        // 关于user.getAuthorities(): 我们的自定义用户实体是实现了
        // org.springframework.security.core.userdetails.UserDetails 接口的, 所以有 user.getAuthorities()
        // 当然该参数传null也行
        ((AbstractAuthenticationToken) userAuth).setDetails(parameters);

        OAuth2Request storedOAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);
        return new OAuth2Authentication(storedOAuth2Request, userAuth);
    }

}
