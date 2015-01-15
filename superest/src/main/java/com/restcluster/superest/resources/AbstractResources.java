package com.restcluster.superest.resources;

import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.graphdb.GraphDatabaseService;

import com.restcluster.superest.db.Neo4jDatabaseFactory;
import com.restcluster.superest.threadlocal.ThreadLocalHolder;

public class AbstractResources {

	public GraphDatabaseService getDatabase(){
		return Neo4jDatabaseFactory.getInstance().getDatabaseService();
	}
	
	public ExecutionEngine getExecutionEngine(){
		return ThreadLocalHolder.getExecutionEngine();
	}
}
