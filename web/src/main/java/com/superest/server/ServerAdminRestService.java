package com.superest.server;

import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("admin")
public class ServerAdminRestService {

	@PermitAll
	@Path("shutdownServer")
	@GET
	@POST
	public String  shutdownServer(){
		SuperRestServerContext.getUndertowJaxrsServer().stop();
		return "OK";
	}
	
	@PermitAll
	@Path("startServer")
	@GET
	@POST
	public String  startServer(){
		SuperRestServerContext.getUndertowJaxrsServer().start();
		return "OK";
	}
}
