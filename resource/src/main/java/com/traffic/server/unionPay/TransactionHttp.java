package com.traffic.server.unionPay;

import com.traffic.server.enums.ErrorCodeEnum;
import com.traffic.server.exception.ApiException;
import com.traffic.server.utils.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 银联工具类
 * @author belive
 */
@Slf4j
@Service
public class TransactionHttp {

    private static String appId;

    private static String appKey;


    @Value("${unionPay.u_appId}")
    public void setAppId(String u_appId){
        appId = u_appId;
    }
    @Value("${unionPay.u_appKey}")
    public void setAppKey(String u_appKey){
        appKey = u_appKey;
    }

	static String authorization;
    private static CloseableHttpClient httpClient;
    private static CloseableHttpResponse response;

    public  String send(String url, String entity){
        try {
            log.info("{银联请求URL}==========="+url);
            log.info("{银联请求Param}==========="+entity);
            authorization = getOpenBodySig(appId, appKey, entity);
            log.info("{authorization}==="+authorization);
            httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader("Authorization", authorization);
            StringEntity se = new StringEntity(entity, "UTF-8");
            se.setContentType("application/json");
            httpPost.setEntity(se);
            response = httpClient.execute(httpPost);
            HttpEntity entity1 = response.getEntity();
            String resStr = null;
            if (entity1 != null) {
                resStr = EntityUtils.toString(entity1, "UTF-8");
            }
            log.info("{银联请求Result}==========="+resStr);
            return resStr;
        }catch (ClientProtocolException e1){
             e1.printStackTrace();
            throw new ApiException(ErrorCodeEnum.UNION01);
        }catch (Exception e){
            e.printStackTrace();
            throw new ApiException(ErrorCodeEnum.UNION01);
        }finally {
            try {
                httpClient.close();
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 生成签名信息认证
     * @param appId 银联appId
     * @param appKey 银蓝秘钥
     * @param body 请求数据
     * @return
     */
	public static String getOpenBodySig(String appId, String appKey, String body){
        try{
            //时间戳
            String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            //生成随机数
            String nonce = UUIDUtils.getDefault();
            byte[] data = body.getBytes("UTF-8");
            InputStream is = new ByteArrayInputStream(data);
            //获取签名
            String bodyDigest = testSHA256(is);
            String str1_C = appId+timestamp+nonce+bodyDigest;

            byte[] localSignature = hmacSHA256(str1_C.getBytes(), appKey.getBytes());
            String localSignatureStr = Base64.encodeBase64String(localSignature);
            return ("OPEN-BODY-SIG AppId="+"\""+appId+"\""+", Timestamp="+"\""+timestamp+"\""+", Nonce="+"\""+nonce+"\""+", Signature="+"\""+localSignatureStr+"\"");
        }catch (Exception e){
            throw new ApiException(ErrorCodeEnum.UNION02);
        }
    }

    /**
     * 数据流加密
     * @param is 数据流
     * @return
     */
    private static String testSHA256(InputStream is) {
        try {
            return DigestUtils.sha256Hex(is);
        } catch (IOException e) {
            throw new ApiException(ErrorCodeEnum.UNION03);
        }
    }

    /**
     * 数据加密
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

}
