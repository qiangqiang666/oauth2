package com.traffic.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.traffic.server.entity.MemberEntity;
import com.traffic.server.mapper.MemberMapper;
import com.traffic.server.service.MemberService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @program: traffic_server
* @description
* @author: lijinhuai
* @create: 2020/12/9 15:56
**/
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, MemberEntity> implements MemberService {

    @Resource
    private MemberMapper memberMapper;

    @Override
    public MemberEntity getMemberByMobile(String mobile) {
        QueryWrapper<MemberEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(MemberEntity::getPhone, mobile);
        return memberMapper.selectOne(queryWrapper);
    }

    @Override
    public MemberEntity selectUserByUserId(Long userId) {
        QueryWrapper<MemberEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(MemberEntity::getUserId, userId);
        return memberMapper.selectOne(queryWrapper);
    }

    @Override
    public void updateUserInfo(MemberEntity memberEntity) {
        //更新的条件
        QueryWrapper<MemberEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", memberEntity.getUserId());
        memberMapper.update(memberEntity,wrapper);
    }


}
