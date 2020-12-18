package com.traffic.server.model.miniPay.param;

import com.traffic.server.unionPay.param.GoodsParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @program: server
 * @description
 * @author: belive
 * @create: 2020-12-11 11:06
 **/
@Data
@ApiModel("小程序支付请求参数")
public class MiniPayParam {
    @NotNull(message = "商户订单号不能为空")
    @ApiModelProperty(value = "商户订单号 【生成订单编号规则: {来源编号(4位)}{时间(yyyyMMddmmHHssSSS)(17位)}{7位随机数}】",required = true)
    private String  merOrderId;
    @NotNull(message = "支付总金额必须大于1分")
    @Min(value = 100)
    @ApiModelProperty(value = "支付总金额 【单位: 分】",required = true)
    private Long totalAmount;

    @NotNull(message = "用户的openId不能为空")
    @ApiModelProperty(value = "用户子标识【微信必传，需要商户 自行调用微信平台接 口获取，具体获取方 式请根据微信接口文 档。】",required = true)
    private String subOpenId;

    @NotNull(message = "用户id不能为空")
    @ApiModelProperty(value = "用户id",required = true)
    private String userId;

    @ApiModelProperty(value = "商品信息")
    private List<GoodsParam> goodsParamList;
    @ApiModelProperty(value = "商品ID")
    private String productId;
    @ApiModelProperty(value = "订单过期时间 【为空则使用系统默认 过期时间(30分 钟)，格式yyyy-MM- dd HH:mm:ss】")
    private String expireTime;
    @ApiModelProperty(value = "账单描述【微信支付时上送值 长度不超过128字节】")
    private String orderDesc;








}
