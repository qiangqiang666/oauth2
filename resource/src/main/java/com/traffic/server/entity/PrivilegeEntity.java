package com.traffic.server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
* @program: traffic_server
* @description
* @author: lijinhuai
* @create: 2020/12/9 15:56
**/

/**
    * 用户权限表
    */
@ApiModel(value="com-traffic-entity-Privilege")
@Data
@TableName(value = "t_privilege")
public class PrivilegeEntity implements Serializable {
    /**
     * 自增id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="自增id")
    private Long id;

    /**
     * 权限名称
     */
    @TableField(value = "`name`")
    @ApiModelProperty(value="权限名称")
    private String name;

    /**
     * 接口路径
     */
    @TableField(value = "url")
    @ApiModelProperty(value="接口路径")
    private String url;

    /**
     * 上级id
     */
    @TableField(value = "parent_id")
    @ApiModelProperty(value="上级id")
    private Long parentId;

    /**
     * 菜单类型0路由 1操作按钮
     */
    @TableField(value = "menu_type")
    @ApiModelProperty(value="菜单类型0路由 1操作按钮")
    private Integer menuType;

    /**
     * 描述
     */
    @TableField(value = "description")
    @ApiModelProperty(value="描述")
    private String description;

    /**
     * 状怿默认0 -1删除 -2禁用
     */
    @TableField(value = "`status`")
    @ApiModelProperty(value="状怿默认0 -1删除 -2禁用")
    private Byte status;

    /**
     * 默认1  0表示不展示  1-展示
     */
    @TableField(value = "shows")
    @ApiModelProperty(value="默认1  0表示不展示  1-展示")
    private Byte shows;

    /**
     * 唯一值
     */
    @TableField(value = "key_name")
    @ApiModelProperty(value="唯一值")
    private String keyName;

    /**
     * 创建时间
     */
    @TableField(value = "create_at")
    @ApiModelProperty(value="创建时间")
    private Date createAt;

    /**
     * 更新时间
     */
    @TableField(value = "update_at")
    @ApiModelProperty(value="更新时间")
    private Date updateAt;

    private static final long serialVersionUID = 1L;
}