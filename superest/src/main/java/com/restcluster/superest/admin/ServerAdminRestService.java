package com.restcluster.superest.admin;

import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import com.restcluster.superest.server.SuperRestServerContextSingleton;

@Path("admin")
public class ServerAdminRestService {

	@PermitAll
	@Path("shutdownServer")
	@GET
	@POST
	public String  shutdownServer(){
		SuperRestServerContextSingleton.getInstance().getUndertowJaxrsServer().stop();
		return "OK";
	}
	
	@PermitAll
	@Path("startServer")
	@GET
	@POST
	public String  startServer(){
		SuperRestServerContextSingleton.getInstance().getUndertowJaxrsServer().start();
		return "OK";
	}
}
