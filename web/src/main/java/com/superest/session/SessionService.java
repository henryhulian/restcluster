package com.superest.session;

public class SessionService {

	public Session updateSession(Session session){
		SessionFatory.getSessionCache().put(session.getSessionId(), session);
		return session;
	}
	
	public Session getSession( String sessionId ){
		return SessionFatory.getSessionCache().get(sessionId);
	}
}
