package com.superest.resources.hello;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("helloWorld")
public class HelloWorld {
	
	static Map<String, String>map = new HashMap<String, String>();
	static{
		map.put("hello","111111111");
		map.put("hello","111111111");
		map.put("hello","111111111");
		map.put("hello","111111111");
	}
	
	@Path("hello")
	@GET
	public String getHello(){
		return "hello";
	}

	@Path("helloJson")
	@GET
	@Produces("application/xml")
	public Map<String, String> getHelloJson(){
		return map;
	}
	
}
