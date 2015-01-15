package com.superest.test.resources.hello.data;

import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Transaction;

import com.restcluster.superest.db.Neo4jDatabaseFactory;

public class Labels {

	public static Label User = DynamicLabel.label("User");
	
	public static void init(){
		GraphDatabaseService databaseService = Neo4jDatabaseFactory.getInstance().getDatabaseService();
		try (Transaction transaction = databaseService.beginTx()) {
			System.out.println("init schema");
			try {
				
				databaseService.schema().constraintFor(Labels.User)
						.assertPropertyIsUnique("userName").create();
				
			} catch (Throwable e) {
				
			}
			transaction.success();
		};
	}
}
