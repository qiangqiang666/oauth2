package com.traffic.server.unionPay.param;

import com.alibaba.fastjson.JSONArray;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @program: server
 * @description
 * @author: belive
 * @create: 2020-12-17 01:13
 **/
@Data
@ApiModel("小程序下单请求参数")
public class ConsumeApplyParam {
    @ApiModelProperty(value = "报文请求时间",required = true)
    private String requestTimestamp;
    @ApiModelProperty(value = "商户订单号",required = true)
    private String merOrderId;
    @ApiModelProperty(value = "商户号",required = true)
    private String mid;
    @ApiModelProperty(value = "终端号",required = true)
    private String tid;
    @ApiModelProperty(value = "商品信息",required = true)
    private JSONArray goods;
    @ApiModelProperty(value = "微信子商户appId",required = true)
    private String subAppId;
    @ApiModelProperty(value = "用户子标识",required = true)
    private String subOpenId;
    @ApiModelProperty(value = "用户子标识",required = true)
    private String userId;
    @ApiModelProperty(value = "交易类型 值为MINI",required = true)
    private String tradeType = "MINI";
    @ApiModelProperty(value = "扫码点餐字端 值为pre_order",required = true)
    private String foodOrderType = "pre_order";
    @ApiModelProperty(value = "支付结果通知地址",required = false)
    private String notifyUrl;
    @ApiModelProperty(value = "账单描述",required = false)
    private String orderDesc;
    @ApiModelProperty(value = "支付总金额 单位分",required = false)
    private Long totalAmount;
    @ApiModelProperty(value = "分账标记 false 不分账",required = false)
    private Boolean divisionFlag = false;

    @ApiModelProperty(value = "商品ID")
    private String productId;
    @ApiModelProperty(value = "订单过期时间 【为空则使用系统默认 过期时间(30分 钟)，格式yyyy-MM- dd HH:mm:ss】")
    private String expireTime;






}
