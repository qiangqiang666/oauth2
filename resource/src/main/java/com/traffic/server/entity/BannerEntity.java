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
    * banner表
    */
@ApiModel(value="com-traffic-entity-Banner")
@Data
@TableName(value = "t_banner")
public class BannerEntity extends BaseEntity {
    /**
     * 编号
     */
    @TableField(value = "banner_no")
    @ApiModelProperty(value="编号")
    private Long bannerNo;

    /**
     * banner名称
     */
    @TableField(value = "banner_name")
    @ApiModelProperty(value="banner名称")
    private String bannerName;

    /**
     * banner位置：0首页、1分类导航栏
     */
    @TableField(value = "layout")
    @ApiModelProperty(value="banner位置：0首页、1分类导航栏")
    private Boolean layout;

    /**
     * 开始时间
     */
    @TableField(value = "start_time")
    @ApiModelProperty(value="开始时间")
    private Date startTime;

    /**
     *  到期时间
     */
    @TableField(value = "end_time")
    @ApiModelProperty(value=" 到期时间")
    private Date endTime;

    /**
     * banner图片
     */
    @TableField(value = "banner_img")
    @ApiModelProperty(value="banner图片")
    private String bannerImg;

    /**
     * 链接
     */
    @TableField(value = "url")
    @ApiModelProperty(value="链接")
    private String url;

    /**
     * 备注
     */
    @TableField(value = "remark")
    @ApiModelProperty(value="备注")
    private String remark;

    /**
     * 状态 0上线1下线
     */
    @TableField(value = "`status`")
    @ApiModelProperty(value="状态 0上线1下线")
    private Boolean status;
}