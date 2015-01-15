package com.restcluster.superest.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.neo4j.graphdb.Node;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class Session implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3600879727090351823L;
	
	private Long id;
	
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

	public Node convertToNode( Node node ){
		
		node.setProperty("userName",this.getUserName());
		node.setProperty("sessionId",this.getSessionId());
		node.setProperty("sessionIp",this.getSessionIp());
		node.setProperty("sessionSign",this.getSessionSign());
		node.setProperty("createTime",this.getCreateTime().getTime());
		node.setProperty("lassAccessTime",this.getLassAccessTime().getTime());
		
		return node;
	}
	
	public Session convertToSession( Node node ){

		this.setId((long)node.getId());
		this.setUserName((String)node.getProperty("userName"));
		this.setSessionId((String)node.getProperty("sessionId"));
		this.setSessionIp((String)node.getProperty("sessionIp"));
		this.setSessionSign((String)node.getProperty("sessionSign"));
		this.setCreateTime(new Timestamp((long)node.getProperty("createTime")));
		this.setLassAccessTime(new Timestamp((long)node.getProperty("lassAccessTime")));
		
		return this;
	}
}
