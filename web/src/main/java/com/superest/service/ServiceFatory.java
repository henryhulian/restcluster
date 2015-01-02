package com.superest.service;

import com.superest.authtication.AuthticationService;
import com.superest.authtication.Authticatior;
import com.superest.session.SessionService;

public class ServiceFatory {
	
	private static SessionService sessionService;
	private static AuthticationService authticationService;

	private ServiceFatory() {
	}
	
	public static final void init( Authticatior authticatior){
		setSessionService(new SessionService());
		setAuthticationService(new AuthticationService( authticatior ));
	}
	
	public static final void init(){
		setSessionService(new SessionService());
		setAuthticationService(new AuthticationService());
	}

	public static SessionService getSessionService() {
		return sessionService;
	}

	public static void setSessionService(SessionService sessionService) {
		ServiceFatory.sessionService = sessionService;
	}

	public static AuthticationService getAuthticationService() {
		return authticationService;
	}

	public static void setAuthticationService(AuthticationService authticationService) {
		ServiceFatory.authticationService = authticationService;
	}
}
