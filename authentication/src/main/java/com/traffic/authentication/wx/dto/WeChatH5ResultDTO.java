package com.traffic.authentication.wx.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author rihong.g
 * @date 2019-07-19 11:53
 * 微信、微博js签名结果
 */
@Data
@AllArgsConstructor
public class WeChatH5ResultDTO {

    /**
     * 微信应用唯一标识
     */
    private String appid;

    /**
     * 随机字符串
     */
    private String nonceStr;
    /**
     * 时间戳
     */
    private String timeStamp;
    /**
     * 签名
     */
    private String signature;
}
