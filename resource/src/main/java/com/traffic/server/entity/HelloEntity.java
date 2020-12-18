package com.traffic.server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
@TableName("t_hello")
public class HelloEntity {
	
    @ApiModelProperty("主键id")
    @TableId(type = IdType.AUTO)
    private Long id;

}