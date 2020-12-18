package com.traffic.authentication.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.traffic.authentication.entity.MemberEntity;
import org.apache.ibatis.annotations.Param;

/**
* @program: traffic_server
* @description
* @author: lijinhuai
* @create: 2020/12/9 15:56
**/
public interface MemberMapper extends BaseMapper<MemberEntity> {
    MemberEntity queryByUserName(@Param("username") String username);
}