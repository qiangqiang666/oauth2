package com.traffic.authentication.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.traffic.authentication.entity.MemberEntity;
import com.traffic.authentication.entity.WechatInfoEntity;
import com.traffic.authentication.mapper.MemberMapper;
import com.traffic.authentication.service.MemberService;
import com.traffic.authentication.wx.WxMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * 描述:
 * 〈用户业务层〉
 *
 * @author Monkey
 * @create 2020/12/10 15:21
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, MemberEntity> implements MemberService, WxMemberService {

    @Resource
    private MemberMapper memberMapper;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public Long checkExist(String openId) {
        QueryWrapper<MemberEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(WechatInfoEntity.COL.OPEN_ID.getColName(), openId);
        MemberEntity wechatInfoEntity = memberMapper.selectOne(queryWrapper);
        return wechatInfoEntity == null ? null : wechatInfoEntity.getId();
    }

    @Override
    public void save(String openId, String phone, String nickName, String avatar) {
        UpdateWrapper<MemberEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().eq(MemberEntity::getOpenId, openId);

        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setPhone(phone);
        memberEntity.setNickName(nickName);
        memberEntity.setAvatar(avatar);
        memberEntity.setOpenId(openId);
        memberEntity.setPassword(bCryptPasswordEncoder.encode("123456"));
        memberMapper.update(memberEntity, updateWrapper);
    }

    @Override
    public Long inserVx(String openId) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setOpenId(openId);
        memberEntity.setDelFlag(false);
        memberMapper.insert(memberEntity);
        return memberEntity.getId();
    }

    @Override
    public boolean login(Long memberId, HttpServletResponse res) {
//        MemberEntity memberInfo = memberMapper.selectById(memberId);
//        Session session = new Session();
//        session.setUserId(memberInfo.getId());
//        session.setUserName(memberInfo.getNickName());
//        session.setMobile(memberInfo.getPhone());
//        session.setScope(SimpleAuthScope.USER);
//        sessionService.createUserSession(res, session);
        return false;
    }

    @Override
    public MemberEntity queryByUserName(String username) {
        return memberMapper.queryByUserName(username);
    }

    @Override
    public void insert(MemberEntity memberEntity) {
        memberMapper.insert(memberEntity);
    }

    @Override
    public void updateUser(MemberEntity user) {
        memberMapper.updateById(user);
    }
}