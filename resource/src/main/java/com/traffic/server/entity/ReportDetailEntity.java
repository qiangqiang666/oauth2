package com.traffic.server.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;


/**
* @program: traffic_server
* @description
* @author: lijinhuai
* @create: 2020/12/9 15:56
**/

/**
    * 举报信息详情表
    */
@ApiModel(value="com-traffic-entity-ReportDetail")
@Data
@TableName(value = "t_report_detail")
public class ReportDetailEntity extends BaseEntity {


}