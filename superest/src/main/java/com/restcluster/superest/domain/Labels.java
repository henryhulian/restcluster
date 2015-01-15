package com.restcluster.superest.domain;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.impl.LogFactoryImpl;
import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Transaction;
import com.restcluster.superest.db.Neo4jDatabaseFactory;

public class Labels {
	
	private static final Log log = LogFactoryImpl.getLog(Labels.class);

	public static Label Session = DynamicLabel.label("Session");
	
	public static void init(){
		GraphDatabaseService databaseService = Neo4jDatabaseFactory.getInstance().getDatabaseService();
		try (Transaction transaction = databaseService.beginTx()) {
			log.info("init schema");
			try {
				
			} catch (Throwable e) {
				
			}
			transaction.success();
		};
	}
}
