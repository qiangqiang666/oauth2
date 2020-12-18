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
@ApiModel(value = "小程序支付响应数据")
public class MiniPayRes {
    @ApiModelProperty("平台错误码 SUCCESS:表示成功  INTERNAL_ERROR:表示内部错误  BAD_REQUEST:请求报文有错 TIMEOUT:处理超时 DENIED_IP:不允许此IP交易")
    private String errCode;
    @ApiModelProperty("平台错误信息")
    private String errMsg;
    @ApiModelProperty("报文响应时间")
    private String responseTimestamp;
    @ApiModelProperty("商户订单号 生成订单编号: {来源编号(4位)}{时间(yyyyMMddmmHHssSSS)(17位)}{7位随机数}")
    private String merOrderId;
    @ApiModelProperty("商户号")
    private String mid;
    @ApiModelProperty("商户名称")
    private String merName;
    @ApiModelProperty("终端号")
    private String tid;
    @ApiModelProperty("小程序支付用的请求报文，带有签名信息")
    private JSONObject miniPayRequest;
    @ApiModelProperty("平台流水号")
    private String seqId;
    @ApiModelProperty("交易状态 NEW_ORDER-新订单 UNKNOWN-不明确的交易状态")
    private String status;
    @ApiModelProperty("支付总金额 单位 分")
    private Long totalAmount;



}
