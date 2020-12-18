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
@ApiModel("退款查询请求参数")
public class RefundQueryParam {
    @ApiModelProperty(value = "报文请求时间",required = true)
    private String requestTimestamp;

    @ApiModelProperty(value = "商户号",required = true)
    private String mid;
    @ApiModelProperty(value = "终端号",required = true)
    private String tid;
    @ApiModelProperty(value = "业务类型 MINIDEFAULT",required = true)
    private String instMid = "MINIDEFAULT";
    @ApiModelProperty(value = "支付订单号",required = true)
    private String targetOrderId;
    @ApiModelProperty(value = "退货订单号",required = true)
    private String merOrderId;



}
