package com.restcluster.superest.session;

public class SessionService {

	public Session updateSession(Session session){
		SessionFatory.getSessionCache().put(session.getSessionId(), session);
		return session;
	}
	
	public Session getSession( String token ){
		return SessionFatory.getSession(token);
	}
	
}
