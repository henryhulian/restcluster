package com.superest.test.resources.hello;

import com.superest.server.SuperRestServer;
import com.superest.test.resources.hello.resources.TestApplication;

public class TestServer {
	

	public static void main(String[] args) throws InterruptedException {
		
		
		final String dir = System.getProperty("user.dir");
		
		SuperRestServer superRestServer = new SuperRestServer();
		superRestServer.setDbDir(dir+"/db");
		superRestServer.setApplicationClass(TestApplication.class);
		superRestServer.start();
		
		Thread.currentThread().join();
		
	}
	
}
