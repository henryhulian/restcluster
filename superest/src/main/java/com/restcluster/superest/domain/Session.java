package com.restcluster.superest.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.Label;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class Session  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3600879727090351823L;
	
	public static final Label Session = DynamicLabel.label("Session");
	
	public static final String ID="id";
	
	public static final String USER_NAME="username";

	public static final String SESSION_ID="sessionId";
	
	public static final String SESSION_IP="sessionIp";
	
	public static final String SESSION_SIGN="sessionSign";
	
	public static final String CREATE_TIME="createTime";
	
	public static final String LASS_ACCESS_TIME="lassAccessTime";
	
	private Long id;
	
	@XmlElement(nillable=false)
	private String userName;

	@XmlElement(nillable=false)
	private String sessionId="";
	
	@XmlElement(nillable=false)
	private String sessionIp="";
	
	@XmlElement(nillable=false)
	private String sessionSign="";
	
	@XmlElement(nillable=false)
	private Timestamp createTime=new Timestamp(System.currentTimeMillis());
	
	@XmlElement(nillable=false)
	private Timestamp lassAccessTime=new Timestamp(System.currentTimeMillis());
	

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
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	

}
