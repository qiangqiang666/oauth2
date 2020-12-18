package com.traffic.authentication.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class BaseEntity {

    @ApiModelProperty(value="主键id")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value="创建时间 ")
    @TableField("create_at")
    private Date createAt;

    @ApiModelProperty(value="修改时间 ")
    @TableField("update_at")
    private Date updateAt;

    @TableField(value = "del_flag")
    @ApiModelProperty(value="0：正常 -1:删除 ")
    private Boolean delFlag;

}
