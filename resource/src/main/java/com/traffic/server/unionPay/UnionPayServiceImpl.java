package com.traffic.server.unionPay;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @program: server
 * @description
 * @author: belive
 * @create: 2020-12-17 00:26
 **/
@Service
@Slf4j
public class UnionPayServiceImpl implements UnionPayService {

    /**
     * 小程序微信下单
     */
    @Value("${unionPay.url.min_wx_unified_order}")
    private String min_wx_unified_order;
    /**
     * 小程序支付订单查询
     */
    @Value("${unionPay.url.min_unified_order_query}")
    private String min_unified_order_query;
    /**
     *小程序退款
     */
    @Value("${unionPay.url.min_pay_unified_refund}")
    private String min_pay_unified_refund;
    /**
     *小程序退款查询
     */
    @Value("${unionPay.url.min_pay_unified_refund_query}")
    private String min_pay_unified_refund_query;
    /**
     *小程序未支付订单关闭
     */
    @Value("${unionPay.url.min_pay_unified_order_close}")
    private String min_pay_unified_order_close;
    /**
     *程序支付结果通知 先讯的请求地址
     */
    @Value("${unionPay.url.min_pay_unified_pay_msg}")
    private String min_pay_unified_pay_msg;



    @Resource
    private TransactionHttp request;

    /**
     * 微信下单
     * @param entity
     * @return
     */
    @Override
    public String consumeApply(String entity){
        //转换成JSON格式数据
        String result = request.send(min_wx_unified_order,entity);
        return result;
    }
    /**
     * 关闭订单
     * @param entity
     * @return
     */
    @Override
    public String depositApply(String entity){
        String result = request.send(min_pay_unified_order_close,entity);
        return result;
    }
    /**
     * 交易结果查询
     * @param entity
     * @return
     */
    @Override
    public String getOrderDetail(String entity){
        String result = request.send(min_unified_order_query,entity);
        return result;
    }
    @Override
    public String refund(String entity){
        String result = request.send(min_pay_unified_refund,entity);
        return result;
    }
    @Override
    public String refundQuery(String entity){
        String result = request.send(min_pay_unified_refund_query,entity);
        return result;
    }
    @Override
    public String payResultMessage(String entity){
        String result = request.send(min_unified_order_query,entity);
        return result;
    }

}
