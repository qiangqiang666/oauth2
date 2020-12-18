package com.traffic.authentication.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.sql.Blob;

/**
 * <p>
 *  刷新token
 * </p>
 *
 * @author Monkey
 * @since 2020-12-09
 */
@Data
@ApiModel(value="OauthRefreshToken对象", description=" 刷新token")
public class OauthRefreshToken implements Serializable {

    private String tokenId;

    private Blob token;

    private Blob authentication;


}
