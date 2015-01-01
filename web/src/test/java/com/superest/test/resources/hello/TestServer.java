package com.superest.test.resources.hello;

import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;

import com.superest.db.DataBaseFactory;
import com.superest.server.SuperRestServer;
import com.superest.test.resources.hello.resources.TestApplication;


public class TestServer {
	
	public static EmbeddedCacheManager emCacheManager ;
	

	public static void main(String[] args) throws InterruptedException {
		
		emCacheManager = new DefaultCacheManager();
		
		final String dir = System.getProperty("user.dir");
		System.out.println(dir);
		DataBaseFactory.init(dir+"/db");
	
		DataBaseFactory.openDatabase("user");
		
		SuperRestServer superRestServer = new SuperRestServer();
		superRestServer.setApplicationClass(TestApplication.class);
		superRestServer.start();
		Thread.currentThread().join();
		
	}
	
}
