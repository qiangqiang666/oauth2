package com.traffic.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.traffic.server.entity.DistrictEntity;
import com.traffic.server.entity.MemberEntity;
import com.traffic.server.mapper.DistrictMapper;
import com.traffic.server.service.DistrictService;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * 描述:
 * 〈国省市区〉
 *
 * @author Monkey
 * @create 2020/12/16 11:20
 */
@Service
public class DistrictServiceImpl extends ServiceImpl<DistrictMapper, DistrictEntity> implements DistrictService {


    @Override
    public List<DistrictEntity> getList() {
//        // 用国家id, 查询省份
//        DistrictEntity districtEntity = baseMapper.selectById(1);
//        List<Provinces> provincesList = baseMapper.selectByPid(districtEntity.getId());
//        // 省份查询市
//        for (Provinces provinces : provincesList) {
//            List<Provinces> cityList = baseMapper.selectByPid(provinces.getPid());
//            provinces.setList(cityList);
//            // 在用市 查询 区
//            for (Provinces city : cityList) {
//                List<Provinces> areaList = baseMapper.selectByPid(city.getPid());
//                city.setList(areaList);
//            }
//        }
//        System.out.println(new Gson().toJson(provincesList));


        return baseMapper.selectList(null);
    }
}