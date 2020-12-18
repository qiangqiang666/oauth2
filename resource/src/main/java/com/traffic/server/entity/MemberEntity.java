package com.traffic.server.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @program: traffic_server
 * @description
 * @author: lijinhuai
 * @create: 2020/12/9 15:56
 **/

/**
 * 用户信息表
 */
@ApiModel(value="com-traffic-entity-Member")
@Data
@TableName(value = "t_member")
public class MemberEntity extends BaseEntity {

    /**
     * 用户ID
     */
    @TableField(value = "user_id")
    @ApiModelProperty(value="用户ID")
    private Long userId;

    @ApiModelProperty(value="用户昵称")
    private String nickName;

    @ApiModelProperty("头像url")
    private String avatar;

    @TableField(value = "user_img")
    @ApiModelProperty(value="用户头像")
    private String userImg;

    @ApiModelProperty("微信open_id")
    private String openId;

    @TableField(value = "phone")
    @ApiModelProperty(value="手机号")
    private String phone;

    @TableField(value = "birthday")
    @ApiModelProperty(value="生日")
    private Date birthday;

    @TableField(value = "sex")
    @ApiModelProperty(value="性别 0男1女")
    private Boolean sex;

    @TableField(value = "user_name")
    @ApiModelProperty(value="真实姓名")
    private String userName;

    @TableField(value = "identity_card")
    @ApiModelProperty(value="身份证")
    private String identityCard;

    @TableField(value = "address")
    @ApiModelProperty(value="联系地址")
    private String address;

    @TableField(value = "`source`")
    @ApiModelProperty(value="注册来源 0小程序")
    private Integer source;

    @TableField(value = "`district_id`")
    @ApiModelProperty(value="所属城市id")
    private Integer districtId;

    @TableField(value = "`password`")
    @ApiModelProperty(value="登录密码")
    private String password;

}