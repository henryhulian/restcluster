package com.superest.test.resources.hello;

import javax.ws.rs.core.Application;

import com.superest.server.SuperRestServer;
import com.superest.test.resources.hello.resources.TestApplication;

public class TestServer {

	public static void main(String[] args) throws InterruptedException {
		
		SuperRestServer superRestServer = new SuperRestServer();
		superRestServer.setApplicationClass(TestApplication.class);
		superRestServer.start();
		Thread.currentThread().join();
		
	}
	
}
