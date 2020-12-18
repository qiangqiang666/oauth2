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
    * 用户更改手机记录表
    */
@ApiModel(value="com-traffic-entity-MemberChangePhone")
@Data
@TableName(value = "t_member_change_phone")
public class MemberChangePhoneEntity extends BaseEntity {

    /**
     * 用户ID
     */
    @TableField(value = "user_id")
    @ApiModelProperty(value="用户ID")
    private Long userId;

    /**
     * 用户昵称
     */
    @TableField(value = "user_nickname")
    @ApiModelProperty(value="用户昵称")
    private String userNickname;

    /**
     * 原有手机号
     */
    @TableField(value = "original_phone")
    @ApiModelProperty(value="原有手机号")
    private String originalPhone;

    /**
     * 新手机号
     */
    @TableField(value = "new_phone")
    @ApiModelProperty(value="新手机号")
    private String newPhone;

    /**
     * 状态 0待审核，1审核未通过，2审核已通过
     */
    @TableField(value = "`status`")
    @ApiModelProperty(value="状态 0待审核，1审核未通过，2审核已通过")
    private Integer status;

    /**
     * 审核意见
     */
    @TableField(value = "approve_note")
    @ApiModelProperty(value="审核意见")
    private String approveNote;

    /**
     * 审核人
     */
    @TableField(value = "approver")
    @ApiModelProperty(value="审核人")
    private String approver;

    /**
     * 审核时间
     */
    @TableField(value = "approve_time")
    @ApiModelProperty(value="审核时间")
    private Date approveTime;

}