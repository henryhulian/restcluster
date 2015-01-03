package com.superest.service;

import com.superest.authtication.AuthenticationService;
import com.superest.authtication.Authenticatior;
import com.superest.authtication.Authorization;
import com.superest.session.SessionService;

public class ServiceFatory {
	
	private static SessionService sessionService;
	private static AuthenticationService authticationService;

	private ServiceFatory() {
	}
	
	public static final void init( Authenticatior authticatior , Authorization authorization){
		setSessionService(new SessionService());
		setAuthticationService(new AuthenticationService( authticatior , authorization ));
	}
	
	public static final void init(){
		setSessionService(new SessionService());
		setAuthticationService(new AuthenticationService());
	}

	public static SessionService getSessionService() {
		return sessionService;
	}

	public static void setSessionService(SessionService sessionService) {
		ServiceFatory.sessionService = sessionService;
	}

	public static AuthenticationService getAuthticationService() {
		return authticationService;
	}

	public static void setAuthticationService(AuthenticationService authticationService) {
		ServiceFatory.authticationService = authticationService;
	}
}
