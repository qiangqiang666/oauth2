package com.traffic.server.service;

import com.traffic.server.entity.MemberEntity;

/**
 * @author Monkey
 */
public interface MemberService {


    /**
     * 验证码缓存前缀
     */
    String codeKey = "user:code:%s";
    /**
     * 验证码有效期
     */
    Integer codeExpireIn = 5 * 60 * 60;


    MemberEntity getMemberByMobile(String mobile);

    /**
     * 根据id查询用户
     * @param userId
     * @return
     */
    MemberEntity selectUserByUserId(Long userId);

    /**
     * 修改用户信息
     * @param memberEntity
     */
    void updateUserInfo(MemberEntity memberEntity);
}
