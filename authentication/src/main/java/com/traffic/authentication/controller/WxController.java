package com.traffic.authentication.controller;

import com.traffic.authentication.redis.SyncLockManage;
import com.traffic.authentication.wx.WxAuthService;
import com.traffic.authentication.wx.dto.WeChatAuthDTO;
import com.traffic.authentication.wx.dto.WeChatInfoResultDTO;
import com.traffic.authentication.wx.dto.WechatInfoParamDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;


/**
 * @Description:
 * @Author: candol
 * @Date: 2020/8/17
 **/
@Api(value = "【小程序】微信登录认证接口",tags = "【小程序】")
@Slf4j
@RestController
@RequestMapping("/mini/auth")
public class WxController {

    @Resource
    private WxAuthService wxAuthService;
	@Resource
	private SyncLockManage lock;
    /**1
     * 授权码获取小程序openId
     *
     * @param code
     * @return
     */
    @ApiOperation(value = "授权码获取小程序openId",notes = "GET")
    @ApiImplicitParam(name ="code" ,required = true)
    @GetMapping
    private WeChatAuthDTO getAndRegisterOpenId(@RequestParam @ApiParam("微信授权码【必填】") String code, @ApiIgnore HttpServletResponse response) {
        log.info("微信小程序登录授权:{}", code);
        return wxAuthService.miniProGetOpenIdAndRegister(code, response);
    }

    /**
     * 获取用户基本信息
     *
     * @return
     */
    @PostMapping("/wechat/info")
    @ApiOperation(value = "获取用户微信", httpMethod = "POST")
    public WeChatInfoResultDTO info(@RequestBody @ApiParam WechatInfoParamDTO paramDTO) {
        return wxAuthService.miniProgramGetUserInfo(paramDTO);
    }

}
