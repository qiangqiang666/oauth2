package com.traffic.authentication.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 缓存基类
 * @author huanggg
 *
 */
@Component
public abstract class CacheManager<T> {

	@Resource
	protected BaseCacheClient redisClient;


	@Value("${lgp-core.redis-key:wb_core}")
	protected String projectKey;
	
	protected abstract Class<T> getDTOClass();
	
	public void put(T value,String key) {
		
		redisClient.innerPut(key, value);
	}
	public void put(T value,String key,int time) {
		
		redisClient.innerPut(key, value,time);
	}

	public T get(String key) {
		
		try {
			T value = redisClient.innerGet(key,getDTOClass());
			return value;
		} catch (Exception e) {
			return null;
		}
	}

	public void remove(String key) {
        if (key != null)
        	redisClient.innerRemove(key);
    }
	
	/**
	 * 缓存list
	 * @param values
	 * @param key
	 */
	public void putList(List<T> values,String key){
		
		redisClient.innerPut(key, values);
	}
	
	/**
	 * 获取缓存list 对象
	 * @param key
	 * @return
	 */
	public List<T> getList(String key) {
		
		try {
			List<T> values = redisClient.innerGetList(key,getDTOClass());
			return values;
		} catch (Exception e) {
			return null;
		}
	}
}
