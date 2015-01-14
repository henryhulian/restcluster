package com.superest.test.resources.hello.resources;

import org.neo4j.graphdb.GraphDatabaseService;

import com.restcluster.superest.server.SuperRestServerContextSingleton;

abstract class DatabaseAware {

	public GraphDatabaseService getDatabase(){
		SuperRestServerContextSingleton context = SuperRestServerContextSingleton.getInstance();
		GraphDatabaseService databaseService = context.getDataBaseFactory().getGraphDatabase();
		return databaseService;
	}
}
