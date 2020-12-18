package com.traffic.authentication.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 描述:
 * 〈用户注册〉
 *
 * @author Monkey
 * @create 2020/12/10 15:24
 */
@Data
@ApiModel(value="用户注册")
public class RegisterDTO {

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "密码")
    private String password;
}