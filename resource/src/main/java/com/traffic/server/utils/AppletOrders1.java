package com.traffic.server.utils;

import net.sf.json.JSON;
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
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * @program: openPlatform
 * @description: 小程序下单
 * @author: Mr.YS
 * @CreatDate: 2019/10/21/021 9:53
 */
public class AppletOrders1 {
    /*
     * 微信：
     *    测试环境：http://58.247.0.18:29015/v1/netpay/wx/unified-order
     *    生产环境：https://api-mop.chinaums.com/v1/netpay/wx/unified-order
     * 支付宝：
     *   测试环境：http://58.247.0.18:29015/v1/netpay/trade/create
     *   生产环境：https://api-mop.chinaums.com/v1/netpay/trade/create
     *
     *  appid   10037e6f6823b20801682b6a5e5a0006
     *  appkey  1c4e3b16066244ae9b236a09e5b312e8
     *  mid：898201612345678
     *  tid：88880001
     * */
    static String appId = "10037e6f6823b20801682b6a5e5a0006";
    static String appKey = "1c4e3b16066244ae9b236a09e5b312e8";
    static String authorization;

    public static void main(String[] args) throws Exception {
        orders();
    }

    /**
     * 下单
     */
    public static void orders() throws Exception {
        /* post参数,格式:JSON */
        JSONObject json = new JSONObject();
        json.put("msgId", "001");   // 消息Id,原样返回
        json.put("requestTimestamp", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));    // 报文请求时间
        json.put("merOrderId", getMerchantOrderId()); // 商户订单号
        json.put("srcReserve", "请求系统预留字段"); // 请求系统预留字段
        json.put("mid", "898201612345678"); // 商户号
        json.put("tid", "88880001");    // 终端号
        json.put("instMid", "MINIDEFAULT"); // 业务类型

        json.put("subAppId", "8a81c1bd744ea7580174c2f5a436007e");      // 支付总金额
        /* 分账部分 */
       /* json.put("divisionFlag", true); // 分账标记
        json.put("platformAmount", 0); // 平台商户分账金额
*/
        /* subOrders,子订单信息 */
       /* ArrayList<JSON> subOrders = new ArrayList<>();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("mid", "988460101800204");
        jsonObject.put("totalAmount", 1);
        subOrders.add(jsonObject);*/
//         json.put("subOrders", subOrders);

        json.put("attachedData", "商户附加数据"); //商户附加数据
       // json.put("expireTime", DateFormatUtils.format(new Date().getTime() + 60 * 60 * 1000, "yyyy-MM-dd HH:mm:ss"));  // 订单过期时间,这里设置为一个小时

//        json.put("goodsTag", "商品标记");   // 商品标记
//        json.put("goodsTradeNo", "001");    // 商品交易单号，跟goods字段二选一
//        json.put("orderDesc", "账单描述");  // 账单描述
//        json.put("originalAmount", 10);     // 订单原始金额
//        json.put("productId", "001");   // 商品ID
        json.put("totalAmount", 1);      // 支付总金额
        json.put("notifyUrl", "https://www.sina.com.cn");  // 支付结果通知地址
        json.put("returnUrl", "http://www.wfdsj.cn/cn/index.htm");  // 网页跳转地址
//        json.put("showUrl", "http://www.baidu.com");    // 订单展示页面

        json.put("secureTransaction", "false"); // 担保交易标识
        json.put("subOpenId", "oah6x4l_tATwUL04AHw5ScpKV_Z8");      // 用户子标识，微信必传
//        json.put("userId", "111");  // 用户子标识，支付宝必传
        json.put("tradeType", "MINI");  // 交易类型
 //       json.put("merchantUserId", "1234567");  // 商户用户号，全民付必传
//        json.put("mobile", "1234567");  // 手机号，全民付必传

//        json.put("limitCreditCard", "false");   // 是否需要限制信用卡支付

        // 准备商品信息
        /*List<Goods> goodsList = new ArrayList<Goods>();
        goodsList.add(new Goods("0001", "充值0.01元", 1L, 100L, "Auto", "充值0.01元"));
        goodsList.add(new Goods("0002", "Goods Name", 2L, 200L, "Auto", "goods body"));

        json.put("goodsList", goodsList);*/

        String url = "http://58.247.0.18:29015/v1/netpay/wx/unified-order";
        String send = send(url, json.toString());
        System.out.println("返回结果:\n" + send);
    }

    /**
     * 发送请求
     *
     * @param url    eg:http://58.247.0.18:29015/v1/netpay/trade/create
     * @return
     * @throws Exception
     */
    public static String send(String url, String entity) throws Exception {
        authorization = getOpenBodySig(appId, appKey, entity);
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
        String bodyDigest = testSHA256(is); // eg:d60bc3aedeb853e2a11c0c096baaf19954dd9b752e48dea8e919e5fb29a42a8d
        System.out.println("bodyDigest:\n" + bodyDigest);
        String str1_C = appId + timestamp + nonce + bodyDigest; // eg:f0ec96ad2c3848b5b810e7aadf369e2f + 20190227113148 + be46cd581c9f46ecbd71b9858311ea12 + d60bc3aedeb853e2a11c0c096baaf19954dd9b752e48dea8e919e5fb29a42a8d

        System.out.println("str1_C:" + str1_C);

//        System.out.println("appKey_D:\n" + appKey);

        byte[] localSignature = hmacSHA256(str1_C.getBytes(), appKey.getBytes());

        String localSignatureStr = Base64.encodeBase64String(localSignature);   // Signature
        System.out.println("Authorization:\n" + "OPEN-BODY-SIG AppId=" + "\"" + appId + "\"" + ", Timestamp=" + "\"" + timestamp + "\"" + ", Nonce=" + "\"" + nonce + "\"" + ", Signature=" + "\"" + localSignatureStr + "\"\n");
        return ("OPEN-BODY-SIG AppId=" + "\"" + appId + "\"" + ", Timestamp=" + "\"" + timestamp + "\"" + ", Nonce=" + "\"" + nonce + "\"" + ", Signature=" + "\"" + localSignatureStr + "\"");
    }

    /**
     * 进行加密
     *
     * @param is
     * @return 加密后的结果
     */
    private static String testSHA256(InputStream is) {
        try {
//            System.out.println(is.hashCode());
            return DigestUtils.sha256Hex(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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
        return DateFormatUtils.format(new Date(), "yyyyMMddHHmmssSSS") + RandomStringUtils.randomNumeric(7);
    }

}
