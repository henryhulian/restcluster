package com.restcluster.superest.db;

import org.neo4j.graphdb.GraphDatabaseService;

public class DataBaseFactory {
	
	private String databasePath;
	private String databaseConfigPath;
	
	public GraphDatabaseService getGraphDatabase(){
		return Neo4jDatabaseSingleton.getInstance().getDatabaseService(databasePath,databaseConfigPath);
	}
	
	public void clear(){
		Neo4jDatabaseSingleton.getInstance().getDatabaseService(databasePath,databaseConfigPath).shutdown();
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
	
	
}
