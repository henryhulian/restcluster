package com.superest.server;

import io.undertow.Undertow;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.impl.LogFactoryImpl;
import org.xnio.Options;

import com.superest.authtication.Authenticatior;
import com.superest.authtication.Authorization;
import com.superest.cache.CacheFatory;
import com.superest.db.DataBaseFactory;
import com.superest.resources.JaxrsApplication;
import com.superest.service.ServiceFatory;
import com.superest.session.SessionFatory;

public class SuperRestServer extends Thread {
	
	private static final Log log = LogFactoryImpl.getLog(SuperRestServer.class);

	private Class<? extends JaxrsApplication> applicationClass = null;
	private UndertowJaxrsServer undertowJaxrsServer = null;
	private String dbDir = null;
	private String configDir = null;
	private int webPort=8081;
	private int adminPort=8082;
	
	private String sessionKey;
	
	private Authenticatior authticatior;
	private Authorization authorization;

	public SuperRestServer() {

	}

	@Override
	public void run() {

		CacheFatory.init(configDir);
		
		SessionFatory.init( sessionKey );

		DataBaseFactory.init(dbDir);

		ServiceFatory.init(authticatior,authorization);
		
		undertowJaxrsServer = new UndertowJaxrsServer();
		SuperRestServerContext.setUndertowJaxrsServer(undertowJaxrsServer);
		undertowJaxrsServer.deploy(applicationClass);
		undertowJaxrsServer.start(Undertow.builder().setIoThreads(20)
				.setWorkerOption(Options.WORKER_TASK_MAX_THREADS, 500)
				.addHttpListener(webPort, "0.0.0.0"));

		final UndertowJaxrsServer undertowJaxrsAdminServer = new UndertowJaxrsServer();
		undertowJaxrsAdminServer.deploy(AdminApplication.class);
		undertowJaxrsAdminServer.start(Undertow.builder().setIoThreads(2)
				.setWorkerOption(Options.WORKER_TASK_MAX_THREADS, 10)
				.addHttpListener(adminPort, "0.0.0.0"));

		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				log.info("stop cache!");
				CacheFatory.clear();
				
				log.info("stop database!");
				DataBaseFactory.clear();
				
				log.info("stop server!");
				undertowJaxrsServer.stop();
				
				log.info("stop admin server!");
				undertowJaxrsAdminServer.stop();
				
				log.info("stop server success!");
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

	public UndertowJaxrsServer getUndertowJaxrsServer() {
		return undertowJaxrsServer;
	}

	public void setUndertowJaxrsServer(UndertowJaxrsServer undertowJaxrsServer) {
		this.undertowJaxrsServer = undertowJaxrsServer;
	}

	public String getDbDir() {
		return dbDir;
	}

	public void setDbDir(String dbDir) {
		this.dbDir = dbDir;
	}

	public String getConfigDir() {
		return configDir;
	}
	
	public int getWebPort() {
		return webPort;
	}

	public void setWebPort(int webPort) {
		this.webPort = webPort;
	}

	public int getAdminPort() {
		return adminPort;
	}

	public void setAdminPort(int adminPort) {
		this.adminPort = adminPort;
	}

	public void setConfigDir(String configDir) {
		this.configDir = configDir;
	}

	public Authenticatior getAuthticatior() {
		return authticatior;
	}

	public void setAuthticatior(Authenticatior authticatior) {
		this.authticatior = authticatior;
	}

	public Authorization getAuthorization() {
		return authorization;
	}

	public void setAuthorization(Authorization authorization) {
		this.authorization = authorization;
	}

	public String getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

}
