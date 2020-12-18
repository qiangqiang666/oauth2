package com.traffic.authentication.wx.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @Author:luoxf
 * @Description:
 * @Date: 10:17 下午 2020/8/21
 */
@Data
@AllArgsConstructor
@Builder
@ApiModel("获取小程序OPEN_ID返回结果")
public class WeChatAuthDTO {

    @ApiModelProperty("OPEN_ID")
    private String openId;

    @ApiModelProperty("会员号")
    private Long memberId;

}
