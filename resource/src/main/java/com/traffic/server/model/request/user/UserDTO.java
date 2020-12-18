package com.traffic.server.model.request.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 描述:
 * 〈用户详情〉
 *
 * @author Monkey
 * @create 2020/12/14 14:24
 */
@Data
@ApiModel(value="用户详情")
public class UserDTO {

    /**
     * 用户昵称
     */
    @ApiModelProperty(value="用户昵称")
    private String nickName;

    /**
     * 头像url
     */
    @ApiModelProperty("头像url")
    private String avatar;

    /**
     * 性别 0男1女
     */
    @ApiModelProperty(value="性别 0男1女")
    private Integer sex;

    /**
     * 联系地址
     */
    @ApiModelProperty(value="联系地址")
    private String address;

    /**
     * 所属城市id
     */
    @ApiModelProperty(value="所属城市id")
    private Integer districtId;
}