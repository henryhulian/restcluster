package com.superest.test.resources.hello.resources;

import javax.ws.rs.ApplicationPath;

import com.restcluster.superest.resources.JaxrsApplication;

@ApplicationPath("rest1")
public class TestApplication extends JaxrsApplication{

	@Override
	public String packageName() {
		// TODO Auto-generated method stub
		return "com.superest.test.resources.hello.resources";
	}
}
