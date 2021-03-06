package com.restcluster.superest.authtication;

import org.neo4j.graphdb.Node;

import com.restcluster.superest.session.SessionFatory;

public class AuthenticationService {

	private SessionFatory sessionFatory;
	private Authenticatior authticatior;
	private Authorization authorization;
	
	public AuthenticationService() {
	}
	
	public AuthenticationService( Authenticatior authticatior , Authorization authorization ) {
		this.authticatior=authticatior;
		this.authorization=authorization;
	}
	
	public Node authtication( String userName , String password ){
		
		if( !authticatior.authtication(userName, password)){
			return null;
		}
		
		Node session = null;
		
		try {
			session=SessionFatory.getInstance().getSession();
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return session;
	}

	public Authenticatior getAuthticatior() {
		return authticatior;
	}

	public void setAuthticatior(Authenticatior authticatior) {
		this.authticatior = authticatior;
	}

	public Authorization getAuthorization() {
		return authorization;
	}

	public void setAuthorization(Authorization authorization) {
		this.authorization = authorization;
	}

	public SessionFatory getSessionFatory() {
		return sessionFatory;
	}

	public void setSessionFatory(SessionFatory sessionFatory) {
		this.sessionFatory = sessionFatory;
	}
	
	
}
