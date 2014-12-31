package com.superest.test.resources.hello.resources;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import com.superest.test.resources.hello.*;



@Path("helloWorld")
public class HelloWorld {
	
	@Context
	private HttpServletRequest request;
	
	static Map<String, String>map = new HashMap<String, String>();
	static{
		map.put("hello1","111111111");
		map.put("hello2","111111111");
		map.put("hello3","111111111");
		map.put("hello4","111111111");
	}
	

	@PermitAll
	@Path("helloJson")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, String> getHelloJson(){
		System.exit(0);
		return map;
	}
	
	@Path("userXml")
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public UserBean getUserJson(){
		UserBean user = new UserBean();
		user.username="sfsdf";
		return user;
	}

	@Path("session")
	@GET
	@Produces(MediaType.APPLICATION_XML)
	@RolesAllowed("test")
	public UserBean getSession(){
		request.getSession().setAttribute("dfdf", "2222");
		System.out.println();
		UserBean user = new UserBean();
		user.username="sfsdf";
		return user;
	}
}
