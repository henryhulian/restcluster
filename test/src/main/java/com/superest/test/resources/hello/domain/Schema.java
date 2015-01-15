package com.superest.test.resources.hello.domain;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;

import com.restcluster.superest.db.Neo4jDatabaseFactory;

public class Schema {

	
	public static void init(){
		GraphDatabaseService databaseService = Neo4jDatabaseFactory.getInstance().getDatabaseService();
		try (Transaction transaction = databaseService.beginTx()) {
			System.out.println("init schema");
			try {
				
				databaseService.schema().constraintFor(User.User)
						.assertPropertyIsUnique(User.USER_NAME).create();
				
			} catch (Throwable e) {
				
			}
			transaction.success();
		};
	}
}
