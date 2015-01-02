package com.superest.authtication;

import com.superest.session.Session;
import com.superest.session.SessionFatory;

public class AuthticationService {

	private Authticatior authticatior;
	
	public AuthticationService() {
	}
	
	public AuthticationService( Authticatior authticatior) {
		this.authticatior=authticatior;
	}
	
	public Session authtication( String userName , String password ){
		
		if( !authticatior.authtication(userName, password)){
			return null;
		}
		
		return SessionFatory.createSession();
	}

	public Authticatior getAuthticatior() {
		return authticatior;
	}

	public void setAuthticatior(Authticatior authticatior) {
		this.authticatior = authticatior;
	}
	
	
}
