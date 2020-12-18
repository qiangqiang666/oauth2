package com.traffic.authentication.model;


import com.traffic.authentication.entity.MemberEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * 描述:
 * 〈登录实体〉
 *
 * @author Monkey
 * @create 2020/7/8 10:48
 */
public class LoginModel implements UserDetails {

    /**
     * 用户实体
     */
    private MemberEntity memberEntity;

    /**
     * 权限集合
     */
    private Collection<GrantedAuthority> roles;

    public LoginModel(MemberEntity memberEntity, Collection<GrantedAuthority> roles) {
        this.memberEntity = memberEntity;
        this.roles = roles;
    }

    public MemberEntity getMemberEntity() {
        return memberEntity;
    }

    public void setMemberEntity(MemberEntity memberEntity) {
        this.memberEntity = memberEntity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public String getPassword() {
        return this.memberEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return this.memberEntity.getPhone();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Collection<GrantedAuthority> getRoles() {
        return roles;
    }

    public void setRoles(Collection<GrantedAuthority> roles) {
        this.roles = roles;
    }
}