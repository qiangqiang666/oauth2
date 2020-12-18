package com.traffic.server.controller;
 
import com.traffic.server.model.RestRes;
import com.traffic.server.oauth2.utils.SecurityUserHelperUtil;
import io.jsonwebtoken.Jwts;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.Map;


@Api(value = "【测试】测试类",tags = "测试类")
@Slf4j
@RestController
public class ResourceController {
 
    @RequestMapping("resource")
    public String resource() {
        return "ResourceServer Module";
    }

    @GetMapping("/get")
    public Object getCurrentUser(Authentication authentication, HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        String token = header.replace( "Bearer ", "");
        return Jwts.parser()
                .setSigningKey("29568a368d5eff3ff".getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(token)
                .getBody();
    }

    @RequestMapping("/user")
    public Principal user(Principal principal){
        return principal;
    }
    @RequestMapping("/tokenInfo")
    public Map<String,Object> userInfo(){
        return SecurityUserHelperUtil.getTokenInfo();
    }

    @RequestMapping("/id")
    public Long id(){
        return SecurityUserHelperUtil.getUserId();
    }
    @RequestMapping("/test")
    @PreAuthorize("hasRole('admin')")
    public RestRes test(){
        return RestRes.succeed(null);
    }

    @RequestMapping("/test1")
    @PreAuthorize("#oauth2.hasScope('read')")
    public RestRes test1(){
        return RestRes.succeed(null);
    }

    @RequestMapping("/test2")
    @PreAuthorize("#oauth2.hasScope('read1')")
    public RestRes test2(){
        return RestRes.succeed(null);
    }

}