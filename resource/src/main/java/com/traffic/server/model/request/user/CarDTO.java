package com.traffic.server.model.request.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@ApiModel
@Data
public class CarDTO {
	@NotNull(message="车牌号不能为空")
    @ApiModelProperty(name = "carNo", value = "车牌号", dataType = "String", example = "京12345",required=true)
    private String carNo;
	
	@NotNull(message="车辆类型不能为空")
    @ApiModelProperty(name = "carType", value = "车辆类型 0-普通车 1-新能源", dataType = "Integer", example = "0",required=true)
    private String carType;
}
