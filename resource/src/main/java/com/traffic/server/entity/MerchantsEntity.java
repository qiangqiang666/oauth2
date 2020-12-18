package com.traffic.server.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
* @program: traffic_server
* @description
* @author: lijinhuai
* @create: 2020/12/9 15:56
**/

/**
    * 商户表
    */
@ApiModel(value="com-traffic-entity-Merchants")
@Data
@TableName(value = "t_merchants")
public class MerchantsEntity extends BaseEntity {
    @TableField(value = "image_url")
    @ApiModelProperty(value="")
    private String imageUrl;



    /**
     * 权值
     */
    @TableField(value = "sort")
    @ApiModelProperty(value="权值")
    private Byte sort;

    /**
     * 商户ID
     */
    @TableField(value = "merchant_id")
    @ApiModelProperty(value="商户ID")
    private String merchantId;

    /**
     *  商户名称
     */
    @TableField(value = "`name`")
    @ApiModelProperty(value=" 商户名称")
    private String name;

    /**
     * 商户手机号
     */
    @TableField(value = "phone")
    @ApiModelProperty(value="商户手机号")
    private String phone;

    /**
     * 登陆账号
     */
    @TableField(value = "login_no")
    @ApiModelProperty(value="登陆账号")
    private String loginNo;

    /**
     * 初始密码
     */
    @TableField(value = "init_password")
    @ApiModelProperty(value="初始密码")
    private String initPassword;

    /**
     * 启用状态 0否1是
     */
    @TableField(value = "`status`")
    @ApiModelProperty(value="启用状态 0否1是")
    private Boolean status;

}