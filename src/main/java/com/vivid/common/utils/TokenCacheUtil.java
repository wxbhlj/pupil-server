package com.vivid.common.utils;

import org.ehcache.Cache;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;


public class TokenCacheUtil {


	
	private Cache<String, Integer> cache;
	
	private static TokenCacheUtil instance = new TokenCacheUtil();

	private TokenCacheUtil() {


		cache = EHCacheManager.getCacheManager().createCache("cache", CacheConfigurationBuilder
                .newCacheConfigurationBuilder(String.class, Integer.class, ResourcePoolsBuilder.heap(10000)));
	}
	
	public static TokenCacheUtil getInstance() {
		return instance;
	}
	
	public void put(String key, Integer value) {
		cache.put(key, value);
	}
	
	public Integer get(String key) {
		return cache.get(key);
	}
	
	public void remove(String key) {
		if(key != null) {
			cache.remove(key);
		}
	}
	

}
