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
    * 群组表
    */
@ApiModel(value="com-traffic-entity-Group")
@Data
@TableName(value = "t_group")
public class GroupEntity extends BaseEntity {
    /**
     * 编号
     */
    @TableField(value = "group_no")
    @ApiModelProperty(value="编号")
    private Integer groupNo;

    /**
     * 群组名称
     */
    @TableField(value = "group_name")
    @ApiModelProperty(value="群组名称")
    private String groupName;

    /**
     * 创建人员
     */
    @TableField(value = "creator")
    @ApiModelProperty(value="创建人员")
    private String creator;

    /**
     * 接收对象
     */
    @TableField(value = "receiving_object")
    @ApiModelProperty(value="接收对象")
    private String receivingObject;

    /**
     * 描述
     */
    @TableField(value = "`describe`")
    @ApiModelProperty(value="描述")
    private String describe;

    /**
     * 用户标签
     */
    @TableField(value = "user_tag")
    @ApiModelProperty(value="用户标签")
    private String userTag;

}