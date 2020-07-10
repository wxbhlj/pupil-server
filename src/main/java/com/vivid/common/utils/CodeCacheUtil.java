package com.vivid.common.utils;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;


public class CodeCacheUtil {
	
	
	private Cache<String, String> minutesCache10;
	
	private Cache<String, String> minutesCache1;
	
	private static CodeCacheUtil instance = new CodeCacheUtil();

	private CodeCacheUtil() {
		
		
		minutesCache10 = EHCacheManager.getCacheManager().createCache("minutesCache10", CacheConfigurationBuilder
                .newCacheConfigurationBuilder(String.class, String.class, ResourcePoolsBuilder.heap(1000))
                .withExpiry(ExpiryPolicyBuilder.timeToIdleExpiration(Duration.ofMinutes(10))).build());
		minutesCache1 = EHCacheManager.getCacheManager().createCache("minutesCache1", CacheConfigurationBuilder
                .newCacheConfigurationBuilder(String.class, String.class, ResourcePoolsBuilder.heap(1000))
                .withExpiry(ExpiryPolicyBuilder.timeToIdleExpiration(Duration.ofMinutes(1))).build());
	}
	
	public static CodeCacheUtil getInstance() {
		return instance;
	}
	
	public void put10(String key, String value) {
		minutesCache10.put(key, value);
	}
	
	public String get10(String key) {
		return minutesCache10.get(key);
	}
	
	public boolean exist1(String key) {
		return minutesCache1.containsKey(key);
	}
	
	public void put1(String key, String value) {
		minutesCache1.put(key, value);
	}

}
