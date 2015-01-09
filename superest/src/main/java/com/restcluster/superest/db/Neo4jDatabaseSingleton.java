package com.restcluster.superest.db;

import org.apache.commons.lang.StringUtils;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

public class Neo4jDatabaseSingleton {

	private static Neo4jDatabaseSingleton instance = new Neo4jDatabaseSingleton();
	private GraphDatabaseService databaseService;
	
	
	private Neo4jDatabaseSingleton() {
	}
	
	public static Neo4jDatabaseSingleton getInstance(){
		return instance;
	}
	

	public GraphDatabaseService getDatabaseService( String databasePath ) {
		
		if( StringUtils.isEmpty(databasePath) ){
			throw new RuntimeException("Neo4j database path is empty!");
		}
		
		if( databaseService==null){
			databaseService=new GraphDatabaseFactory().newEmbeddedDatabase( databasePath );
		}
		
		return databaseService;
	}

	public void setDatabaseService(GraphDatabaseService databaseService) {
		this.databaseService = databaseService;
	}
	
}
