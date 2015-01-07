package com.restcluster.superest.cache;

import java.io.File;
import java.io.IOException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.impl.LogFactoryImpl;
import org.infinispan.Cache;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;

public class CacheFatory {
	
	private static final Log log = LogFactoryImpl.getLog(CacheFatory.class);

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
	
	public static  EmbeddedCacheManager init( String configDir ){
		
		if( configDir==null ){
			return init();
		}
		
		try {
			log.trace("infinispan configration file:"+configDir+File.separatorChar+"infinispan.xml");
			emCacheManager=new DefaultCacheManager(configDir+File.separatorChar+"infinispan.xml");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return emCacheManager;
	}
	
	public static <K, V>Cache<K, V> getCache(){
		return emCacheManager.getCache();
	}
	
	public static <K, V>Cache<K, V> getCache( String cacheName ){
		return emCacheManager.getCache(cacheName);
	}

	public static EmbeddedCacheManager getEmCacheManager() {
		return emCacheManager;
	}

	public static void clear() {
		emCacheManager.stop();
	}

}
