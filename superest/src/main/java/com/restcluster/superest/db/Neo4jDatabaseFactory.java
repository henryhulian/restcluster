package com.restcluster.superest.db;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.shell.ShellSettings;

public class Neo4jDatabaseFactory {

	private static Neo4jDatabaseFactory instance = new Neo4jDatabaseFactory();
	
	private GraphDatabaseService databaseService;
	private String databasePath;
	private String databaseConfigPath;
	private Integer shellPort;
	
	
	private Neo4jDatabaseFactory() {
	}
	
	public static Neo4jDatabaseFactory getInstance(){
		return instance;
	}
	
	public void init(){
		if( databaseService==null ){
			databaseService = new GraphDatabaseFactory().newEmbeddedDatabaseBuilder(databasePath).loadPropertiesFromFile(databaseConfigPath).setConfig(ShellSettings.remote_shell_port,String.valueOf(shellPort)).newGraphDatabase();
		}
	}

	public GraphDatabaseService getDatabaseService() {
		return databaseService;
	}


	public String getDatabasePath() {
		return databasePath;
	}

	public void setDatabasePath(String databasePath) {
		this.databasePath = databasePath;
	}

	public String getDatabaseConfigPath() {
		return databaseConfigPath;
	}

	public void setDatabaseConfigPath(String databaseConfigPath) {
		this.databaseConfigPath = databaseConfigPath;
	}

	public Integer getShellPort() {
		return shellPort;
	}

	public void setShellPort(Integer shellPort) {
		this.shellPort = shellPort;
	}

	public void clear() {
		if( databaseService!=null ){
			databaseService.shutdown();
		}
	}
	
}
