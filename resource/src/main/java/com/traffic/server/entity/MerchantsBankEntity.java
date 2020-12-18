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
    * 商户银联开户信息表
    */
@ApiModel(value="com-traffic-entity-MerchantsBank")
@Data
@TableName(value = "t_merchants_bank")
public class MerchantsBankEntity extends BaseEntity{
    /**
     * 企业类型 0企业1个体工商户
     */
    @TableField(value = "company_type")
    @ApiModelProperty(value="企业类型 0企业1个体工商户")
    private Boolean companyType;

    /**
     * 营业执照
     */
    @TableField(value = "license_img")
    @ApiModelProperty(value="营业执照")
    private String licenseImg;

    @TableField(value = "merchant_name")
    @ApiModelProperty(value="")
    private String merchantName;

    /**
     * 统一社会信用代码
     */
    @TableField(value = "social_code")
    @ApiModelProperty(value="统一社会信用代码")
    private String socialCode;

    /**
     * 地址
     */
    @TableField(value = "area")
    @ApiModelProperty(value="地址")
    private String area;

    /**
     * 详细地址
     */
    @TableField(value = "address")
    @ApiModelProperty(value="详细地址")
    private String address;

    /**
     * 营业期限 
     */
    @TableField(value = "business_term")
    @ApiModelProperty(value="营业期限 ")
    private String businessTerm;

    /**
     * 身份证头像页、国徽页
     */
    @TableField(value = "card_img")
    @ApiModelProperty(value="身份证头像页、国徽页")
    private String cardImg;

    /**
     * 证件类型 0中国大陆居民--身份证
     */
    @TableField(value = "card_type")
    @ApiModelProperty(value="证件类型 0中国大陆居民--身份证")
    private Boolean cardType;

    /**
     * 姓名
     */
    @TableField(value = "`name`")
    @ApiModelProperty(value="姓名")
    private String name;

    /**
     * 证件号码
     */
    @TableField(value = "identity_card")
    @ApiModelProperty(value="证件号码")
    private String identityCard;

    /**
     * 证件有效期
     */
    @TableField(value = "card_term")
    @ApiModelProperty(value="证件有效期")
    private String cardTerm;

    /**
     * 联系人手机号
     */
    @TableField(value = "phone")
    @ApiModelProperty(value="联系人手机号")
    private String phone;

    /**
     * 账户类型 0对公账号1对私账号
     */
    @TableField(value = "account_type")
    @ApiModelProperty(value="账户类型 0对公账号1对私账号")
    private Boolean accountType;

    /**
     * 开户银行
     */
    @TableField(value = "bank_name")
    @ApiModelProperty(value="开户银行")
    private String bankName;

    /**
     * 开户支行
     */
    @TableField(value = "bank_address")
    @ApiModelProperty(value="开户支行")
    private String bankAddress;

    /**
     * 银行账号
     */
    @TableField(value = "account_no")
    @ApiModelProperty(value="银行账号")
    private String accountNo;

    /**
     * 发票抬头
     */
    @TableField(value = "invoice_title")
    @ApiModelProperty(value="发票抬头")
    private String invoiceTitle;

    /**
     * 发票税号
     */
    @TableField(value = "invoice_tax")
    @ApiModelProperty(value="发票税号")
    private String invoiceTax;

}