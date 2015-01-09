package com.restcluster.superest.cache;

import org.infinispan.Cache;

public class CacheFatory {
	
	private String configFilePath;
	
	/**
	 * 获取默认缓存
	 * */
	public  <K, V>Cache<K, V> getCache(){
		return CacheManagerSingleton.getInstance().getEmbeddedCacheManager().getCache();
	}
	
	/**
	 * 获取命名缓存
	 * @param cacheName
	 * @return
	 */
	public  <K, V>Cache<K, V> getCache( String cacheName ){
		return CacheManagerSingleton.getInstance(configFilePath).getEmbeddedCacheManager().getCache(cacheName);
	}

	/**
	 * 清理缓存
	 */
	public  void clear() {
		CacheManagerSingleton.getInstance().getEmbeddedCacheManager().stop();
	}

	public String getConfigFilePath() {
		return configFilePath;
	}

	public void setConfigFilePath(String configFilePath) {
		this.configFilePath = configFilePath;
	}

}
