package com.traffic.server.oauth2;

import com.alibaba.fastjson.JSONObject;
import com.traffic.server.enums.ErrorCodeEnum;
import com.traffic.server.model.RestRes;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class TokenFilter extends OncePerRequestFilter {

    private static final  String ACCESS_TOKEN = "access_token";
    private static final  String AUTHORIZATION = "Authorization";
    private static final  String AUTHORIZATION_BEARER = "Bearer";
    private static final  String CONTENT_TYPE = "application/json;charset=UTF-8";


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String uri=request.getRequestURI();
        if(uri.contains("/doc.html")
                || uri.contains("/swagger-resources")
                || uri.contains("/webjars")
                || uri.contains("/common")
                || uri.contains("/v2")){
            filterChain.doFilter(request,response);
        }else{
            boolean access_token=false;
            boolean authorization=false;
            if(request.getParameter(ACCESS_TOKEN)==null){
                access_token=true;
            }
            if(request.getHeader(AUTHORIZATION)==null){
                authorization=true;
            }else{
                if(!request.getHeader(AUTHORIZATION).startsWith(AUTHORIZATION_BEARER)){
                    authorization=true;
                }
            }
            if(access_token&&authorization){
                response.setContentType(CONTENT_TYPE);
                response.getWriter().write(JSONObject.toJSONString(RestRes.failed(ErrorCodeEnum.U1)));
            }else{
                filterChain.doFilter(request,response);
            }
        }
    }
}

