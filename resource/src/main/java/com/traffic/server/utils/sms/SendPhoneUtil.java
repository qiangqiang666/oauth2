package com.traffic.server.utils.sms;

import com.traffic.server.enums.ErrorCodeEnum;
import com.traffic.server.exception.ApiException;
import com.traffic.server.utils.GsonUtils;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * 描述:
 * 〈发送手机验证码工具类〉
 *
 * @author Monkey
 * @create 2020/12/11 20:36
 */
public class SendPhoneUtil {

    /**
     * 手机发送验证码
     *
     * @param phone 手机号
     * @param code  验证码
     * @return
     */
    public static boolean sendCode(String phone, String code) throws ApiException {
        try {
            if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(code)) {
                throw new ApiException(ErrorCodeEnum.SMS1005);
            }
            String url = "http://118.190.41.112/smsJson.aspx?action=send&userid=&account=jntcjt&password=CBC0F04705FA76171654A27A5065754C&sendTime=&extno=";
            url += "&mobile=" + phone + "&content=" + code;
            //System.out.println(url);
            /*{"returnstatus":"Success","message":"操作成功","remainpoint":"-235010","taskID":"2012084130105446","successCounts":"1"}*/
            /**
             * {"returnstatus":"Faild","message":"手机号码为空","remainpoint":"0","taskID":"","successCounts":"0"}
             * */
            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Call call = okHttpClient.newCall(request);
            Response response = call.execute();
            System.out.println(response);
            if (response.code() == 200) {
                String result = response.body().string();
                if (result.contains("Success")) {
                    return true;
                } else {
                    Map map = GsonUtils.fromJson(result, Map.class);
                    throw new ApiException(ErrorCodeEnum.SMS1001.code(), map.get("message").toString());
                }
            }
            return false;
        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            throw new ApiException(ErrorCodeEnum.SMS1000);
        }
    }

    public static void main(String[] args) {
        SendPhoneUtil.sendCode("17671632486", "123456");
        //System.out.println((int)((Math.random()*9+1)*100000));
    }
}