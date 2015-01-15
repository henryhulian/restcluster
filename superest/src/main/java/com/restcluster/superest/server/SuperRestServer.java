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
import com.restcluster.superest.authtication.AuthenticationService;
import com.restcluster.superest.authtication.Authenticatior;
import com.restcluster.superest.authtication.Authorization;
import com.restcluster.superest.cache.CacheFatory;
import com.restcluster.superest.common.ServerConfigCanstant;
import com.restcluster.superest.db.Neo4jDatabaseFactory;
import com.restcluster.superest.resources.JaxrsApplication;
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
	
	/**
	 * WEB服务
	 * */
	private UndertowJaxrsServer undertowJaxrsServer = null;
	
	/**
	 * 管理后台服务
	 * */
	private UndertowJaxrsServer adminUndertowJaxrsServer = null;
	
	/**
	 * 服务工作在路径
	 */
	private String workPath = null;
	
	/**
	 * 服务静态资源所在路径
	 */
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
	
	
	public SuperRestServer( String workPath , Class<? extends JaxrsApplication> applicationClass , Authenticatior authticatior , Authorization authorization ) {

		this.setWorkPath(workPath);
		this.applicationClass=applicationClass;
		this.authticatior=authticatior;
		this.authorization=authorization;
		
		String configurationFilePath=workPath+File.separatorChar+"config"+File.separatorChar+"server.properties";
		
		/*Get server configuration*/
		log.info("Read configuration from "+configurationFilePath);
		CompositeConfiguration config = new CompositeConfiguration();
		config.addConfiguration(new SystemConfiguration());
		try {
			config.addConfiguration(new PropertiesConfiguration(configurationFilePath));
		} catch (ConfigurationException e) {
			log.error("Cannot find server configuration file:"+configurationFilePath);
			throw new RuntimeException(e);
		}
		
		/*swagger configuration*/
		SwaggerConfig swaggerConfig = new SwaggerConfig();
        ConfigFactory.setConfig(swaggerConfig);
        swaggerConfig.setBasePath(applicationClass.getAnnotation(ApplicationPath.class).value());
        swaggerConfig.setApiVersion("1.0.0");
        ScannerFactory.setScanner(new DefaultJaxrsScanner());
        ClassReaders.setReader(new DefaultJaxrsApiReader());
		
		/*Set server port*/
		webPort=config.getInt(ServerConfigCanstant.PUBLIC_PORT,8081)+config.getInt(ServerConfigCanstant.PORT_OFFSET);
		webInterface=config.getString(ServerConfigCanstant.PUBLIC_INTERFACE,"0.0.0.0");
		
		adminPort=config.getInt(ServerConfigCanstant.ADMIN_PORT,8082)+config.getInt(ServerConfigCanstant.PORT_OFFSET);
		adminInterface=config.getString(ServerConfigCanstant.ADMIN_INTERFACE,"127.0.0.1");
		
		/*Server thread configuration*/
		serverIOThread=config.getInt(ServerConfigCanstant.SERVER_IO_THREAD,10);
		serverIOMaxWorker=config.getInt(ServerConfigCanstant.SERVER_IO_MAX_WORKER,500);
		
		staticResourcePath=config.getString(ServerConfigCanstant.STATIC_RESOURCE_PATH,workPath+File.separatorChar+"web");
		
		/*Initialize cache factory*/
		log.info("Initialize cache factory......");
		final CacheFatory cacheFatory=new CacheFatory();
		cacheFatory.setConfigFilePath(config.getString(ServerConfigCanstant.CACHE_CONFIG_FILE_PATH,workPath+File.separatorChar+"config"+File.separatorChar+"infinispan.xml"));
		
		/*Initialize session factory*/
		log.info("Initialize session factory......");
		final SessionFatory sessionFatory = new SessionFatory(cacheFatory);

		/*Initialize DB factory*/
		log.info("Initialize DB factory......");
		Neo4jDatabaseFactory neo4jDatabaseFactory = Neo4jDatabaseFactory.getInstance();
		neo4jDatabaseFactory.setDatabasePath(workPath+File.separatorChar+"db");
		neo4jDatabaseFactory.setDatabaseConfigPath(config.getString(ServerConfigCanstant.DBD_CONFIG_FILE_PATH,workPath+File.separatorChar+"config"+File.separatorChar+"neo4j.properties"));
		neo4jDatabaseFactory.setShellPort(1337+config.getInt(ServerConfigCanstant.PORT_OFFSET));
		neo4jDatabaseFactory.setModel(config.getString(ServerConfigCanstant.SERVER_MODEL,ServerConfigCanstant.SERVER_MODEL_SINGLE));
		neo4jDatabaseFactory.init();
		
		/*Initialize context*/
		AuthenticationService authenticationService = new AuthenticationService(authticatior, authorization);
		SuperRestServerContextSingleton context = SuperRestServerContextSingleton.getInstance();
		context.setSessionFatory(sessionFatory);
		context.setUndertowJaxrsServer(undertowJaxrsServer);
		context.setAdminUndertowJaxrsServer(adminUndertowJaxrsServer);
		context.setAuthenticationService(authenticationService);
		
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				log.info("stop cache!");
				cacheFatory.clear();
				
				log.info("stop database!");
				Neo4jDatabaseFactory.getInstance().clear();
				
				log.info("stop server!");
				undertowJaxrsServer.stop();
				
				log.info("stop admin server!");
				adminUndertowJaxrsServer.stop();
				
				log.info("stop server success!");
			}
		});
	}
	

	@Override
	public void run() {
		
		undertowJaxrsServer = new UndertowJaxrsServer();
		SuperRestServerContextSingleton.getInstance().setUndertowJaxrsServer(undertowJaxrsServer);
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


	public String getWorkPath() {
		return workPath;
	}


	public void setWorkPath(String workPath) {
		this.workPath = workPath;
	}

	
}
