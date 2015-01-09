package com.superest.test.resources.hello;

import java.util.Set;

import com.restcluster.superest.authtication.Authenticatior;
import com.restcluster.superest.authtication.Authorization;
import com.restcluster.superest.server.SuperRestServer;
import com.superest.test.resources.hello.resources.TestApplication;

public class TestServer {

	public static void main(String[] args) throws InterruptedException {

		String workPath = System.getProperty("user.dir");

		SuperRestServer superRestServer = new SuperRestServer(workPath,TestApplication.class,new Authenticatior() {
			
			@Override
			public boolean authtication(String userName, String password) {
				// TODO Auto-generated method stub
				return true;
			}
		},new Authorization() {
			
			@Override
			public boolean isUserAllowed(String username, Set<String> rolesSet) {
				// TODO Auto-generated method stub
				return true;
			}
		});

		superRestServer.start();
		
		Thread.currentThread().join();

	}

}
