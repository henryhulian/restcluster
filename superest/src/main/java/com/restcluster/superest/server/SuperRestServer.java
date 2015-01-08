package com.restcluster.superest.server;

import java.io.File;

import javax.ws.rs.ApplicationPath;

import io.undertow.Undertow;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.SystemConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.impl.LogFactoryImpl;
import org.xnio.Options;

import com.restcluster.superest.admin.AdminApplication;
import com.restcluster.superest.authtication.Authenticatior;
import com.restcluster.superest.authtication.Authorization;
import com.restcluster.superest.cache.CacheFatory;
import com.restcluster.superest.common.ServerConfigCanstant;
import com.restcluster.superest.db.DataBaseFactory;
import com.restcluster.superest.resources.JaxrsApplication;
import com.restcluster.superest.service.ServiceFatory;
import com.restcluster.superest.session.SessionFatory;
import com.wordnik.swagger.config.ConfigFactory;
import com.wordnik.swagger.config.ScannerFactory;
import com.wordnik.swagger.config.SwaggerConfig;
import com.wordnik.swagger.jaxrs.config.DefaultJaxrsScanner;
import com.wordnik.swagger.jaxrs.reader.DefaultJaxrsApiReader;
import com.wordnik.swagger.reader.ClassReaders;



public class SuperRestServer extends Thread {
	
	private static final Log log = LogFactoryImpl.getLog(SuperRestServer.class);

	private Class<? extends JaxrsApplication> applicationClass = null;
	private UndertowJaxrsServer undertowJaxrsServer = null;
	private UndertowJaxrsServer adminUndertowJaxrsServer = null;
	
	private String dbDir = null;
	private String configDir = null;
	private String staticResourcePath = null;
	
	private Authenticatior authticatior= null;
	private Authorization authorization= null;
	
	private Integer webPort= null;
	private String  webInterface=null;
	
	private Integer adminPort= null;
	private String  adminInterface=null;
	
	private Integer serverIOThread=null;
	private Integer serverIOMaxWorker=null;
	
	private String sessionKey= null;

	public SuperRestServer() {

	}
	
	public void init(){
		
		String configurationFilePath=configDir+File.separatorChar+"server.properties";
		
		/*swagger configuration*/
		SwaggerConfig swaggerConfig = new SwaggerConfig();
        ConfigFactory.setConfig(swaggerConfig);
        swaggerConfig.setBasePath(applicationClass.getAnnotation(ApplicationPath.class).value());
        swaggerConfig.setApiVersion("1.0.0");
        ScannerFactory.setScanner(new DefaultJaxrsScanner());
        ClassReaders.setReader(new DefaultJaxrsApiReader());
		
		/*Get server configuration*/
		log.info("Read configuration from "+configurationFilePath);
		CompositeConfiguration config = new CompositeConfiguration();
		config.addConfiguration(new SystemConfiguration());
		try {
			config.addConfiguration(new PropertiesConfiguration(configurationFilePath));
		} catch (ConfigurationException e) {
			log.error(e);
			log.error("Cannot find server configuration file:"+configurationFilePath);
		}
		
		/*Set server port*/
		webPort=config.getInt(ServerConfigCanstant.WEB_PORT,8081);
		webInterface=config.getString(ServerConfigCanstant.WEB_INTERFACE,"0.0.0.0");
		
		adminPort=config.getInt(ServerConfigCanstant.ADMIN_PORT,8082);
		adminInterface=config.getString(ServerConfigCanstant.ADMIN_INTERFACE,"127.0.0.1");
		
		/*Server thread configuration*/
		serverIOThread=config.getInt(ServerConfigCanstant.SERVER_IO_THREAD,10);
		serverIOMaxWorker=config.getInt(ServerConfigCanstant.SERVER_IO_MAX_WORKER,500);
		
		/*INIT INFINISPAN cache*/
		log.info("INIT INFINISPAN cache....");
		CacheFatory.init(configDir);
		
		/*INIT session factory*/
		log.info("INIT session factory....");
		SessionFatory.init( config.getString(ServerConfigCanstant.SESSION_KEY) );

		/*INIT database factory*/
		log.info("INIT database factory....");
		DataBaseFactory.init(dbDir);

		/*INIT service factory*/
		log.info("NIT service factory....");
		ServiceFatory.init(authticatior,authorization);
		
	}

	@Override
	public void run() {
		
		undertowJaxrsServer = new UndertowJaxrsServer();
		SuperRestServerContext.setUndertowJaxrsServer(undertowJaxrsServer);
		undertowJaxrsServer.deploy(applicationClass,staticResourcePath);
		undertowJaxrsServer.start(Undertow.builder().setIoThreads(serverIOThread)
				.setWorkerOption(Options.WORKER_TASK_MAX_THREADS, serverIOMaxWorker)
				.addHttpListener(webPort, webInterface));
		

		adminUndertowJaxrsServer = new UndertowJaxrsServer();
		adminUndertowJaxrsServer.deploy(AdminApplication.class,staticResourcePath);
		adminUndertowJaxrsServer.start(Undertow.builder().setIoThreads(2)
				.setWorkerOption(Options.WORKER_TASK_MAX_THREADS, 10)
				.addHttpListener(adminPort, adminInterface));
		
		log.info("Superest Start server at "+webInterface+":"+webPort+" staticResourcePath:"+staticResourcePath);
		log.info("Superest Start admin interface at "+adminInterface+":"+adminPort+" staticResourcePath:"+staticResourcePath);

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
				adminUndertowJaxrsServer.stop();
				
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

	public String getStaticResourcePath() {
		return staticResourcePath;
	}

	public void setStaticResourcePath(String staticResourcePath) {
		this.staticResourcePath = staticResourcePath;
	}
	
}
