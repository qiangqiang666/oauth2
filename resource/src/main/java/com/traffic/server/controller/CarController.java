package com.traffic.server.controller;

import com.traffic.server.model.request.user.CarDTO;
import com.traffic.server.service.CarService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * @Description:
 * @Author: candol
 * @Date: 2020/8/17
 **/
@Api(value = "【小程序】绑定车牌接口",tags = "【小程序】")
@Slf4j
@RestController
@RequestMapping("/mini/member/car")
public class CarController {
    @Resource
    private CarService carService;
	
	
	
    @ApiOperation(value = "用户绑定车牌",notes = "POST")
    @PostMapping("add")
    private void add(@RequestBody @ApiParam(required=true) CarDTO carDTO) {
        log.info("用户绑定车牌:{}", carDTO.getCarNo());
        carService.add(carDTO);
    }

    @ApiOperation(value = "用户解绑车牌",notes = "POST")
    @PostMapping("delete")
    private void delete(@RequestBody @ApiParam(required=true) CarDTO carDTO) {
        log.info("用户解绑车牌:{}", carDTO.getCarNo());
        carService.delete(carDTO);
    }


    @ApiOperation(value = "车牌号是否绑定",notes = "GET")
    @GetMapping("isBind")
    private void isBind(@ApiParam(value = "车牌号",required=true) String carNo) {
        carService.isBind(carNo);
    }

    @ApiOperation(value = "用户当前绑定车辆个数",notes = "GET", response = Integer.class)
    @GetMapping("bindCount")
    private Integer bindCount() {
        return carService.bindCount();
    }
    
}
