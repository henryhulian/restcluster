package com.superest.server;

import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import com.superest.db.DataBaseFactory;

@Path("admin")
public class ServerAdminRestService {

	@PermitAll
	@Path("shutdownServer")
	@GET
	@POST
	public String  shutdownServer(){
		DataBaseFactory.clear();
		SuperRestServerContext.getSuperRestServer().getUndertowJaxrsServer().stop();
		return "OK";
	}
	
	@PermitAll
	@Path("startServer")
	@GET
	@POST
	public String  startServer(){
		SuperRestServerContext.getSuperRestServer().getUndertowJaxrsServer().start();
		return "OK";
	}
}
