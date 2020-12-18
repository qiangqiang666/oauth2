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
    * 商户申请记录表
    */
@ApiModel(value="com-traffic-entity-MerchantsDemand")
@Data
@TableName(value = "t_merchants_demand")
public class MerchantsDemandEntity extends BaseEntity{
    /**
     * 申请编号
     */
    @TableField(value = "demand_no")
    @ApiModelProperty(value="申请编号")
    private String demandNo;

    /**
     * 公司名称
     */
    @TableField(value = "company_name")
    @ApiModelProperty(value="公司名称")
    private String companyName;

    /**
     * 联系人
     */
    @TableField(value = "`user`")
    @ApiModelProperty(value="联系人")
    private String user;

    /**
     * 联系人手机
     */
    @TableField(value = "phone")
    @ApiModelProperty(value="联系人手机")
    private String phone;

}