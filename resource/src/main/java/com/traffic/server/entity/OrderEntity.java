package com.traffic.server.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
* @program: traffic_server
* @description
* @author: lijinhuai
* @create: 2020/12/9 15:56
**/

/**
    * 订单表
    */
@ApiModel(value="com-traffic-entity-Order")
@Data
@TableName(value = "t_order")
public class OrderEntity extends BaseEntity {
    /**
     * 订单号
     */
    @TableField(value = "order_no")
    @ApiModelProperty(value="订单号")
    private Long orderNo;

    /**
     * 商户ID
     */
    @TableField(value = "merchant_id")
    @ApiModelProperty(value="商户ID")
    private String merchantId;

    /**
     * 商户名称
     */
    @TableField(value = "merchant_name")
    @ApiModelProperty(value="商户名称")
    private String merchantName;

    /**
     * 用户ID
     */
    @TableField(value = "user_id")
    @ApiModelProperty(value="用户ID")
    private Long userId;

    /**
     * 用户手机号
     */
    @TableField(value = "user_phone")
    @ApiModelProperty(value="用户手机号")
    private String userPhone;

    /**
     * 用户名称
     */
    @TableField(value = "user_name")
    @ApiModelProperty(value="用户名称")
    private String userName;

    /**
     * 车牌号
     */
    @TableField(value = "car_num")
    @ApiModelProperty(value="车牌号")
    private String carNum;

    /**
     * 业务类型 0停车缴费1美团单车2哈罗单车3青桔单车4新能源5停车包月6预约停车
     */
    @TableField(value = "`type`")
    @ApiModelProperty(value="业务类型 0停车缴费1美团单车2哈罗单车3青桔单车4新能源5停车包月6预约停车")
    private Boolean type;

    /**
     * 支付时间
     */
    @TableField(value = "pay_time")
    @ApiModelProperty(value="支付时间")
    private Date payTime;

    /**
     * 优惠金额
     */
    @TableField(value = "discount_amount")
    @ApiModelProperty(value="优惠金额")
    private BigDecimal discountAmount;

    /**
     * 支付金额
     */
    @TableField(value = "pay_amount")
    @ApiModelProperty(value="支付金额")
    private BigDecimal payAmount;

    /**
     * 实付金额
     */
    @TableField(value = "pay_actually_amount")
    @ApiModelProperty(value="实付金额")
    private BigDecimal payActuallyAmount;

    /**
     * 支付状态 0支付成功
     */
    @TableField(value = "pay_status")
    @ApiModelProperty(value="支付状态 0支付成功")
    private Boolean payStatus;

    /**
     * 支付方式 0包月支付
     */
    @TableField(value = "pay_way")
    @ApiModelProperty(value="支付方式 0包月支付")
    private Boolean payWay;

}