package com.superest.test.resources.hello.resources;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.infinispan.Cache;

import com.superest.cache.CacheFatory;
import com.superest.session.Session;
import com.superest.session.SessionFatory;
import com.superest.test.resources.hello.data.UserBean;



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
		return map;
	}
	
	@PermitAll
	@Path("helloXml")
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public Map<String, String> getHelloXML(){
		return map;
	}
	
	@PermitAll
	@Path("userXml")
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public UserBean getUserXml(){
		UserBean user = new UserBean();
		user.username="sfsdf";
		return user;
	}
	
	@PermitAll
	@Path("userJson")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public UserBean getUserJson(){
		UserBean user = new UserBean();
		user.username="sfsdf";
		return user;
	}
	
	@PermitAll
	@Path("putCache")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String putCache(){
		
		Cache<String, String> cache = CacheFatory.getCache();
		cache.put("cachekey", String.valueOf(System.currentTimeMillis()));
		return (String)cache.get("cachekey");
	}
	
	@PermitAll
	@Path("getCache")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getCache(){
		
		Cache<String, String> cache = CacheFatory.getCache();
		return (String)cache.get("cachekey");
	}

	@PermitAll
	@Path("postsession")
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public Session getSession(){
		Session session = SessionFatory.createSession();
		session.put("key", "value");
		return session;
	}
	
	@PermitAll
	@Path("putsession")
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public Session putSession(@QueryParam("sessionId") String sessionId){
		SessionFatory.getSession(sessionId).put("key", "value");
		return SessionFatory.getSession(sessionId);
	}
	
	@PermitAll
	@Path("getsession")
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public Session getSession( @QueryParam("sessionId") String sessionId){
		return SessionFatory.getSession(sessionId);
	}
}
