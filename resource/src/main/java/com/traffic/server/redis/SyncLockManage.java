package com.traffic.server.redis;

import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

/**
 * 统一并发锁
 *
 * @Author 黄国刚【1058118328@qq.com】
 */
@Component
public class SyncLockManage extends CacheManager<String> {


    private String preKey = projectKey + "sysnc_lock_key_";

    /**
     * 加锁
     *
     * @param functionName 方法标志
     * @param affix        词缀
     * @param seconds      锁时间长度
     * @return
     */
    public boolean lock(String functionName, String affix, Integer seconds) {
        String key = preKey + functionName + "_" + affix;
        Jedis redis = null;
        try {
            redis = redisClient.getResource();
            Long rs = redis.setnx(key, affix);
            if (rs == 0) {
                return false;
            }
            redis.expire(key, seconds);
        } finally {
            if (null != redis) {
                redis.close();
            }
        }
        return true;
    }

    /**
     * 释放锁
     *
     * @param functionName 方法标志
     * @param affix        词缀
     */
    public void unLock(String functionName, String affix) {
        String key = preKey + functionName + "_" + affix;
        Jedis redis = null;
        try {
            redis = redisClient.getResource();
            redis.del(key);
        } finally {
            if (null != redis) {
                redis.close();
            }
        }
    }

    /**
     * 并发锁
     *
     * @param serverName
     * @param methodName
     * @return
     */
    public Integer sysncLock(String serverName, String methodName) {
        Jedis redis = null;
        try {
            String key = preKey + serverName + "_" + methodName;
            redis = redisClient.getResource();
            Long num = redis.incr(key);
            if (num < 2) {
                redis.expire(key, 600);
            }
            return num.intValue();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != redis) {
                redis.close();
            }
        }
        return 0;
    }

    @Override
    protected Class<String> getDTOClass() {
        return String.class;
    }
}
