package com.restcluster.superest.session;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.impl.LogFactoryImpl;
import org.infinispan.Cache;

import com.restcluster.superest.cache.CacheFatory;
import com.restcluster.superest.util.AESUtil;
import com.restcluster.superest.util.TokenUtil;

public class SessionFatory {
	
	private static final Log log = LogFactoryImpl.getLog(SessionFatory.class);
	
	private static String SERVER_SIGN=null;
	private static final String SESSION_CACHE_NAME="session";
	
	private CacheFatory cacheFatory = null;
	private Cache<String, Session> sessionCache = null;

	public SessionFatory(CacheFatory cacheFatory) {
		sessionCache=cacheFatory.getCache(SESSION_CACHE_NAME);
	}
	
	public  Session getSession() throws Exception{
		Session session = new Session();
		session.setSessionId(TokenUtil.createRandomToken());
		session.setSessionSign(AESUtil.encrypt(session.getSessionId(),SERVER_SIGN));
		sessionCache.put(session.getSessionId(), session);
		return session;
	}
	
	public  Session getSession( String token ){
		
		if( StringUtils.isEmpty(token) ){
			log.warn("Token is empty");
			return null;
		}
		
		String sessionId = null;
		try {
			sessionId = AESUtil.decrypt(token,SERVER_SIGN);
		} catch (Exception e) {
			log.warn("Decrypt token error! token:"+token);
		}
		
		if( sessionId==null){
			return null;
		}
		
		return sessionCache.get(sessionId);
	}

	public CacheFatory getCacheFatory() {
		return cacheFatory;
	}

	public void setCacheFatory(CacheFatory cacheFatory) {
		this.cacheFatory = cacheFatory;
	}
	
}
