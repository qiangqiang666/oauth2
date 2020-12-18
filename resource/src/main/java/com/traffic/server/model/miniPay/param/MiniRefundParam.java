package com.traffic.server.model.miniPay.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @program: server
 * @description
 * @author: belive
 * @create: 2020-12-17 01:40
 **/
@Data
@ApiModel("退款请求参数")
public class MiniRefundParam {
    @NotNull(message = "支付订单号不能为空")
    @ApiModelProperty(value = "商户订单号(退货订单号)【生成订单编号规则: {来源编号(4位)}{时间(yyyyMMddmmHHssSSS)(17位)}{7位随机数}】",required = true)
    private String targetOrderId;
    @NotNull(message = "用户id不能为空")
    @ApiModelProperty(value = "用户id",required = true)
    private Long userId;

    @ApiModelProperty(value = "退款描述")
    private String orderDesc;



}
