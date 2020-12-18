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
* @create: 2020/12/9 15:56
**/

/**
    * 举报表
    */
@ApiModel(value="com-traffic-entity-Report")
@Data
@TableName(value = "t_report")
public class ReportEntity extends BaseEntity {

    /**
     * 编号
     */
    @TableField(value = "report_no")
    @ApiModelProperty(value="编号")
    private Long reportNo;

    /**
     * 举报人名称
     */
    @TableField(value = "reporter_name")
    @ApiModelProperty(value="举报人名称")
    private String reporterName;

    /**
     * 举报内容
     */
    @TableField(value = "report_content")
    @ApiModelProperty(value="举报内容")
    private String reportContent;

    /**
     * 手机号
     */
    @TableField(value = "phone")
    @ApiModelProperty(value="手机号")
    private String phone;

    /**
     * 举报状态 0未处理1已处理
     */
    @TableField(value = "`status`")
    @ApiModelProperty(value="举报状态 0未处理1已处理")
    private Boolean status;

    /**
     * 处理人
     */
    @TableField(value = "`operator`")
    @ApiModelProperty(value="处理人")
    private String operator;

    /**
     * 处理结果 0恶意举报1有效举报2无效举报
     */
    @TableField(value = "operate_result")
    @ApiModelProperty(value="处理结果 0恶意举报1有效举报2无效举报")
    private Boolean operateResult;

    /**
     * 处理时间
     */
    @TableField(value = "operate_time")
    @ApiModelProperty(value="处理时间")
    private Date operateTime;

    @TableField(value = "operate_note")
    @ApiModelProperty(value="")
    private String operateNote;

}