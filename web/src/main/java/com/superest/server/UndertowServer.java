package com.superest.server;

import io.undertow.Undertow;

import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
import org.xnio.Options;

import com.superest.resources.JaxrsApplication;

public class UndertowServer {

	public static void main(String[] args) throws InterruptedException {
		UndertowJaxrsServer undertowJaxrsServer = new UndertowJaxrsServer();
		undertowJaxrsServer.deploy(JaxrsApplication.class);
		undertowJaxrsServer.start(Undertow.builder().setIoThreads(20).setWorkerOption(Options.WORKER_TASK_MAX_THREADS,500).addHttpListener(8081,"0.0.0.0"));
		Thread.currentThread().join();
	}
}