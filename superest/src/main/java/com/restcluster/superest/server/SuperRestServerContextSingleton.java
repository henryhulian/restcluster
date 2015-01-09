package com.restcluster.superest.server;

import com.restcluster.superest.authtication.AuthenticationService;
import com.restcluster.superest.session.SessionFatory;

public class SuperRestServerContextSingleton {

	public static final String SERVER_CONTEXT = "SERVER_CONTEXT";

	private static SuperRestServerContextSingleton instance = new SuperRestServerContextSingleton();
	
	private SessionFatory sessionFatory = null;
	
	private UndertowJaxrsServer undertowJaxrsServer = null;
	private UndertowJaxrsServer adminUndertowJaxrsServer = null;
	
	private AuthenticationService authenticationService = null;
	
	private SuperRestServerContextSingleton() {
	}

	public static SuperRestServerContextSingleton getInstance(){
		return instance;
	}

	public SessionFatory getSessionFatory() {
		return sessionFatory;
	}

	public void setSessionFatory(SessionFatory sessionFatory) {
		this.sessionFatory = sessionFatory;
	}

	public UndertowJaxrsServer getUndertowJaxrsServer() {
		return undertowJaxrsServer;
	}

	public void setUndertowJaxrsServer(UndertowJaxrsServer undertowJaxrsServer) {
		this.undertowJaxrsServer = undertowJaxrsServer;
	}

	public UndertowJaxrsServer getAdminUndertowJaxrsServer() {
		return adminUndertowJaxrsServer;
	}

	public void setAdminUndertowJaxrsServer(
			UndertowJaxrsServer adminUndertowJaxrsServer) {
		this.adminUndertowJaxrsServer = adminUndertowJaxrsServer;
	}

	public AuthenticationService getAuthenticationService() {
		return authenticationService;
	}

	public void setAuthenticationService(AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}

}
