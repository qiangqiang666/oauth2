package com.traffic.server.unionPay;

import com.alibaba.fastjson.JSONObject;
import com.traffic.server.unionPay.param.*;

/**
 * @program: server
 * @description
 * @author: belive
 * @create: 2020-12-17 00:26
 **/
public interface UnionPayService {

    /**
     * 微信下单
     * @param param
     * @return
     */
     String consumeApply(String param);
    /**
     * 关闭订单
     * @param param
     * @return
     */
     String depositApply(String param);
    /**
     * 交易结果查询
     * @param param
     * @return
     */
     String getOrderDetail(String param);

    /**
     * 小程序退款
     * @param param
     * @return
     */
     String refund(String param);

    /**
     * 退款查询
     * @param param
     * @return
     */
     String refundQuery(String param);

    /**
     * 交易结果回调
     * @param param
     * @return
     */
     String payResultMessage(String param);

}
