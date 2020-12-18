package com.traffic.server.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
* @program: traffic_server
* @description
* @author: lijinhuai
* @create: 2020/12/9 15:56
**/

/**
    * 支付渠道表
    */
@ApiModel(value="com-traffic-entity-PayChannel")
@Data
@TableName(value = "t_pay_channel")
public class PayChannelEntity extends BaseEntity {

    /**
     * 渠道名称 0银联1微信
     */
    @TableField(value = "channel_name")
    @ApiModelProperty(value="渠道名称 0银联1微信")
    private String channelName;

    /**
     * 支付方式 0微信支付，1支付宝支付，2云闪付
     */
    @TableField(value = "pay_way")
    @ApiModelProperty(value="支付方式 0微信支付，1支付宝支付，2云闪付")
    private Boolean payWay;

    /**
     * 费率
     */
    @TableField(value = "rate")
    @ApiModelProperty(value="费率")
    private BigDecimal rate;

    /**
     * 状态 0正常1关闭
     */
    @TableField(value = "`status`")
    @ApiModelProperty(value="状态 0正常1关闭")
    private Boolean status;

}