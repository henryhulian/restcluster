package com.superest.util;

import javax.servlet.http.HttpServletRequest;

public class UserInfoUtil {
	
	public static final String USERNAME_ADDR="USERNAME";

	public static Object getUserName( HttpServletRequest request ){
		return request.getAttribute(USERNAME_ADDR);
	}
	
	public static void setUserName( HttpServletRequest request , String userName ){
		request.setAttribute(USERNAME_ADDR,userName);
	}
	
}
