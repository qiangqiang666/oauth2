package com.traffic.server.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class UUIDUtils {

    public static String getDefault() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }


    /**
     * 生成订单编号: {来源编号(4位)}{时间(yyyyMMddmmHHssSSS)(17位)}{7位随机数}
     */
    public static String generateOrderSn() {
        StringBuilder sb = new StringBuilder();
        //来源编号 银联分配的4位来源号
        sb.append("FX");
        sb.append(dateToString(new Date()));

        sb.append( RandomStringUtils.randomNumeric(7));
        return sb.toString();
    }

    public static String dateToString(Date time) {
        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat("yyyyMMddmmHHssSSS");
        String ctime = formatter.format(time);
        return ctime;
    }


}
