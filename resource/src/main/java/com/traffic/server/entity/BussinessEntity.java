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
* @create: 2020/12/9 15:55
**/

/**
    * 业务列表
    */
@ApiModel(value="com-traffic-entity-Bussiness")
@Data
@TableName(value = "t_bussiness")
public class BussinessEntity extends BaseEntity {

    /**
     * 商户ID
     */
    @TableField(value = "company_id")
    @ApiModelProperty(value="商户ID")
    private Long companyId;

    /**
     * 商户名称
     */
    @TableField(value = "company_name")
    @ApiModelProperty(value="商户名称")
    private String companyName;

    /**
     * 业务名称
     */
    @TableField(value = "business_name")
    @ApiModelProperty(value="业务名称")
    private String businessName;

    /**
     * 上架日期
     */
    @TableField(value = "on_date")
    @ApiModelProperty(value="上架日期")
    private Date onDate;

    /**
     * 下架日期
     */
    @TableField(value = "down_date")
    @ApiModelProperty(value="下架日期")
    private Date downDate;

    /**
     * 接入方式
     */
    @TableField(value = "access_mode")
    @ApiModelProperty(value="接入方式")
    private String accessMode;

    /**
     * 状态 0已上架1已下架
     */
    @TableField(value = "`status`")
    @ApiModelProperty(value="状态 0已上架1已下架")
    private Boolean status;

}