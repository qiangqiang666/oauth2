package com.traffic.authentication.wx;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.traffic.authentication.enums.ErrorEnum;
import com.traffic.authentication.exception.ApiException;
import com.traffic.authentication.redis.BaseCacheClient;
import com.traffic.authentication.util.DecodePhone;
import com.traffic.authentication.util.HttpClientUtil;
import com.traffic.authentication.wx.dto.WeChatAuthDTO;
import com.traffic.authentication.wx.dto.WeChatInfoResultDTO;
import com.traffic.authentication.wx.dto.WechatInfoParamDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @Description:
 * @Author: candol
 * @Date: 2020/8/17
 **/
@Slf4j
@Service
public class WxAuthServiceImpl implements WxAuthService {

    /**
     * 微信小程序授权信息
     */
    @Value("${wx.mini.appid}")
    private String miniWeChatAppId;
    @Value("${wx.mini.secret}")
    private String miniWeChatSecret;

    @Resource
    private BaseCacheClient baseCacheClient;

    @Resource
    private WxMemberService wxMemberService;

    /**
     * 缓存用户微信授权的数据
     */
    private String WECHAT_AUTH_DATA_KEY = "user:wechat:auth:data";

    /**
     * token 缓存时间为 30分钟
     */
    private static final Integer EXPIRE = 1800;

    private static final java.util.Random RANDOM = new java.util.Random();
    private static final String RANDOM_STR = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";


    @Override
    public WeChatAuthDTO miniProGetOpenIdAndRegister(String code, HttpServletResponse response) {
        StringBuffer authUrl = new StringBuffer();

        authUrl.append(WechatAuthConstants.MINI_AUTH_CHECK_URL).append(miniWeChatAppId)
                .append("&secret=").append(miniWeChatSecret).append("&js_code=").append(code).append("&grant_type=authorization_code");

        String result = null;
        try {
            result = HttpClientUtil.get(authUrl.toString(), null);
        } catch (Exception e) {
            log.error("获取用户openid异常", e);
            ApiException.thr(ErrorEnum.MINI_FAILED_TO_GET_OPEN_ID);
        }

        //2、解析授权结果，拿到access_token和openid
        JSONObject jsonObject = JSON.parseObject(result);
        System.out.println("jsonObject========授权结果============" + jsonObject.toJSONString() + "==========================================");
        if (!ObjectUtils.isEmpty(jsonObject)) {
            String openId = jsonObject.getString(WechatAuthConstants.OPENID);

            if (Objects.isNull(openId)) {
                throw new ApiException(ErrorEnum.MINI_FAILED_TO_GET_OPEN_ID.code(), jsonObject.getString("errmsg"));
            }

            // 判断是否存在于数据库,不存在则插入一条记录
            Long memberId = wxMemberService.checkExist(openId);
            if (null == memberId){
                memberId =  wxMemberService.inserVx(openId);
            }
            wxMemberService.login(memberId, response);
            baseCacheClient.innerPut(WECHAT_AUTH_DATA_KEY + openId, jsonObject);
            System.out.println("jsonObject========授权结果============" + baseCacheClient.innerGet(WECHAT_AUTH_DATA_KEY + openId, JSONObject.class) + "==========================================");

            return WeChatAuthDTO.builder()
                    .openId(openId)
                    .memberId(memberId)
                    .build();
        }
        return null;
    }


    /**
     * 获取微信基本信息
     *
     * @param paramDTO
     * @return
     */
    public WeChatInfoResultDTO miniProgramGetUserInfo(WechatInfoParamDTO paramDTO) {
        WeChatInfoResultDTO resultDTO = new WeChatInfoResultDTO();
        JSONObject jsonObject = baseCacheClient.innerGet(WECHAT_AUTH_DATA_KEY + paramDTO.getOpenId(), JSONObject.class);
        if (ObjectUtils.isEmpty(jsonObject)) {
            ApiException.thr(ErrorEnum.MINI_LOGIN_TIMEOUT);
        }
        String sessionKey = jsonObject.getString("session_key");

        String encryptedData = paramDTO.getEncryptedData();//包括敏感数据在内的完整用户信息的加密数据
        String iv = paramDTO.getIv();//加密算法的初始向量
        String result = DecodePhone.wxDecrypt(encryptedData, sessionKey, iv);
        JSONObject phoneStr = JSON.parseObject(result);
        log.info("小程序获取用户微信基本信息{}", phoneStr);
        String phone = phoneStr.getString("phoneNumber");
        String nickName = phoneStr.getString("nickName");
        String headImg = phoneStr.getString("avatarUrl");
        resultDTO.setPhone(phone);
        resultDTO.setNickname(nickName);
        resultDTO.setHeadImg(headImg);
        wxMemberService.save(paramDTO.getOpenId(), phone, nickName, headImg);
        return resultDTO;
    }

    public String getMiniAccessToken() {

        String token = baseCacheClient.simpleGet(MiniMsgConstants.MINI_ACCESS_TOKEN_REDIS_KEY);

        if (StringUtils.isBlank(token)) {
            token = updateAccessTokenFromWx();
        }
        return token;
    }


    private String updateAccessTokenFromWx() {
        String url = MiniMsgConstants.MINI_ACCESS_TOKEN_URL;

        url = String.format(url, miniWeChatAppId, miniWeChatSecret);

        String accessToken = null;
        try {
            log.info("重刷小程序accessToken开始");
            String result = HttpClientUtil.get(url, null);
            JSONObject json = JSONObject.parseObject(result);
            accessToken = json.getString("access_token");
            Integer expiresIn = json.getInteger("expires_in");
            baseCacheClient.simplePut(MiniMsgConstants.MINI_ACCESS_TOKEN_REDIS_KEY, accessToken, expiresIn - 600);
            log.info("重刷小程序accessToken结束");
        } catch (Exception e) {
            log.error("query accessToken error: " + e);
        }
        return accessToken;
    }


}
