package com.traffic.authentication.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 客户端信息
 * </p>
 *
 * @author Monkey
 * @since 2020-12-09
 */
@Data
@ApiModel(value="OauthClientDetails对象", description="客户端信息")
public class OauthClientDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    private String clientId;

    private String resourceIds;

    private String clientSecret;

    private String scope;

    private String authorizedGrantTypes;

    private String webServerRedirectUri;

    private String authorities;

    private Integer accessTokenValidity;

    private Integer refreshTokenValidity;

    private String additionalInformation;

    private String autoapprove;


}
