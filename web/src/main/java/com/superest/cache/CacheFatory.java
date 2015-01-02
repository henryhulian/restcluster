package com.superest.cache;

import java.io.IOException;

import org.infinispan.Cache;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;

public class CacheFatory {

	/**
	 * database environment
	 * */
	private static EmbeddedCacheManager emCacheManager ;

	private CacheFatory() {
	}
	
	public static  EmbeddedCacheManager init(){
		emCacheManager=new DefaultCacheManager();
		return emCacheManager;
	}
	
	public static  EmbeddedCacheManager init( String config ){
		
		if( config==null ){
			return init();
		}
		
		try {
			emCacheManager=new DefaultCacheManager(config);
		} catch (IOException e) {
			throw new RuntimeException();
		}
		return emCacheManager;
	}
	
	public static <K, V>Cache<K, V> getCache(){
		return emCacheManager.getCache();
	}

	public static EmbeddedCacheManager getEmCacheManager() {
		return emCacheManager;
	}

	public static void setEmCacheManager(EmbeddedCacheManager emCacheManager) {
		CacheFatory.emCacheManager = emCacheManager;
	}

}
