package com.superest.authtication;

import javax.servlet.http.HttpServletResponse;

import com.superest.session.Session;
import com.superest.session.SessionFatory;
import com.superest.util.CookieUtil;

public class AuthticationService {

	private Authticatior authticatior;
	
	public AuthticationService() {
	}
	
	public AuthticationService( Authticatior authticatior) {
		this.authticatior=authticatior;
	}
	
	public boolean authtication( String userName , String password , HttpServletResponse response){
		
		if( !authticatior.authtication(userName, password)){
			return false;
		}
		
		Session session = SessionFatory.createSession();
		CookieUtil.setCookie(response, "TOKEN", session.getSessionId(), "/", true,10000);
		
		return true;
	}

	public Authticatior getAuthticatior() {
		return authticatior;
	}

	public void setAuthticatior(Authticatior authticatior) {
		this.authticatior = authticatior;
	}
	
	
}
