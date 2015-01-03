package com.superest.authtication;

import com.superest.session.Session;
import com.superest.session.SessionFatory;

public class AuthenticationService {

	private Authenticatior authticatior;
	private Authorization authorization;
	
	public AuthenticationService() {
	}
	
	public AuthenticationService( Authenticatior authticatior , Authorization authorization ) {
		this.authticatior=authticatior;
		this.authorization=authorization;
	}
	
	public Session authtication( String userName , String password ){
		
		if( !authticatior.authtication(userName, password)){
			return null;
		}
		
		Session session = null;
		
		try {
			session=SessionFatory.createSession();
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
	
	
}