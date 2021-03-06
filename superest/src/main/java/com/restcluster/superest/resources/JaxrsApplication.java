package com.restcluster.superest.resources;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.Path;
import javax.ws.rs.core.Application;
import javax.ws.rs.ext.Provider;

import org.reflections.Reflections;


public class JaxrsApplication extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<Class<?>>();
		classes.add(com.wordnik.swagger.jaxrs.listing.ApiListingResource.class);
		classes.add(com.wordnik.swagger.jaxrs.listing.ApiDeclarationProvider.class);
		classes.add(com.wordnik.swagger.jaxrs.listing.ApiListingResourceJSON.class);
		classes.add(com.wordnik.swagger.jaxrs.listing.ResourceListingProvider.class);
		classes.add(SecurityInterceptor.class);
		classes.addAll(addPackage(packageName()));
		return classes;
	}

	private Set<Class<?>> addPackage(String packageName) {

		if (packageName == null) {
			return null;
		}

		Reflections reflections = new Reflections(packageName);
		Set<Class<?>> classes = reflections.getTypesAnnotatedWith(Path.class);
		classes.addAll(reflections.getTypesAnnotatedWith(Provider.class));

		return classes;
	}

	public String packageName() {
		return null;
	}
}
