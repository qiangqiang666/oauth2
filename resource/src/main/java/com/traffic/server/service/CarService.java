package com.traffic.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.traffic.server.entity.CarEntity;
import com.traffic.server.model.request.user.CarDTO;

import java.util.List;

/**
* @program: traffic_server
* @description
* @author: lijinhuai
* @create: 2020/12/9 15:56
**/
public interface CarService extends IService<CarEntity>{

    Boolean add(CarDTO carDTO);

    void isBind(String carNo);

    Integer bindCount();

    List<CarEntity> memberCar();

    void delete(CarDTO carDTO);
}
