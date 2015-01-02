package com.superest.server;

import io.undertow.Undertow;

import org.xnio.Options;

import com.superest.cache.CacheFatory;
import com.superest.db.DataBaseFactory;
import com.superest.resources.JaxrsApplication;

public class SuperRestServer extends Thread {

	private Class<? extends JaxrsApplication> applicationClass = null;
	private  UndertowJaxrsServer undertowJaxrsServer = null;
	private  String dbDir=null;
	
	public SuperRestServer() {
		
	}

	@Override
	public void run() {
		
		CacheFatory.init();
		
		DataBaseFactory.init(dbDir);
		
		undertowJaxrsServer = new UndertowJaxrsServer();
		SuperRestServerContext.setUndertowJaxrsServer(undertowJaxrsServer);
		undertowJaxrsServer.deploy(applicationClass);
		undertowJaxrsServer.start(Undertow.builder().setIoThreads(20)
				.setWorkerOption(Options.WORKER_TASK_MAX_THREADS, 500)
				.addHttpListener(8081, "0.0.0.0"));
		
		final UndertowJaxrsServer undertowJaxrsAdminServer = new UndertowJaxrsServer();
		undertowJaxrsAdminServer.deploy(AdminApplication.class);
		undertowJaxrsAdminServer.start(Undertow.builder().setIoThreads(2)
				.setWorkerOption(Options.WORKER_TASK_MAX_THREADS, 10)
				.addHttpListener(8082, "0.0.0.0"));

		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				DataBaseFactory.clear();
				undertowJaxrsServer.stop();
				undertowJaxrsAdminServer.stop();
				System.out.println("Inside Add Shutdown Hook");
			}
		});
	}

	public Class<? extends JaxrsApplication> getApplicationClass() {
		return applicationClass;
	}

	public void setApplicationClass(
			Class<? extends JaxrsApplication> applicationClass) {
		this.applicationClass = applicationClass;
	}

	public  UndertowJaxrsServer getUndertowJaxrsServer() {
		return undertowJaxrsServer;
	}

	public  void setUndertowJaxrsServer(
			UndertowJaxrsServer undertowJaxrsServer) {
		this.undertowJaxrsServer = undertowJaxrsServer;
	}

	public  String getDbDir() {
		return dbDir;
	}

	public  void setDbDir(String dbDir) {
		this.dbDir = dbDir;
	}

}
