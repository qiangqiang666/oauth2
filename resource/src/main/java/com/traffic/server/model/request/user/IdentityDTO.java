package com.traffic.server.model.request.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 描述:
 * 〈身份证信息〉
 *
 * @author Monkey
 * @create 2020/12/14 15:10
 */
@Data
@ApiModel(value="身份证信息")
public class IdentityDTO {


    @ApiModelProperty(value="真实姓名",required = true)
    @NotBlank(message = "真实姓名不能为空")
    private String userName;

    @ApiModelProperty(value="身份证",required = true)
    @NotNull(message = "身份证不能为空")
    private String identityCard;
}