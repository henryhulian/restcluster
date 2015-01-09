package com.restcluster.superest.cache;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.impl.LogFactoryImpl;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;

public class CacheManagerSingleton {
	
	private static final Log log = LogFactoryImpl.getLog(CacheManagerSingleton.class);

	private static CacheManagerSingleton instance = new CacheManagerSingleton();
	private EmbeddedCacheManager embeddedCacheManager = null ;
	
	private CacheManagerSingleton() {
	}
	
	public static CacheManagerSingleton getInstance(){
		
		if( instance.embeddedCacheManager==null ){
			instance.embeddedCacheManager = new DefaultCacheManager();
		}
		
		return instance;
	}
	
	public static CacheManagerSingleton getInstance( String configFilePath ){
		
		if( instance.embeddedCacheManager==null ){
			try {
				log.debug("Infinispan configration file:"+configFilePath);
				instance.embeddedCacheManager=new DefaultCacheManager(configFilePath);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		
		return instance;
	}

	public EmbeddedCacheManager getEmbeddedCacheManager() {
		return embeddedCacheManager;
	}

	public void setEmbeddedCacheManager(EmbeddedCacheManager embeddedCacheManager) {
		this.embeddedCacheManager = embeddedCacheManager;
	}

}
