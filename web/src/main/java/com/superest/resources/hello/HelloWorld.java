package com.superest.resources.hello;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

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
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, String> getHelloJson(){
		return map;
	}
	
	@Path("userJson")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public UserBean getUserJson(){
		return new UserBean();
	}
	
	@XmlRootElement
	class UserBean{
		@XmlElement
		private String username;
	}
	
}
