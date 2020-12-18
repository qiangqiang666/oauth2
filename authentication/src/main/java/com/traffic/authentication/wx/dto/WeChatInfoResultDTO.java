package com.traffic.authentication.wx.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author rihong.g
 * @date 2019-07-17 16:35
 * 微信授权登录返回结果
 */
@Data
@ApiModel("获取用户微信信息返回结果")
public class WeChatInfoResultDTO {

    @ApiModelProperty("OPEN_ID")
    private String openId;

    @ApiModelProperty("UNION_ID")
    private String unionId;

    /**
     * 用户微信昵称
     */
    @ApiModelProperty("微信昵称")
    private String nickname;

    /**
     * 用户微信头像url
     */
    @ApiModelProperty("微信用户头像")
    private String headImg;


    /**
     * 用户性别 0：未知、1：男、2：女
     */
    @ApiModelProperty("用户性别 0：未知、1：男、2：女")
    private Integer sex;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("用户名称")
    private String userName;

}
