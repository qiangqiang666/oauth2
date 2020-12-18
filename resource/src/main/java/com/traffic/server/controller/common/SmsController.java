package com.traffic.server.controller.common;

import com.traffic.server.constants.GlobalConstant;
import com.traffic.server.entity.SmsLogEntity;
import com.traffic.server.enums.ErrorCodeEnum;
import com.traffic.server.enums.SmsPhoneEnum;
import com.traffic.server.exception.ApiException;
import com.traffic.server.mapper.SmsLogMapper;
import com.traffic.server.model.RestRes;
import com.traffic.server.redis.CacheManager;
import com.traffic.server.utils.RegexUtils;
import com.traffic.server.utils.sms.SendPhoneUtil;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 描述:
 * 〈短信〉
 *
 * @author Monkey
 * @create 2020/12/11 19:51
 */
@Api(value = "【短信】短信接口", tags = "【短信】")
@Slf4j
@RestController
@RequestMapping("/common/sms")
public class SmsController {

    @Resource
    private CacheManager cacheManager;
    @Resource
    private SmsLogMapper smsLogMapper;


    @ApiOperation(value = "发送短信验证码", notes = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header",name = "Authorization",value = "凭证",defaultValue = "Bearer ")
    })
    @GetMapping("/phone")
    private RestRes sendPhoneCode(@RequestParam @ApiParam(value = "手机号", required = true) String phone,
                                  @RequestParam @ApiParam(value = "类别(1-登录,2-修改手机号校验,2-绑定新手机号)", required = true) Integer type) {
        if (SmsPhoneEnum.getEnum(type) == null) {
            throw new ApiException(ErrorCodeEnum.SMS1002);
        }
        if (!RegexUtils.validateMobilePhone(phone)) {
            throw new ApiException(ErrorCodeEnum.SMS1006);
        }
        String code = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
        SmsLogEntity smsLogEntity = new SmsLogEntity();
        smsLogEntity.setPhone(phone);
        smsLogEntity.setContent(code);
        try {
            if (SendPhoneUtil.sendCode(phone, code)) {
                cacheManager.put(code, String.format(SmsPhoneEnum.getEnum(type).msg(), phone), GlobalConstant.REDIS_SECONDS);
                smsLogEntity.setStatus(true);
                smsLogMapper.insert(smsLogEntity);
                return RestRes.succeed(code);
            } else {
                smsLogEntity.setStatus(false);
                smsLogMapper.insert(smsLogEntity);
                throw new ApiException(ErrorCodeEnum.SMS1001);
            }
        }catch (ApiException e){
            smsLogEntity.setStatus(false);
            smsLogMapper.insert(smsLogEntity);
            throw e;
        }
    }

}