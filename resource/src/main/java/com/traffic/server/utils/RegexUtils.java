package com.traffic.server.utils;

import java.util.regex.Pattern;
 
/**
 * Created with IDEA
 * author:Dingsheng Huang
 * Date:2019/7/2
 * Time:下午6:51
 */
public class RegexUtils {

    private static final  String PHONE_REGEX = "^[1](([3][0-9])|([4][5-9])|([5][0-3,5-9])|([6][5,6])|([7][0-8])|([8][0-9])|([9][1,8,9]))[0-9]{8}$";
    /**
     *  正则：手机号
     * @param in
     * @return 符合返回true,不符合false
     */
    public static boolean validateMobilePhone(String in) {
        Pattern pattern = Pattern.compile(PHONE_REGEX);
        return pattern.matcher(in).matches();
    }
 
    public static void main(String[] args) {
        System.out.println(validateMobilePhone("13100000000"));
 
        System.out.println(validateMobilePhone("123"));
    }
}