package com.restcluster.superest.session;

import java.util.Iterator;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.impl.LogFactoryImpl;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;

import com.restcluster.superest.db.Neo4jDatabaseFactory;
import com.restcluster.superest.domain.Session;
import com.restcluster.superest.util.AESUtil;

public class SessionFatory{
	
	private static final Log log = LogFactoryImpl.getLog(SessionFatory.class);
	
	private static SessionFatory instance = new SessionFatory();
	
	private String sessionKey=null;
	
	private SessionFatory() {
	}
	
	public static SessionFatory getInstance(){
		return instance;
	}
	
	public  Node getSession(){
		
		Node node = null;
		GraphDatabaseService graphDatabaseService = Neo4jDatabaseFactory.getInstance().getDatabaseService();
		try (Transaction transaction = graphDatabaseService.beginTx()) {
			
			node = graphDatabaseService.createNode(Session.Session);
			node.setProperty(Session.SESSION_ID,node.getId());
			node.setProperty(Session.CREATE_TIME, System.currentTimeMillis());
			node.setProperty(Session.LASS_ACCESS_TIME, System.currentTimeMillis());
			try {
				node.setProperty(Session.SESSION_SIGN, AESUtil.encrypt(String.valueOf(node.getId()),sessionKey));
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			
			transaction.success();
		};
		
		return node;
	}
	
	public  Node getSession( String token ){
		
		if( StringUtils.isEmpty(token) ){
			log.warn("Token is empty");
			return null;
		}
		
		String sessionId = null;
		try {
			sessionId = AESUtil.decrypt(token,sessionKey);
		} catch (Exception e) {
			log.warn("Decrypt token error! token:"+token);
		}
		
		if( sessionId==null){
			return null;
		}
		
		GraphDatabaseService graphDatabaseService = Neo4jDatabaseFactory.getInstance().getDatabaseService();
		Node node = null;
		try (Transaction transaction = graphDatabaseService.beginTx()) {
			
			 node = graphDatabaseService.getNodeById(Long.parseLong(sessionId));
			 
			 Iterator<String> iterator = node.getPropertyKeys().iterator();
			 while( iterator.hasNext()){
				 node.getProperty(iterator.next());
			 }
			 
			transaction.success();
		};
		
		return node;
	}
	

	public String getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}
	
}
