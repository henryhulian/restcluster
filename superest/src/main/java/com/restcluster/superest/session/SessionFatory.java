package com.restcluster.superest.session;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.impl.LogFactoryImpl;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;

import com.restcluster.superest.db.Neo4jDatabaseFactory;
import com.restcluster.superest.domain.Labels;
import com.restcluster.superest.domain.Session;
import com.restcluster.superest.util.AESUtil;
import com.restcluster.superest.util.TokenUtil;

public class SessionFatory{
	
	private static final Log log = LogFactoryImpl.getLog(SessionFatory.class);
	
	private static SessionFatory instance = new SessionFatory();
	
	private String sessionKey=null;
	
	private SessionFatory() {
	}
	
	public static SessionFatory getInstance(){
		return instance;
	}
	
	public  Session getSession(){
		
		Session session = new Session();
		session.setSessionId(TokenUtil.createRandomToken());
		try {
			session.setSessionSign(AESUtil.encrypt(session.getSessionId(),sessionKey));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		GraphDatabaseService graphDatabaseService = Neo4jDatabaseFactory.getInstance().getDatabaseService();
		try (Transaction transaction = graphDatabaseService.beginTx()) {
			
			Node node = graphDatabaseService.createNode(Labels.Session);
			session.convertToNode(node);
			
			transaction.success();
		};
		
		return session;
	}
	
	public  Session getSession( String token ){
		
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
		Session session = new Session();
		try (Transaction transaction = graphDatabaseService.beginTx()) {
			
			Node node = graphDatabaseService.findNodesByLabelAndProperty(Labels.Session, "sessionId", sessionId).iterator().next();
			
			session.convertToSession(node);
			
			transaction.success();
		};
		
		return session;
	}
	
	public  void updateSession( Session session ){
		
		GraphDatabaseService graphDatabaseService = Neo4jDatabaseFactory.getInstance().getDatabaseService();
		try (Transaction transaction = graphDatabaseService.beginTx()) {
			
			Node node = graphDatabaseService.findNodesByLabelAndProperty(Labels.Session, "sessionId", session.getSessionId()).iterator().next();
			
			session.convertToNode(node);
			
			transaction.success();
		};
		
	}


	public String getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}
	
}
