package com.traffic.server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
* @program: server
*
* @description
*
* @author: belive
*
* @create: 2020-12-17 02:50
**/
/**
    * 支付流水单
    */
@Data
@TableName(value = "t_order_pay_not")
public class OrderPayNotEntity {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 支付类型[1：微信  2:支付宝 3:银行卡]
     */
    @TableField(value = "pay_type")
    private Integer payType;

    /**
     * 支付平台单流水号
     */
    @TableField(value = "pay_code")
    private String payCode;

    /**
     * 业务方订单编号
     */
    @TableField(value = "order_no")
    private String orderNo;
    /**
     * 我方生成业务订单编号
     */
    @TableField(value = "biz_order_no")
    private String bizOrderNo;

    /**
     * 支付状态[0 失败；1:成功 ]
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 支付总金额 单位 分
     */
    @TableField(value = "total_amount")
    private Long totalAmount;

    /**
     * 支付完成时间
     */
    @TableField(value = "finish_time")
    private Date finishTime;

    /**
     * 业务类型[ 0 停车缴费 1 美团单车 2 哈罗单车 3 青桔单车 4 新能源 5 停车包月 6 预约停车 ]
     */
    @TableField(value = "type")
    private Integer type;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;
    @TableField(value = "code")
    private String code;
    @TableField(value = "info")
    private String info;
}