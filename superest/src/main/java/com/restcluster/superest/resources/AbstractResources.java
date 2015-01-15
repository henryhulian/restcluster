package com.restcluster.superest.resources;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.graphdb.GraphDatabaseService;

import com.restcluster.superest.db.Neo4jDatabaseFactory;
import com.restcluster.superest.domain.Session;
import com.restcluster.superest.session.SessionFatory;
import com.restcluster.superest.threadlocal.ThreadLocalHolder;
import com.restcluster.superest.util.CookieUtil;
import com.restcluster.superest.util.IpUtil;
import com.restcluster.superest.util.TokenUtil;

public class AbstractResources {

	public GraphDatabaseService getDatabase(){
		return Neo4jDatabaseFactory.getInstance().getDatabaseService();
	}
	
	public ExecutionEngine getExecutionEngine(){
		return ThreadLocalHolder.getExecutionEngine();
	}
	
	public Session getSession(){
		return SessionFatory.getInstance().getSession();
	}
	
	public Session getSession( HttpServletRequest request , HttpServletResponse response){
		Session session =  SessionFatory.getInstance().getSession();
		session.setSessionIp(IpUtil.getIp(request));
		updateSession(session);
		CookieUtil.setCookie(response, TokenUtil.TOKEN_COOKIE_NMAE, session.getSessionSign(), request.getContextPath(), true, -1);
		return session;
	}

	public Session getSession( String token ){
		return SessionFatory.getInstance().getSession(token);
	}
	
	public void updateSession( Session session ){
		SessionFatory.getInstance().updateSession(session);
	}
}
