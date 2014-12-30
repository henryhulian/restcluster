package com.superest.resources.hello;

import com.superest.server.SuperRestServer;

public class TestServer {

	public static void main(String[] args) throws InterruptedException {
		
		SuperRestServer superRestServer = new SuperRestServer();
		superRestServer.start();
		Thread.currentThread().join();
		
	}
	
}
