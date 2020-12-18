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
    * 短信发送记录表
    */
@ApiModel(value="com-traffic-entity-SmsLog")
@Data
@TableName(value = "t_sms_log")
public class SmsLogEntity extends BaseEntity {

    /**
     * 发送内容
     */
    @TableField(value = "content")
    @ApiModelProperty(value="发送内容")
    private String content;

    /**
     * 用户手机号
     */
    @TableField(value = "phone")
    @ApiModelProperty(value="用户手机号")
    private String phone;

    /**
     * 状态 0发送成功1发送失败
     */
    @TableField(value = "`status`")
    @ApiModelProperty(value="状态 0发送成功1发送失败")
    private Boolean status;

}