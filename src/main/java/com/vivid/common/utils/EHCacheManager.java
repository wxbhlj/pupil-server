package com.vivid.common.utils;

import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheManagerBuilder;

public class EHCacheManager {

	private CacheManager cacheManager;
	
	private static EHCacheManager instance = new EHCacheManager();
	
	private EHCacheManager() {
		cacheManager = CacheManagerBuilder.newCacheManagerBuilder().build();
		cacheManager.init();
	}
	
	public static EHCacheManager getInstance() {
		return instance;
	}
	
	public static CacheManager getCacheManager() {
		return getInstance().cacheManager;
	}
}
