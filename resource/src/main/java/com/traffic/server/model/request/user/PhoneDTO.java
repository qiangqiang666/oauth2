package com.traffic.server.model.request.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 描述:
 * 〈手机参数类〉
 *
 * @author Monkey
 * @create 2020/12/14 14:58
 */
@Data
@ApiModel(value="手机参数类")
public class PhoneDTO {

    @ApiModelProperty(value="手机号",required = true)
    @NotBlank(message = "手机号不能为空")
    private String phone;

    @ApiModelProperty(value="短信类别",required = true)
    @NotNull(message = "短信类别不能为空")
    private Integer type;

    @ApiModelProperty(value="验证码",required = true)
    @NotBlank(message = "验证码不能为空")
    private String code;
}