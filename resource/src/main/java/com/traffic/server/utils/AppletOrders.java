package com.traffic.server.utils;

import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


/**
 * @program: openPlatform
 * @description: 小程序下单
 */
public class AppletOrders {
    static String appId = "10037e6f6823b20801682b6a5e5a0006";
    static String appKey = "1c4e3b16066244ae9b236a09e5b312e8";
    static String authorization;
    static String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    static String nonce = UUID.randomUUID().toString().replace("-", "");

    public static void main(String[] args) throws Exception {
        orders();
    }

    /**
     * 下单
     */
    public static void orders() throws Exception {
        /* post参数,格式:JSON */
        JSONObject json = new JSONObject();
        json.put("requestTimestamp", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));    // 报文请求时间
        json.put("merOrderId", getMerchantOrderId()); // 商户订单号
        json.put("mid", "898201612345678"); // 商户号
        json.put("tid", "88880001");    // 终端号
        json.put("instMid", "MINIDEFAULT"); // 业务类型
        json.put("totalAmount", 1);      // 支付总金额
        json.put("subOpenId", "oah6x4l_tATwUL04AHw5ScpKV_Z8");      // 支付总金额
        json.put("subAppId", "8a81c1bd744ea7580174c2f5a436007e");      // 支付总金额
        json.put("notifyUrl", "https://www.sina.com.cn");  // 支付结果通知地址
        json.put("returnUrl", "http://www.wfdsj.cn/cn/index.htm");  // 网页跳转地址
        json.put("tradeType", "MINI");  // 交易类型

        /* 分账部分 */
//        json.put("divisionFlag", true); // 分账标记
//        json.put("platformAmount", 0); // 平台商户分账金额
//        ArrayList<JSON> subOrders = new ArrayList<>();
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("mid", "988460101800204");
//        jsonObject.put("totalAmount", 1);
//        subOrders.add(jsonObject);
//        json.put("subOrders", subOrders);

        System.out.println("请求报文json:\n" + json.toString());
        String url = "http://58.247.0.18:29015/v1/netpay/wx/unified-order";
        //OPEN-BODY-SIG 方式
        authorization = getOpenBodySig(appId, appKey, json.toString());
        //token 方式
        //authorization = getToken();

        String send = send(url, json.toString() , authorization);
        System.out.println("请求报文json:\n" + send);
    }

    /**
     * 发送请求
     * @param url    eg:http://58.247.0.18:29015/v1/netpay/trade/create
     * @return
     * @throws Exception
     */
    public static String send(String url, String entity , String authorization) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Authorization", authorization);
        StringEntity se = new StringEntity(entity, "UTF-8");
        se.setContentType("application/json");
        httpPost.setEntity(se);
        CloseableHttpResponse response = httpClient.execute(httpPost);
        HttpEntity entity1 = response.getEntity();
        String resStr = null;
        if (entity1 != null) {
            resStr = EntityUtils.toString(entity1, "UTF-8");
        }
        httpClient.close();
        response.close();
        return resStr;
    }

    /**
     * open-body-sig方式获取到Authorization 的值
     *
     * @param appId  f0ec96ad2c3848b5b810e7aadf369e2f
     * @param appKey 775481e2556e4564985f5439a5e6a277
     * @param body   json字符串 String body = "{\"merchantCode\":\"123456789900081\",\"terminalCode\":\"00810001\",\"merchantOrderId\":\"20123333644493200\",\"transactionAmount\":\"1\",\"merchantRemark\":\"测试\",\"payMode\":\"CODE_SCAN\",\"payCode\":\"285668667587422761\",\"transactionCurrencyCode\":\"156\"}";
     * @return
     * @throws Exception
     */
    public static String getOpenBodySig(String appId, String appKey, String body) throws Exception {
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());   // eg:20190227113148
        String nonce = UUID.randomUUID().toString().replace("-", ""); // eg:be46cd581c9f46ecbd71b9858311ea12
        byte[] data = body.getBytes("UTF-8");
        System.out.println("data:\n" + body);
        InputStream is = new ByteArrayInputStream(data);
        String bodyDigest = DigestUtils.sha256Hex(is); // eg:d60bc3aedeb853e2a11c0c096baaf19954dd9b752e48dea8e919e5fb29a42a8d
        System.out.println("bodyDigest:\n" + bodyDigest);
        String str1_C = appId + timestamp + nonce + bodyDigest; // eg:f0ec96ad2c3848b5b810e7aadf369e2f + 20190227113148 + be46cd581c9f46ecbd71b9858311ea12 + d60bc3aedeb853e2a11c0c096baaf19954dd9b752e48dea8e919e5fb29a42a8d

        System.out.println("str1_C:" + str1_C);
        byte[] localSignature = hmacSHA256(str1_C.getBytes(), appKey.getBytes());

        String localSignatureStr = Base64.encodeBase64String(localSignature);   // Signature
        System.out.println("Authorization:\n" + "OPEN-BODY-SIG AppId=" + "\"" + appId + "\"" + ", Timestamp=" + "\"" + timestamp + "\"" + ", Nonce=" + "\"" + nonce + "\"" + ", Signature=" + "\"" + localSignatureStr + "\"\n");
        return ("OPEN-BODY-SIG AppId=" + "\"" + appId + "\"" + ", Timestamp=" + "\"" + timestamp + "\"" + ", Nonce=" + "\"" + nonce + "\"" + ", Signature=" + "\"" + localSignatureStr + "\"");
    }



    public static String getToken() {
        JSONObject jsonObject = new JSONObject();
        String all = appId + timestamp + nonce + appKey;
        String signature = DigestUtils.sha256Hex(getMessageBytes(all));
        jsonObject.put("appId", appId);
        jsonObject.put("timestamp", timestamp);
        jsonObject.put("nonce", nonce);
        jsonObject.put("signature", signature);
        jsonObject.put("signMethod","SHA256");
        JSONObject response = doPost("http://58.247.0.18:29015/v1/token/access", jsonObject);
        //JSONObject response = doPost("http://api-mop.chinaums.com/v1/token/access", jsonObject);
        String accessToken = response.get("accessToken").toString();
        return "OPEN-ACCESS-TOKEN AccessToken="+accessToken+"";
    }


    /**
     * @param data
     * @param key
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    public static byte[] hmacSHA256(byte[] data, byte[] key) throws NoSuchAlgorithmException, InvalidKeyException {
        String algorithm = "HmacSHA256";
        Mac mac = Mac.getInstance(algorithm);
        mac.init(new SecretKeySpec(key, algorithm));
        return mac.doFinal(data);
    }

    /**
     * 获取到订单号
     *
     * @return 订单号
     */
    public static String getMerchantOrderId() {
        String id = DateFormatUtils.format(new Date(), "yyyyMMddHHmmssSSS") + RandomStringUtils.randomNumeric(7);
        return id;
    }




    public static JSONObject doPost(String url, JSONObject json) {

        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        JSONObject response = null;
        try {
            StringEntity s = new StringEntity(json.toString());
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json");//发送json数据需要设置contentType
            post.setEntity(s);
            CloseableHttpResponse res = httpclient.execute(post);
            HttpEntity entity = res.getEntity();
            String result = EntityUtils.toString(entity);// 返回json格式：
            response = JSONObject.fromObject(result);
            res.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return response;
    }

    public static byte[] getMessageBytes(String message) {
        try {
            return message.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("签名过程中出现错误");
        }
    }

}
