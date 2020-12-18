package com.traffic.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.traffic.server.entity.CarEntity;
import com.traffic.server.enums.DelFlagEnum;
import com.traffic.server.enums.ErrorCodeEnum;
import com.traffic.server.exception.ApiException;
import com.traffic.server.mapper.CarMapper;
import com.traffic.server.model.request.user.CarDTO;
import com.traffic.server.oauth2.utils.SecurityUserHelperUtil;
import com.traffic.server.service.CarService;
import com.traffic.server.utils.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: traffic_server
 * @description
 * @author: lijinhuai
 * @create: 2020/12/9 15:56
 **/
@Service
public class CarServiceImpl extends ServiceImpl<CarMapper, CarEntity> implements CarService {

    @Resource
    CarMapper carMapper;

    @Override
    public Boolean add(CarDTO carDTO) {
        QueryWrapper<CarEntity> query = new QueryWrapper<>();
        query.lambda().eq(CarEntity::getUserId, SecurityUserHelperUtil.getUserId());
        List<CarEntity> carEntities = carMapper.selectList(query);
        if (carEntities.size() >= 3) {
            throw new ApiException(ErrorCodeEnum.CAR2001);
        }
        CarEntity carEntity = BeanUtils.copyProperties(carDTO, CarEntity::new);
        carEntity.setUserId(SecurityUserHelperUtil.getUserId());
        return carMapper.insert(carEntity) == 1;

    }

    @Override
    public void isBind(String carNo) {
        QueryWrapper<CarEntity> query = new QueryWrapper<>();
        query.lambda().eq(CarEntity::getCarNo, carNo).eq(CarEntity::getDelFlag, DelFlagEnum.nomal);
        CarEntity car = carMapper.selectOne(query);
        if (car != null) {
            ApiException.thr(ErrorCodeEnum.CAR2000);
        }
    }

    @Override
    public Integer bindCount() {
        QueryWrapper<CarEntity> query = new QueryWrapper<>();
        query.lambda().eq(CarEntity::getUserId, SecurityUserHelperUtil.getUserId()).eq(CarEntity::getDelFlag, DelFlagEnum.nomal);
        return carMapper.selectCount(query);
    }


    @Override
    public List<CarEntity> memberCar() {
        QueryWrapper<CarEntity> query = new QueryWrapper<>();
        query.lambda().eq(CarEntity::getUserId, SecurityUserHelperUtil.getUserId()).eq(CarEntity::getDelFlag, DelFlagEnum.nomal);
        return carMapper.selectList(query);
    }

    @Override
    public void delete(CarDTO carDTO) {
        QueryWrapper<CarEntity> query = new QueryWrapper<>();
        query.lambda().eq(CarEntity::getUserId, SecurityUserHelperUtil.getUserId())
                .eq(CarEntity::getCarNo, carDTO.getCarNo())
                .eq(CarEntity::getCarType, carDTO.getCarType());
        carMapper.delete(query);
    }

}
