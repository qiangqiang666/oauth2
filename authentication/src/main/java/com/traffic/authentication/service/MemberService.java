package com.traffic.authentication.service;

import com.traffic.authentication.entity.MemberEntity;

/**
 * @author Monkey
 */
public interface MemberService {
    MemberEntity queryByUserName(String username);

    void insert(MemberEntity memberEntity);

    void updateUser(MemberEntity user);
}
