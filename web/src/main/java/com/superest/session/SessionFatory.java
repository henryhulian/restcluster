package com.superest.session;

import org.infinispan.Cache;

import com.superest.cache.CacheFatory;
import com.superest.util.DigestUtil;
import com.superest.util.TokenUtil;

public class SessionFatory {
	
	private static final String SERVER_SIGN="&#*($hkhkjhfHSDFKH7979872";
	private static final String SESSION_CACHE_NAME="session";
	
	private static Cache<String, Session> sessionCache;

	private SessionFatory() {
	}
	
	public static void init(){
		sessionCache = CacheFatory.getCache(SESSION_CACHE_NAME);
	}
	
	public static Session createSession(){
		Session session = new Session();
		session.setSessionId(TokenUtil.createRandomToken());
		session.setSessionSign(DigestUtil.sha256_base64(session.getSessionId()+SERVER_SIGN));
		sessionCache.put(session.getSessionId(), session);
		return session;
	}
	
	public static Session pudateSession(Session session){
		sessionCache.put(session.getSessionId(), session);
		return session;
	}
	
	public static Session getSession( String sessionId ){
		return sessionCache.get(sessionId);
	}
}
