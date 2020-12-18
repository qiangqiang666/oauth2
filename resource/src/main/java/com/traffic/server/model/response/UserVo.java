package com.traffic.server.model.response;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 描述:
 * 〈授权码模式: 用户信息〉
 *
 * @author Monkey
 * @create 2020/12/16 16:30
 */
@Data
public class UserVo {

    @NotBlank(message = "手机号")
    private String phone;

    @NotBlank(message = "昵称")
    private String nickName;

    @NotBlank(message = "图片地址")
    private String userImg;
}