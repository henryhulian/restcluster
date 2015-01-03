package com.superest.session;

import org.infinispan.Cache;

import com.superest.cache.CacheFatory;
import com.superest.util.AESUtil;
import com.superest.util.TokenUtil;

public class SessionFatory {
	
	private static String SERVER_SIGN="wXf;7-*!i)&d7TCM";
	private static final String SESSION_CACHE_NAME="session";
	
	private static Cache<String, Session> sessionCache;

	private SessionFatory() {
	}
	
	public static void init( String sessionKey ){
		SERVER_SIGN=sessionKey;
		sessionCache = CacheFatory.getCache(SESSION_CACHE_NAME);
	}
	
	public static Session createSession() throws Exception{
		Session session = new Session();
		session.setSessionId(TokenUtil.createRandomToken());
		session.setSessionSign(AESUtil.encrypt(session.getSessionId(),SERVER_SIGN));
		sessionCache.put(session.getSessionId(), session);
		return session;
	}
	
	public static Session getSession( String token ){
		String sessionId = null;
		try {
			sessionId = AESUtil.decrypt(token,SERVER_SIGN);
		} catch (Exception e) {
			System.out.println("Decrypt token error! token:"+token);
		}
		if( sessionId==null ){
			return null;
		}
		return sessionCache.get(sessionId);
	}

	public static Cache<String, Session> getSessionCache() {
		return sessionCache;
	}

	public static void setSessionCache(Cache<String, Session> sessionCache) {
		SessionFatory.sessionCache = sessionCache;
	}
	
}
