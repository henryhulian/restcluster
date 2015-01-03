package com.superest.server;

import java.io.File;

import io.undertow.Undertow;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.SystemConfiguration;
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
	
	private Authenticatior authticatior= null;
	private Authorization authorization= null;
	
	private Integer webPort= null;
	private Integer adminPort= null;
	private String sessionKey= null;

	public SuperRestServer() {

	}
	
	public void init(){
		
		/*Get server configuration*/
		CompositeConfiguration config = new CompositeConfiguration();
		config.addConfiguration(new SystemConfiguration());
		try {
			config.addConfiguration(new PropertiesConfiguration(configDir+File.separatorChar+"server.properties"));
		} catch (ConfigurationException e) {
			log.error(e);
			log.error("Cannot find server configuration file:"+configDir+File.separatorChar+"server.properties");
		}
		
		/*Set server port*/
		webPort=config.getInt("webPort",8081);
		adminPort=config.getInt("adminPort",8082);
		
		/*INIT INFINISPAN cache*/
		CacheFatory.init(configDir);
		
		/*INIT session factory*/
		SessionFatory.init( config.getString("sessionKey") );

		/*INIT database factory*/
		DataBaseFactory.init(dbDir);

		/*INIT service factory*/
		ServiceFatory.init(authticatior,authorization);
		
	}

	@Override
	public void run() {
		
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
