package com.traffic.authentication.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Monkey
 * @since 2020-12-09
 */
@Data
@ApiModel(value="Clientdetails对象", description="")
public class Clientdetails implements Serializable {

    @ApiModelProperty("appId")
    private String appId;

    @ApiModelProperty("resourceIds")
    private String resourceIds;

    @ApiModelProperty("appSecret")
    private String appSecret;

    private String scope;

    @ApiModelProperty("grantTypes")
    private String grantTypes;

    @ApiModelProperty("redirectUrl")
    private String redirectUrl;

    private String authorities;

    private Integer accessTokenValidity;

    private Integer refreshTokenValidity;

    @ApiModelProperty("additionalInformation")
    private String additionalInformation;

    @ApiModelProperty("autoApproveScopes")
    private String autoApproveScopes;


}
