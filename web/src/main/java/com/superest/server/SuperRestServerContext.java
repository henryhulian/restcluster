package com.superest.server;

public class SuperRestServerContext {

	private static SuperRestServer superRestServer;

	public static SuperRestServer getSuperRestServer() {
		return superRestServer;
	}

	public static void setSuperRestServer(SuperRestServer superRestServer) {
		SuperRestServerContext.superRestServer = superRestServer;
	}
	
}
