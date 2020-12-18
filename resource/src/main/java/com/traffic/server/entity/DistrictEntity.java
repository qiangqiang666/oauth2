package com.traffic.server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 描述:
 * 〈国省市区〉
 *
 * @author Monkey
 * @create 2020/12/16 11:21
 */
@Data
@TableName(value = "district")
public class DistrictEntity {

    @ApiModelProperty(value = "主键id")
    @TableId(type = IdType.AUTO)
    private Integer id;


    @TableField(value = "pid")
    @ApiModelProperty(value = "父类id")
    private Integer pid;


    @TableField(value = "type")
    @ApiModelProperty(value = "城市的类型，0是国，1是省，2是市，3是区")
    private Integer type;


    @TableField(value = "hierarchy")
    @ApiModelProperty(value = "地区所处的层级")
    private Integer hierarchy;


    @TableField(value = "district_sqe")
    @ApiModelProperty(value = "层级序列")
    private String districtSqe;

    @TableField(value = "district_name")
    @ApiModelProperty(value = "城市的名字")
    private String districtName;
}