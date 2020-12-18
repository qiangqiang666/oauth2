package com.traffic.server.oauth2;

import com.alibaba.fastjson.JSONObject;
import com.traffic.server.enums.ErrorCodeEnum;
import com.traffic.server.model.RestRes;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class TokenExceptionEntryPoint extends OAuth2AuthenticationEntryPoint {
    private static final  String CONTENT_TYPE = "application/json;charset=UTF-8";
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setContentType(CONTENT_TYPE);
        httpServletResponse.getWriter().write(JSONObject.toJSONString(RestRes.failed(ErrorCodeEnum.U3)));
    }
}

