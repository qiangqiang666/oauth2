package com.traffic.server.oauth2;

import com.alibaba.fastjson.JSONObject;
import com.traffic.server.enums.ErrorCodeEnum;
import com.traffic.server.model.RestRes;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 处理没有权限的类
 * @author zhoukebo
 * @date 2018/9/5
 */
@Component
public class RestAuthAccessDeniedHandler implements AccessDeniedHandler {
    private static final  String CONTENT_TYPE = "application/json;charset=UTF-8";

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException {
        response.setContentType(CONTENT_TYPE);
        response.getWriter().write(JSONObject.toJSONString(RestRes.failed(ErrorCodeEnum.U5)));
    }
}