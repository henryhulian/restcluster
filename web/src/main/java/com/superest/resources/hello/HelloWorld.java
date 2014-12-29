package com.superest.resources.hello;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("helloWorld")
public class HelloWorld {

	@Path("hello")
	@GET
	public String getHello(){
		return "HELLO!";
	}
	
}
