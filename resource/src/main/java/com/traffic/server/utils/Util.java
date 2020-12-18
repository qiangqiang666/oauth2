package com.traffic.server.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * @program: publicpaydemo
 * @description: 工具类
 * @author: Mr.YS
 * @CreatDate: 2019/5/17/017 13:24
 */
public class Util {

    /**
     * 获取到订单号
     *
     * @param msgSrcId 消息来源编号  1017
     * @return 商户订单号    {来源编号(4位)}{时间(yyyyMMddmmHHssSSS)(17位)}{7位随机数}
     */
    public static String getMerOrderId(String msgSrcId) {
        return msgSrcId + DateFormatUtils.format(new Date(), "yyyyMMddHHmmssSSS") + RandomStringUtils.randomNumeric(7);
    }

    /**
     * 获取到退货订单号refundOrderId
     *
     * @param msgSrcId 消息来源编号  1017
     * @return 商户订单号    {来源编号(4位)}{时间(yyyyMMddmmHHssSSS)(17位)}{7位随机数}
     */
    public static String getRefundOrderId(String msgSrcId) {
        return msgSrcId + DateFormatUtils.format(new Date(), "yyyyMMddHHmmssSSS") + RandomStringUtils.randomNumeric(7);
    }



    // 获取HttpServletRequest里面的参数
    public static Map<String, String> getRequestParams(HttpServletRequest request) {
        Map<String, String[]> params = request.getParameterMap();
        Map<String, String> params2 = new HashMap<>();
        for (String key : params.keySet()) {
            String[] values = params.get(key);
            if (values.length > 0) {
                params2.put(key, request.getParameter(key));
            }
        }
        return params2;
    }


    public static String makeSign(String md5Key, Map<String, String> params) {

        String preStr = buildSignString(params); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        String text = preStr + md5Key;
        System.out.println("待签名字符串："+text);
        return DigestUtils.md5Hex(getContentBytes(text)).toUpperCase();
        //return DigestUtils.sha256Hex(getContentBytes(text)).toLowerCase();

    }

    // 构建签名字符串
    public static String buildSignString(Map<String, String> params) {

        // params.put("Zm","test_test");

        if (params == null || params.size() == 0) {
            return "";
        }

        List<String> keys = new ArrayList<>(params.size());

        for (String key : params.keySet()) {
            if ("sign".equals(key))
                continue;
            if (StringUtils.isEmpty(params.get(key)))
                continue;
            keys.add(key);
        }
        //System.out.println(listToString(keys));

        Collections.sort(keys);

        StringBuilder buf = new StringBuilder();

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);

            if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
                buf.append(key + "=" + value);
            } else {
                buf.append(key + "=" + value + "&");
            }
        }

        return buf.toString();
    }

    // 根据编码类型获得签名内容byte[]
    public static byte[] getContentBytes(String content) {
        try {
            return content.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("签名过程中出现错误");
        }
    }

}
