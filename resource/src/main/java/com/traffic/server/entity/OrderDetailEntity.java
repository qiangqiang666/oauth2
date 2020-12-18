package com.traffic.server.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
* @program: traffic_server
* @description
* @author: lijinhuai
* @create: 2020/12/9 15:56
**/

/**
    * 订单详情表
    */
@ApiModel(value="com-traffic-entity-OrderDetail")
@Data
@TableName(value = "t_order_detail")
public class OrderDetailEntity extends BaseEntity {
    /**
     * 订单编号
     */
    @TableField(value = "order_no")
    @ApiModelProperty(value="订单编号")
    private Long orderNo;

    /**
     * 预约地址
     */
    @TableField(value = "reservation_address")
    @ApiModelProperty(value="预约地址")
    private String reservationAddress;

    /**
     * 预约单号
     */
    @TableField(value = "reservation_no")
    @ApiModelProperty(value="预约单号")
    private Long reservationNo;

    /**
     * 预约时间
     */
    @TableField(value = "reservation_time")
    @ApiModelProperty(value="预约时间")
    private Date reservationTime;

    /**
     * 停车资源预留时间
     */
    @TableField(value = "keep_time")
    @ApiModelProperty(value="停车资源预留时间")
    private String keepTime;

    /**
     * 停车场
     */
    @TableField(value = "car_park")
    @ApiModelProperty(value="停车场")
    private String carPark;

    /**
     * 停车场区域
     */
    @TableField(value = "car_area")
    @ApiModelProperty(value="停车场区域")
    private String carArea;

    /**
     * 入场时间
     */
    @TableField(value = "in_time")
    @ApiModelProperty(value="入场时间")
    private Date inTime;

    /**
     * 停车时长
     */
    @TableField(value = "duration")
    @ApiModelProperty(value="停车时长")
    private String duration;

    /**
     * 出场时间
     */
    @TableField(value = "off_time")
    @ApiModelProperty(value="出场时间")
    private Date offTime;

}