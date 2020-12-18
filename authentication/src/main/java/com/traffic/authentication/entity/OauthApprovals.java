package com.traffic.authentication.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author Monkey
 * @since 2020-12-09
 */
@Data
@ApiModel(value="OauthApprovals对象", description="")
public class OauthApprovals implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("userId")
    private String userId;

    @ApiModelProperty("clientId")
    private String clientId;

    private String scope;

    private String status;

    @ApiModelProperty("expiresAt")
    private Date expiresAt;

    @ApiModelProperty("lastModifiedAt")
    private Date lastModifiedAt;


}
