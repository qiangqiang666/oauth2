package com.traffic.server.unionPay.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: server
 * @description
 * @author: belive
 * @create: 2020-12-17 01:40
 **/
@Data
@ApiModel("退款请求参数")
public class RefundParam {
    @ApiModelProperty(value = "报文请求时间",required = true)
    private String requestTimestamp;

    @ApiModelProperty(value = "商户号",required = true)
    private String mid;
    @ApiModelProperty(value = "终端号",required = true)
    private String tid;
    @ApiModelProperty(value = "业务类型 YUEDANDEFAULT",required = true)
    private String instMid = "YUEDANDEFAULT";
    @ApiModelProperty(value = "支付订单号",required = true)
    private String targetOrderId;
    @ApiModelProperty(value = "原交易订单号",required = true)
    private String merOrderId;
    @ApiModelProperty(value = "要退款金额 单位分",required = true)
    private Long refundAmount;


}
