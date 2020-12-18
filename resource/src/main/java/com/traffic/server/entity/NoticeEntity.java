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
    * 消息通知表
    */
@ApiModel(value="com-traffic-entity-Notice")
@Data
@TableName(value = "t_notice")
public class NoticeEntity extends BaseEntity {

    /**
     * 编号
     */
    @TableField(value = "notice_id")
    @ApiModelProperty(value="编号")
    private Long noticeId;

    /**
     * 消息标题
     */
    @TableField(value = "notice_title")
    @ApiModelProperty(value="消息标题")
    private String noticeTitle;

    /**
     * 发布人员
     */
    @TableField(value = "publisher")
    @ApiModelProperty(value="发布人员")
    private String publisher;

}