package com.traffic.authentication.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.sql.Blob;

/**
 * <p>
 * 
 * </p>
 *
 * @author Monkey
 * @since 2020-12-09
 */
@Data
@ApiModel(value="OauthClientToken对象", description="")
public class OauthClientToken implements Serializable {

    private String tokenId;

    private Blob token;

    private String authenticationId;

    private String userName;

    private String clientId;


}
