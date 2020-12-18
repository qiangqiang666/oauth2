package com.traffic.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.traffic.server.entity.DistrictEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Monkey
 */
public interface DistrictMapper extends BaseMapper<DistrictEntity> {

    //List<Provinces> selectByPid(@Param("pid") Integer pid);
}
