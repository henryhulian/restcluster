package com.superest.server;

import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;

import com.superest.resources.JaxrsApplication;

public class UndertowServer{

	public static void main(String[] args) throws InterruptedException {
		UndertowJaxrsServer undertowJaxrsServer = new UndertowJaxrsServer();
		undertowJaxrsServer.deploy(JaxrsApplication.class);
		undertowJaxrsServer.start();
		Thread.currentThread().join();
	}

}
