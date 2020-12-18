package com.traffic.server.controller.common;

import com.alibaba.fastjson.JSONObject;
import com.traffic.server.entity.OrderPayNotEntity;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: server
 * @description
 * @author: belive
 * @create: 2020-12-17 02:54
 **/
@Slf4j
@RestController
@Api(value = "NotifyController", tags = "【后台】银联回调")
@RequestMapping("/common/unionPay/notify/")
public class UnionPayNotifyController {

    @RequestMapping(value = "pay", method = RequestMethod.POST)
    @ResponseBody
    public void payNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, String> params = new HashMap<>();
        Map map = request.getParameterMap();
        String result = JSONObject.toJSONString(map);
        JSONObject object = JSONObject.parseObject(result);

        OrderPayNotEntity orderPayNot = new OrderPayNotEntity();
        orderPayNot.setPayType(1);

    }

}
