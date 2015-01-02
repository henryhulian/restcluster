package com.superest.test.resources.hello;

import java.io.File;

import com.superest.authtication.Authticatior;
import com.superest.server.SuperRestServer;
import com.superest.test.resources.hello.resources.TestApplication;

public class TestServer {
	

	public static void main(String[] args) throws InterruptedException {
		
		
		final String dir = System.getProperty("user.dir");
		
		SuperRestServer superRestServer = new SuperRestServer();
		
		superRestServer.setDbDir(dir+File.separatorChar+"db");
		superRestServer.setConfigDir(".");
		superRestServer.setApplicationClass(TestApplication.class);
		superRestServer.setAuthticatior(new Authticatior() {
			@Override
			public boolean authtication(String userName, String password) {
				// TODO Auto-generated method stub
				return true;
			}
		});

		superRestServer.start();
		Thread.currentThread().join();
		
	}
	
}
