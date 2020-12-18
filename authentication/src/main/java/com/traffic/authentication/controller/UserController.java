//package com.traffic.authentication.controller;
//
//import com.traffic.authentication.model.RegisterDTO;
//import com.traffic.authentication.service.MemberService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.annotation.Resource;
//
///**
// * 描述:
// * 〈用户服务〉
// *
// * @author Monkey
// * @create 2020/12/10 14:49
// */
//@Api(value = "【认证服务】用户接口")
//@Slf4j
//@RestController
//@RequestMapping("/aaa")
//public class UserController {
//
//    @Resource
//    private MemberService memberService;
//
//    @ApiOperation(value = "注册接口", notes = "POST")
//    @PostMapping
//    private Object register(@RequestBody RegisterDTO registerDTO) {
//        return null;
//    }
//}