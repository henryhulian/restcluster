package com.superest.db;

import java.io.File;

import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;

public class DataBaseFactory {
	
	private static Environment environment=null;
	

	private DataBaseFactory() {
	}
	
	public static  Environment init( String dbDir ){
		EnvironmentConfig environmentConfig = new EnvironmentConfig();
		environmentConfig.setAllowCreate(true);
		environment = new Environment(new File(dbDir), environmentConfig);
		return environment;
	}

	public static Database openDatabase(String databaseName){
		DatabaseConfig databaseConfig = new DatabaseConfig();
		databaseConfig.setAllowCreate(true);
		Database database = environment.openDatabase(environment.getThreadTransaction(), databaseName, databaseConfig);
		return database;
	}

	public static Environment getEnvironment() {
		return environment;
	}
	
}
