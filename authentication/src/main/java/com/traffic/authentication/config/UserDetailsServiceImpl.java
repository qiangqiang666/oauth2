package com.traffic.authentication.config;

import com.traffic.authentication.entity.MemberEntity;
import com.traffic.authentication.enums.ErrorCodeEnum;
import com.traffic.authentication.exception.ApiException;
import com.traffic.authentication.model.LoginModel;
import com.traffic.authentication.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private MemberService memberService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
 
    @Override
    public LoginModel loadUserByUsername(String username) {
        MemberEntity user = memberService.queryByUserName(username);
        if(user == null){
            user = new MemberEntity();
            user.setPhone(username);
            user.setPassword(bCryptPasswordEncoder.encode("123456"));
            user.setDelFlag(false);
            memberService.insert(user);
            user.setUserId(user.getId());
            memberService.updateUser(user);
        }
        if (user.getDelFlag()){
            throw new ApiException(ErrorCodeEnum.U9);
        }
        //返回认证用户
        return new LoginModel(user,null);
    }
}