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
    * 用户角色表
    */
@ApiModel(value="com-traffic-entity-Role")
@Data
@TableName(value = "t_role")
public class RoleEntity extends BaseEntity {
    /**
     * 角色名称
     */
    @TableField(value = "role_name")
    @ApiModelProperty(value="角色名称")
    private String roleName;

    /**
     * 描述
     */
    @TableField(value = "`describe`")
    @ApiModelProperty(value="描述")
    private String describe;

    /**
     * 是否启用 0否1是
     */
    @TableField(value = "stauts")
    @ApiModelProperty(value="是否启用 0否1是")
    private Boolean stauts;

}