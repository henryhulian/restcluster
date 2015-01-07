package com.restcluster.superest.db;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;

public class DataBaseFactory {
	
	/**
	 * database environment
	 * */
	private static Environment environment=null;
	
	/**
	 * database map , this is used to close all opened database when server shutdown
	 * */
	private final static Map<String,Database> databases = new HashMap<String, Database>();

	private DataBaseFactory() {
	}
	
	public static  Environment init( String dbDir ){
		EnvironmentConfig environmentConfig = new EnvironmentConfig();
		environmentConfig.setAllowCreate(true);
		environment = new Environment(new File(dbDir), environmentConfig);
		return environment;
	}

	public static Database openDatabase(String databaseName){
		
		Database database = databases.get(databaseName);
		
		if( database!= null){
			return database;
		}
		
		DatabaseConfig databaseConfig = new DatabaseConfig();
		databaseConfig.setAllowCreate(true);
		database = environment.openDatabase(environment.getThreadTransaction(), databaseName, databaseConfig);
		databases.put(databaseName,database);
		
		return database;
	}

	public static Environment getEnvironment() {
		return environment;
	}

	public static void clear() {
		
		for( String databaseName : databases.keySet() ){
			databases.get(databaseName).close();
		}
		
		if( environment!= null ){
			environment.close();
		}
		
	}
	
}
