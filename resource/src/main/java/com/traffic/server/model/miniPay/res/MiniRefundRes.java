package com.traffic.server.model.miniPay.res;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: server
 * @description
 * @author: belive
 * @create: 2020-12-11 11:08
 **/
@Data
@ApiModel(value = "小程序退款响应数据")
public class MiniRefundRes {
    @ApiModelProperty("平台错误码 SUCCESS:表示成功  INTERNAL_ERROR:表示内部错误  BAD_REQUEST:请求报文有错 TIMEOUT:处理超时 DENIED_IP:不允许此IP交易")
    private String errCode;
    @ApiModelProperty("平台错误信息")
    private String errMsg;
    @ApiModelProperty("报文响应时间")
    private String responseTimestamp;
    @ApiModelProperty("退款状态")
    private String refundStatus;
    @ApiModelProperty("退货订单号")
    private String refundOrderId;
    @ApiModelProperty("目标系统退货订单号")
    private String refundTargetOrderId;
    @ApiModelProperty("平台流水号")
    private String seqId;
    @ApiModelProperty("交易状态 NEW_ORDER-新订单 UNKNOWN-不明确的交易状态")
    private String status;
    @ApiModelProperty("商户订单号")
    private String merOrderId;

    @ApiModelProperty("商户实退金额 单位 分")
    private String sendBackAmount;
    @ApiModelProperty("实付部分退款 单位 分")
    private Long refundInvoiceAmount;
    @ApiModelProperty("支付时间 格式yyyy-MM-dd HH:mm:ss")
    private String payTime;
    @ApiModelProperty("结算日期 格式yyyy-MM-dd")
    private String settleDate;
    @ApiModelProperty("退款渠道描述")
    private String refundFundsDesc;




}
