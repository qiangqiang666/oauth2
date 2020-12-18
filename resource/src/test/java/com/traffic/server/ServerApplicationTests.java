package com.traffic.server;

import com.traffic.server.controller.open.MiniPayController;
import com.traffic.server.model.miniPay.param.MiniPayParam;
import com.traffic.server.utils.UUIDUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class ServerApplicationTests {

    @Test
    void contextLoads() {
    }


    @Resource
    private MiniPayController miniPayController;


    /**
     * 银联支付
     */
    @Test
    public void pay(){
        MiniPayParam payParam = new MiniPayParam();
        payParam.setMerOrderId(UUIDUtils.generateOrderSn());
        payParam.setUserId("10001");
        //payParam.setSubOpenId("w3234234");
        payParam.setTotalAmount(100L);
        miniPayController.miniPay(payParam);
    }

}
