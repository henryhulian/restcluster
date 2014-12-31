package com.superest.resources;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.superest.util.ClassCollectUtils;

@ApplicationPath("rest")
public class JaxrsApplication extends Application{

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<Class<?>>();
		classes.addAll(addPackage("com.superest.resources"));
		classes.addAll(addPackage(packageName()));
		return classes;
	}
	
	private Set<Class<?>> addPackage(String packageName) {
		
		if(packageName==null){
			return null;
		}
		
		Set<Class<?>> classes = new HashSet<Class<?>>();
		classes.addAll(new HashSet<>(ClassCollectUtils.find(packageName)));
		
		return classes;
	}
	
	public String packageName(){
		return null;
	}
}
