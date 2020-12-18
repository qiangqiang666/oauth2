package com.traffic.authentication.wx;


import javax.servlet.http.HttpServletResponse;

public interface WxMemberService {

    Long checkExist(String openId);

    boolean login(Long memberId, HttpServletResponse response);

    void save(String openId, String phone, String nickName, String avatar);

    Long inserVx(String openId);
}
