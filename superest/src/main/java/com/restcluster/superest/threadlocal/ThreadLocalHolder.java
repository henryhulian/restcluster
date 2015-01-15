package com.restcluster.superest.threadlocal;

import java.text.SimpleDateFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.impl.LogFactoryImpl;
import org.neo4j.cypher.javacompat.ExecutionEngine;

import com.restcluster.superest.db.Neo4jDatabaseFactory;

public class ThreadLocalHolder {
	
	private static final Log log = LogFactoryImpl.getLog(ThreadLocalHolder.class);

	private static ThreadLocal<ExecutionEngine> graphDatabaseServiceThreadLocal=new ThreadLocal<ExecutionEngine>();
	private static ThreadLocal<SimpleDateFormat> threadLocalSimpleDateFormat = new ThreadLocal<SimpleDateFormat>();

	public static ExecutionEngine getExecutionEngine() {
		
		if(graphDatabaseServiceThreadLocal.get()==null){
			graphDatabaseServiceThreadLocal.set(new ExecutionEngine( Neo4jDatabaseFactory.getInstance().getDatabaseService()));
			log.debug("create ExecutionEngine on thread"+Thread.currentThread().getId());
		}
		log.debug("get ExecutionEngine on thread"+Thread.currentThread().getId());
		return graphDatabaseServiceThreadLocal.get();
	}


	public static SimpleDateFormat getSimpleDateFormat() {
		if(threadLocalSimpleDateFormat.get()==null){
			threadLocalSimpleDateFormat.set(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
			log.debug("create SimpleDateFormat on thread"+Thread.currentThread().getId());
		}
		log.debug("get SimpleDateFormat on thread"+Thread.currentThread().getId());
		return threadLocalSimpleDateFormat.get();
	}
	
}
