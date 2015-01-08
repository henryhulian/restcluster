package com.superest.test.resources.hello;

import java.io.File;
import java.util.Set;

import com.restcluster.superest.authtication.Authenticatior;
import com.restcluster.superest.authtication.Authorization;
import com.restcluster.superest.server.SuperRestServer;
import com.superest.test.resources.hello.resources.TestApplication;

public class TestServer {

	public static void main(String[] args) throws InterruptedException {

		final String dir = System.getProperty("user.dir");

		SuperRestServer superRestServer = new SuperRestServer();

		superRestServer.setDbDir(dir + File.separatorChar + "db");
		superRestServer.setConfigDir(dir + File.separatorChar + "config");
		superRestServer.setStaticResourcePath(dir + File.separatorChar + "web");
		superRestServer.setApplicationClass(TestApplication.class);
		
		superRestServer.setAuthticatior(new Authenticatior() {
			@Override
			public boolean authtication(String userName, String password) {
				// TODO Auto-generated method stub
				return true;
			}
		});

		superRestServer.setAuthorization(new Authorization() {
			@Override
			public boolean isUserAllowed(String username, Set<String> rolesSet) {
				boolean isAllowed = false;

				String userRole = "ADMIN";

				// Step 2. Verify user role
				if (rolesSet.contains(userRole)) {
					isAllowed = true;
				}
				return isAllowed;
			}
		});

		superRestServer.init();
		
		superRestServer.start();
		
		Thread.currentThread().join();

	}

}
