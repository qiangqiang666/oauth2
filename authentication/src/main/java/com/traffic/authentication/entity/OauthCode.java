package com.traffic.authentication.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.sql.Blob;

/**
 * <p>
 * 授权码
 * </p>
 *
 * @author Monkey
 * @since 2020-12-09
 */
@Data
@ApiModel(value="OauthCode对象", description="授权码")
public class OauthCode implements Serializable {

    private String code;

    private Blob authentication;


}
