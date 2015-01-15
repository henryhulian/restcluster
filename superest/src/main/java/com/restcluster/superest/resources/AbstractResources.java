package com.restcluster.superest.resources;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;

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
	
	public Node getSession(){
		return SessionFatory.getInstance().getSession();
	}
	
	public Node getSession( HttpServletRequest request , HttpServletResponse response){
		Node session =  SessionFatory.getInstance().getSession();
		
		try( Transaction transaction =  getDatabase().beginTx()){
			session.setProperty(Session.SESSION_IP, IpUtil.getIp(request));
			CookieUtil.setCookie(response, TokenUtil.TOKEN_COOKIE_NMAE, (String)session.getProperty(Session.SESSION_SIGN), request.getContextPath(), true, -1);
			transaction.success();
		}
		
		return session;
	}

	public Node getSession( String token ){
		return SessionFatory.getInstance().getSession(token);
	}
	
}
