package com.traffic.server.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
* @program: traffic_server
* @description
* @author: lijinhuai
* @create: 2020/12/9 15:55
**/
@Data
@TableName(value = "t_car")
public class CarEntity extends BaseEntity {

    @ApiModelProperty(value="用户id")
    private Long userId;

    @ApiModelProperty(value="车辆图url")
    private String imageUrl;

    @ApiModelProperty(value="编号")
    private String carNo;
    
    @ApiModelProperty(value="车辆类型(0-普通,1-新能源)")
    private Integer carType;

}