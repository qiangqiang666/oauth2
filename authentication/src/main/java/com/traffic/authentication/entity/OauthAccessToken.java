package com.traffic.authentication.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.sql.Blob;

/**
 * <p>
 * 生成的token
 * </p>
 *
 * @author Monkey
 * @since 2020-12-09
 */
@Data
@ApiModel(value="OauthAccessToken对象", description="生成的token")
public class OauthAccessToken implements Serializable {

    private static final long serialVersionUID = 1L;

    private String tokenId;

    private Blob token;

    private String authenticationId;

    private String userName;

    private String clientId;

    private Blob authentication;

    private String refreshToken;


}
