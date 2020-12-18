package com.traffic.server.controller.open;

import com.traffic.server.entity.MemberEntity;
import com.traffic.server.model.response.UserVo;
import com.traffic.server.oauth2.utils.SecurityUserHelperUtil;
import com.traffic.server.service.MemberService;
import com.traffic.server.utils.BeanUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 描述:
 * 〈对外APi〉
 *
 * @author Monkey
 * @create 2020/12/16 16:28
 */
@Api(value = "【对外APi】提供给第三方接口",tags = "【对外APi】")
@Slf4j
@RestController
@RequestMapping("/open")
public class OpenController {

    @Resource
    private MemberService memberService;

    @ApiOperation(value = "用户详情", notes = "GET")
    @GetMapping("/info")
    private UserVo getUserInfo() {
        MemberEntity memberEntity = memberService.selectUserByUserId(SecurityUserHelperUtil.getUserId());
        return BeanUtils.copyProperties(memberEntity, UserVo::new);
    }

}