package com.restcluster.superest.server;

public class SuperRestServerContext {

	private static UndertowJaxrsServer undertowJaxrsServer;

	public static UndertowJaxrsServer getUndertowJaxrsServer() {
		return undertowJaxrsServer;
	}

	public static void setUndertowJaxrsServer(
			UndertowJaxrsServer undertowJaxrsServer) {
		SuperRestServerContext.undertowJaxrsServer = undertowJaxrsServer;
	}

}
