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
    * 用户信息(操作员)表
    */
@ApiModel(value="com-traffic-entity-User")
@Data
@TableName(value = "t_user")
public class UserEntity extends BaseEntity {
    /**
     * 用户名
     */
    @TableField(value = "user_no")
    @ApiModelProperty(value="用户名")
    private String userNo;

    /**
     * 真实姓名
     */
    @TableField(value = "user_name")
    @ApiModelProperty(value="真实姓名")
    private String userName;

    /**
     * 手机号
     */
    @TableField(value = "phone")
    @ApiModelProperty(value="手机号")
    private String phone;

    /**
     * 初始登陆密码
     */
    @TableField(value = "init_password")
    @ApiModelProperty(value="初始登陆密码")
    private String initPassword;

    /**
     * 角色 0财务，1运营，2客服
     */
    @TableField(value = "`role`")
    @ApiModelProperty(value="角色 0财务，1运营，2客服")
    private String role;

    /**
     * 是否启用 0否1是
     */
    @TableField(value = "`status`")
    @ApiModelProperty(value="是否启用 0否1是")
    private Boolean status;

}