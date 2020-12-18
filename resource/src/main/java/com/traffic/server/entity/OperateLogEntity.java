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
    * 操作日志表
    */
@ApiModel(value="com-traffic-entity-OperateLog")
@Data
@TableName(value = "t_operate_log")
public class OperateLogEntity extends BaseEntity {
    /**
     * 操作人IP
     */
    @TableField(value = "operator_ip")
    @ApiModelProperty(value="操作人IP")
    private String operatorIp;

    /**
     * 操作人账号
     */
    @TableField(value = "operator_no")
    @ApiModelProperty(value="操作人账号")
    private String operatorNo;

    /**
     * 地区
     */
    @TableField(value = "area")
    @ApiModelProperty(value="地区")
    private String area;

    /**
     * 操作描述
     */
    @TableField(value = "`describe`")
    @ApiModelProperty(value="操作描述")
    private String describe;

}