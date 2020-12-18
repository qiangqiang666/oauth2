package com.traffic.server.redis;


import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.traffic.server.utils.http.JsonUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * Redis 客户端
 * @author huangggg
 *
 */
@Component
@Getter
@Setter
public class BaseCacheClient {
	@Value("${spring.redis.host}")
	private String host;
	@Value("${spring.redis.port}")
	private int port;
	@Value("${spring.redis.password}")
	private String password;
	@Value("${spring.redis.database}")
	private int database;

	private JedisPool pool;

	private boolean redisEnable = true;
	
	/**
	 * 初始化
	 */
	@PostConstruct
	public void init() {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxIdle(50);
		config.setMaxTotal(1000);
		config.setMaxWaitMillis(1000);
		config.setTestOnBorrow(true);
		config.setTestOnReturn(true);
		pool = new JedisPool(config, host, Integer.valueOf(port), 3000, password,database);
		Jedis resource = pool.getResource();
		resource.close();
	}
	
	@PreDestroy
	public void destory() {
		pool.close();
	}

	public Jedis getResource() {
		return pool.getResource();
	}

	protected void returnResource(Jedis jedis) {
		jedis.close();
	}

	public <T> void innerPut(String key, T value) {
		innerPut(key, value, 7200);
	}

	public <T> void innerPut(String key, T value, int seconds) {
		if (redisEnable == false) {
			return;
		}
		if (value == null) {
			return;
		}
		byte[] data = stringToByte(JsonUtil.toJson(value));
		Jedis resource = getResource();
		try {
			byte[] keyBytes = key.getBytes();
			resource.set(keyBytes, data);
			resource.expire(keyBytes, seconds);
		} finally {
			resource.close();
		}
	}
	public String simpleGet(String key) {
		if (redisEnable == false) {
			return null;
		}
		String value = null;
		Jedis resource = getResource();
		try {
			byte[] data = resource.get(stringToByte(key));
			if (data == null) {
				return null;
			}
			value = byteToString(data);
		} finally {
			resource.close();
		}
		return value;
	}

	private String byteToString(byte[] data) {
		try {
			return new String(data, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}

	public void simplePut(String key, String value, int seconds) {
		if (redisEnable == false) {
			return;
		}
		if (value == null) {
			return;
		}

		byte[] data = stringToByte(value);
		Jedis resource = getResource();
		try {
			byte[] keyBytes = stringToByte(key);
			resource.set(keyBytes, data);
			resource.expire(keyBytes, seconds);
		} finally {
			resource.close();
		}
	}

	private byte[] stringToByte(String value) {
		try {
			return value.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}

	public <T> T innerGet(String key, Class<T> clazz) {
		if (redisEnable == false) {
			return null;
		}
		Jedis resource = getResource();
		try {
			byte[] data = resource.get(key.getBytes());
			if (data == null) {
				return null;
			}
			T value = JsonUtil.fromJson(byteToString(data), clazz);
			return value;
		} finally {
			resource.close();
		}
	}

	/**
	 * 获取list缓存对象
	 * @param key
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> innerGetList(String key,Class<T> clazz){
		if (redisEnable == false) {
			return null;
		}
		Jedis resource = getResource();
		try {
			byte[] data = resource.get(key.getBytes());
			if (data == null) {
				return null;
			}
			List<T> list = (List<T>) SerializationUtils.deserialize(data);
			return list;
		} finally {
			resource.close();
		}
	}
	
	public void innerRemove(String key) {
		if (redisEnable == false) {
			return;
		}
		Jedis resource = getResource();
		resource.del(key.getBytes());
		resource.close();
	}

	public Long incr(String key){

		Jedis resource = getResource();

		try {

			Long num = resource.incr(key);
			if(num.intValue()==1)
				resource.expire(key,18000);
			return num;
		}catch (Exception io){
			io.printStackTrace();
		}finally {
			resource.close();
		}
		return 0l;
	}

	public void hset(String key,String filed,String str){

		Jedis resource = getResource();

		try {
			resource.hset(key,filed,str);
			resource.expire(key,18000);
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			resource.close();
		}
	}

	public String hget(String key,String filed){

		Jedis resource = getResource();
		try {

			return resource.hget(key,filed);
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			resource.close();
		}
		return null;
	}

    public Map<String, String> hgetAll(String key){

        Jedis resource = getResource();
        try {
            return resource.hgetAll(key);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            resource.close();
        }
        return null;
    }

	public Object blpop(String key, int waitSeconds){
		Jedis resource = null;

		try{
			resource = getResource();
			List<byte[]> values = resource.brpop(waitSeconds, key.getBytes());

			if (values != null && values.size() > 0){
				byte[] value = values.get(1);
				return SerializeUtils.deserialize(value);
			}
			else{
				return null;
			}
		}
		catch (Exception e){
			return null;
		}
		finally{
			resource.close();
		}
	}

	public <T> Long rPush(String key, T value, int second){

		Jedis resource = null;
		Long ret = null;
		try{

			resource = getResource();
			byte[] bytes = SerializeUtils.serialize(value);
			ret = resource.rpush(key.getBytes(), bytes);

			if (second > 0)
				resource.expire(key, second);
		}
		catch (Exception e){
			e.printStackTrace();
		}
		finally{
			resource.close();
		}

		return ret;
	}

    public Boolean setNX(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = getResource();
            //0-key设置失败, 1-key设置成功
            return jedis.setnx(key, value) == 1;
        } catch (Exception e) {
            return false;
        } finally {
            jedis.close();
        }
    }
    public String getAndSet(String key, String value) {
        Jedis jedis = null;
        boolean broken = false;
        try {
            jedis = getResource();
            return jedis.getSet(key, value);
        } catch (Exception e) {
            return null;
        } finally {
            jedis.close();
        }
    }
}
