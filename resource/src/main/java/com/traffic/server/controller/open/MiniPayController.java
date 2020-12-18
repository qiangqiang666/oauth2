package com.traffic.server.controller.open;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.traffic.server.entity.OrderPayNotEntity;
import com.traffic.server.model.miniPay.param.MiniPayParam;
import com.traffic.server.model.miniPay.param.MiniRefundParam;
import com.traffic.server.model.miniPay.res.MiniPayRes;
import com.traffic.server.model.miniPay.res.MiniRefundRes;
import com.traffic.server.service.IOrderPayNotService;
import com.traffic.server.unionPay.UnionPayService;
import com.traffic.server.unionPay.param.ConsumeApplyParam;
import com.traffic.server.unionPay.param.GoodsParam;
import com.traffic.server.utils.DateUtil;
import com.traffic.server.utils.UUIDUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


/**
 * @program: server
 * @description 微信小程序支付
 * @author: belive
 * @create: 2020-12-11 11:03
 **/
@Slf4j
@RestController
@Api(value = "【对外APi】微信小程序支付",tags = "【对外APi】")
@RequestMapping(value = "/open/")
public class MiniPayController {
    /**
     * 微信商户秘钥
     */
    @Value("${wx.mini.secret}")
    private String secret;
    /**
     * 微信商户号
     */
    @Value("${wx.mini.appid}")
    private String appid;
    /**
     * 商户号
     */
    @Value("${unionPay.u_mid}")
    private String u_mid;
    /**
     * 终端号
     */
    @Value("${unionPay.u_tid}")
    private String u_tid;


    @Resource
    private UnionPayService unionPayService;
    @Resource
    private IOrderPayNotService orderPayNotService;

    @ApiOperation(httpMethod = "POST", value = "微信小程序支付", response = MiniPayRes.class)
    @RequestMapping(value = "/miniPay", method = RequestMethod.POST)
    public MiniPayRes miniPay(@RequestBody @Validated MiniPayParam param) {
        JSONObject body = new JSONObject();
        List<GoodsParam> goodsInfo = param.getGoodsParamList();
        if(!ObjectUtils.isEmpty(goodsInfo)){
            body.put("goods",JSON.parseArray(JSONObject.toJSONString(goodsInfo)));
        }
        body.put("requestTimestamp", DateUtil.Now());
        body.put("merOrderId",param.getMerOrderId());
        body.put("mid",u_mid);
        body.put("tid",u_tid);
        body.put("subAppId","wx446fe8fe003c3d38");
        body.put("subOpenId",param.getSubOpenId());
        body.put("userId",param.getUserId());
        if(StringUtils.isNotBlank(param.getProductId())){
            body.put("productId",param.getProductId());
        }
        if(StringUtils.isNotBlank(param.getExpireTime())){
            body.put("expireTime",param.getExpireTime());
        }
        if(StringUtils.isNotBlank(param.getOrderDesc())){
            body.put("orderDesc",param.getOrderDesc());
        }
        body.put("tradeType","MINI");
        //todo 回调地址
        body.put("notifyUrl","http://192.168.1.1:8080/traffic_resource/common/unionPay/notify/pay");
        body.put("totalAmount",param.getTotalAmount());
        body.put("divisionFlag",false);
        String result = unionPayService.consumeApply(body.toJSONString());
        log.info(result);
        MiniPayRes resultObj = JSONObject.parseObject(result,MiniPayRes.class);
        String code = resultObj.getErrCode();

        //支付流水存储 回调修改
        OrderPayNotEntity opne = new OrderPayNotEntity();
        opne.setBizOrderNo(UUIDUtils.generateOrderSn());
        opne.setCreateTime(new Date());
        opne.setOrderNo(param.getMerOrderId());
        opne.setPayType(1);
        opne.setStatus(0);
        opne.setTotalAmount(param.getTotalAmount());
        opne.setUserId(Long.valueOf(param.getUserId()));
        opne.setCode(code);
        opne.setPayCode(resultObj.getSeqId());
        if(!code.equals("SUCCESS")){
            opne.setInfo(resultObj.getErrMsg());
        }
        orderPayNotService.save(opne);
        return resultObj;

    }

    @ApiOperation(httpMethod = "POST", value = "微信小程序退款", response = MiniRefundRes.class)
    @RequestMapping(value = "/miniRefund", method = RequestMethod.POST)
    public MiniRefundRes miniRefund(MiniRefundParam param){
        JSONObject body = new JSONObject();
        String targetOrderId = param.getTargetOrderId();
        Long userId = param.getUserId();
        body.put("requestTimestamp", DateUtil.Now());
        body.put("targetOrderId",targetOrderId);
        body.put("mid",u_mid);
        body.put("tid",u_tid);
        body.put("instMid","MINIDEFAULT");
        String result = unionPayService.refund(body.toJSONString());
        MiniRefundRes resultObj = JSONObject.parseObject(result,MiniRefundRes.class);
        //本地数据处理
        return resultObj;
    }




}
