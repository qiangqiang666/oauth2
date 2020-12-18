package com.traffic.authentication.wx.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author rihong.g
 * @date 2020-02-12 17:59
 */
@Data
@ApiModel("获取用户微信信息请求参数")
public class WechatInfoParamDTO {

    /**
     * 用户openId
     */
    @ApiModelProperty("【必填】OPEN_ID")
    private String openId;
    /**
     * 加密数据
     */
    @ApiModelProperty("【必填】微信加密数据")
    private String encryptedData;

    /**
     * 加密向量
     */
    @ApiModelProperty("【必填】微信加密向量")
    private String iv;


}
