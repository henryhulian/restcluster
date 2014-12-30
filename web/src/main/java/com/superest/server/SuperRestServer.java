package com.superest.server;

import io.undertow.Undertow;

import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
import org.xnio.Options;

import com.superest.resources.JaxrsApplication;

public class SuperRestServer extends Thread{

	@Override
	public void run() {
		final UndertowJaxrsServer undertowJaxrsServer = new UndertowJaxrsServer();
		undertowJaxrsServer.deploy(JaxrsApplication.class);
		undertowJaxrsServer.start(Undertow.builder().setIoThreads(20)
				.setWorkerOption(Options.WORKER_TASK_MAX_THREADS, 500)
				.addHttpListener(8081, "0.0.0.0"));

		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				undertowJaxrsServer.stop();
				System.out.println("Inside Add Shutdown Hook");
			}
		});
	}
	
}
