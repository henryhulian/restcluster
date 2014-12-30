package com.superest.resources.hello;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.superest.security.SecurityInterceptor;

@ApplicationPath("rest")
public class JaxrsApplication extends Application{

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<Class<?>>();
		classes.add(HelloWorld.class);
		classes.add(SecurityInterceptor.class);
		return classes;
	}
	
}
