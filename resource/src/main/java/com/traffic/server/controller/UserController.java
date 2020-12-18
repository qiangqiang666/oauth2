package com.traffic.server.controller;

import com.traffic.server.entity.DistrictEntity;
import com.traffic.server.entity.MemberChangePhoneEntity;
import com.traffic.server.entity.MemberEntity;
import com.traffic.server.enums.ErrorCodeEnum;
import com.traffic.server.enums.MemberChangePhoneEnum;
import com.traffic.server.enums.SmsPhoneEnum;
import com.traffic.server.exception.ApiException;
import com.traffic.server.mapper.MemberChangePhoneMapper;
import com.traffic.server.model.request.user.IdentityDTO;
import com.traffic.server.model.request.user.PhoneDTO;
import com.traffic.server.model.request.user.UserDTO;
import com.traffic.server.oauth2.utils.SecurityUserHelperUtil;
import com.traffic.server.redis.CacheManager;
import com.traffic.server.service.DistrictService;
import com.traffic.server.service.MemberService;
import com.traffic.server.utils.BeanUtils;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * 描述:
 * 〈用户服务〉
 *
 * @author Monkey
 * @create 2020/12/10 14:49
 */
@Api(value = "【前台用户】用户接口", tags = "【前台用户】")
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private MemberService memberService;
    @Resource
    private MemberChangePhoneMapper memberChangePhoneMapper;
    @Resource
    private CacheManager cacheManager;
    @Resource
    private DistrictService districtService;


    @ApiOperation(value = "用户详情", notes = "GET")
    @GetMapping("/info")
    private MemberEntity getUserInfo() {
        MemberEntity memberEntity = memberService.selectUserByUserId(SecurityUserHelperUtil.getUserId());
        return memberEntity;
    }

    @ApiOperation(value = "修改用户信息", notes = "POST")
    @PostMapping("/update")
    private void updateUserInfo(@ApiParam @RequestBody @Validated UserDTO dto) {
        MemberEntity memberEntity = BeanUtils.copyProperties(dto, MemberEntity::new);
        memberEntity.setUserId(SecurityUserHelperUtil.getUserId());
        memberService.updateUserInfo(memberEntity);
    }

    @ApiOperation(value = "修改手机号校验", notes = "POST")
    @PostMapping("/update/phone")
    private void updatePhone(@ApiParam @RequestBody @Validated PhoneDTO dto) {
        if (!Objects.equals(SmsPhoneEnum.getEnum(dto.getType()), SmsPhoneEnum.SMS_UPDATE_PHONE)) {
            throw new ApiException(ErrorCodeEnum.SMS1002);
        }
        String cache = cacheManager.get(String.format(SmsPhoneEnum.getEnum(dto.getType()).msg(), dto.getPhone())).toString();
        if (StringUtils.isEmpty(cache)) {
            throw new ApiException(ErrorCodeEnum.SMS1003);
        } else {
            if (!Objects.equals(cache, dto.getCode())) {
                throw new ApiException(ErrorCodeEnum.SMS1004);
            }
        }
    }

    @ApiOperation(value = "新手机号绑定(申请)", notes = "POST")
    @PostMapping("/binding/phone")
    private void bindingNewPhone(@ApiParam @RequestBody @Validated PhoneDTO dto) {
        if (!Objects.equals(SmsPhoneEnum.getEnum(dto.getType()), SmsPhoneEnum.SMS_BINDING_PHONE)) {
            throw new ApiException(ErrorCodeEnum.SMS1002);
        }
        String cache = cacheManager.get(String.format(SmsPhoneEnum.getEnum(dto.getType()).msg(), dto.getPhone())).toString();
        if (StringUtils.isEmpty(cache)) {
            throw new ApiException(ErrorCodeEnum.SMS1003);
        } else {
            if (Objects.equals(cache, dto.getCode())) {
                // 验证码成功,插入变更表
                MemberEntity memberEntity = memberService.selectUserByUserId(SecurityUserHelperUtil.getUserId());
                MemberChangePhoneEntity memberChangePhoneEntity = new MemberChangePhoneEntity();
                memberChangePhoneEntity.setUserId(memberEntity.getUserId());
                memberChangePhoneEntity.setUserNickname(memberEntity.getNickName());
                memberChangePhoneEntity.setOriginalPhone(memberEntity.getPhone());
                memberChangePhoneEntity.setNewPhone(dto.getPhone());
                memberChangePhoneEntity.setStatus(MemberChangePhoneEnum.TO_AUDIT.code());
                memberChangePhoneMapper.insert(memberChangePhoneEntity);
            } else {
                throw new ApiException(ErrorCodeEnum.SMS1004);
            }
        }
    }

    @ApiOperation(value = "实名认证", notes = "POST")
    @PostMapping("/update/identity")
    private void updateIdentity(@ApiParam @RequestBody @Validated IdentityDTO dto) {
        MemberEntity memberEntity = memberService.selectUserByUserId(SecurityUserHelperUtil.getUserId());
        if (StringUtils.isNotEmpty(memberEntity.getIdentityCard()) && StringUtils.isNotEmpty(memberEntity.getUserName())) {
            throw new ApiException(ErrorCodeEnum.BF102);
        }
        memberEntity.setUserName(dto.getUserName());
        memberEntity.setIdentityCard(dto.getIdentityCard());
        memberEntity.setUserId(SecurityUserHelperUtil.getUserId());
        memberService.updateUserInfo(memberEntity);
    }

    @ApiOperation(value = "省市区数据", notes = "GET",response = DistrictEntity.class)
    @GetMapping("/address/info")
    private List<DistrictEntity> addressInfo() {
        return districtService.getList();
    }

}