package com.superest.session;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Map;

public class UserSession implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3600879727090351823L;

	private String sessionId;
	
	private String sessionIp;
	
	private String sessionSign;
	
	private Timestamp createTime;
	
	private Timestamp lassAccessTime;
	
	private Map<String,String> properties;
	
	public void put( String key, String value){
		properties.put(key, value);
	}
	
	public String get( String key ){
		return properties.get(key);
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getSessionIp() {
		return sessionIp;
	}

	public void setSessionIp(String sessionIp) {
		this.sessionIp = sessionIp;
	}

	public String getSessionSign() {
		return sessionSign;
	}

	public void setSessionSign(String sessionSign) {
		this.sessionSign = sessionSign;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getLassAccessTime() {
		return lassAccessTime;
	}

	public void setLassAccessTime(Timestamp lassAccessTime) {
		this.lassAccessTime = lassAccessTime;
	}
	
}
