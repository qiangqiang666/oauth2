package com.traffic.authentication.wx;


import com.traffic.authentication.wx.dto.WeChatAuthDTO;
import com.traffic.authentication.wx.dto.WeChatInfoResultDTO;
import com.traffic.authentication.wx.dto.WechatInfoParamDTO;

import javax.servlet.http.HttpServletResponse;

/**
 * @Description:
 * @Author: candol
 * @Date: 2020/8/13
 **/
public interface WxAuthService {

    /**
     * 微信授权获取openId
     *
     * @param code 授权码
     * @return
     */
    WeChatAuthDTO miniProGetOpenIdAndRegister(String code, HttpServletResponse response);


    /**
     * 获取微信信息
     *
     * @param paramDTO
     * @return
     */
    WeChatInfoResultDTO miniProgramGetUserInfo(WechatInfoParamDTO paramDTO);

    String getMiniAccessToken();


}
