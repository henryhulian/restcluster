package com.superest.resources;

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
	public void shutdownServer(){
		System.exit(0);
	}
}
