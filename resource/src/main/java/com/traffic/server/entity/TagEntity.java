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
    * 标签表
    */
@ApiModel(value="com-traffic-entity-Tag")
@Data
@TableName(value = "t_tag")
public class TagEntity extends BaseEntity {
    /**
     * 标签名称
     */
    @TableField(value = "tag_name")
    @ApiModelProperty(value="标签名称")
    private String tagName;

    /**
     * 会员人数
     */
    @TableField(value = "membership")
    @ApiModelProperty(value="会员人数")
    private Integer membership;

}