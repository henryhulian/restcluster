package com.superest.test.resources.hello.resources;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;

import com.restcluster.superest.server.SuperRestServerContextSingleton;
import com.superest.test.resources.hello.data.UserBean;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@Path("/userResources")
@Api(value = "/userResources")
public class UserResources {

	@POST
	@Path("/user")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	@PermitAll
	@ApiOperation(value="/user",consumes=MediaType.APPLICATION_JSON,produces=MediaType.TEXT_PLAIN,notes="创建会员")
	public String createUser( UserBean userBean){
		
		SuperRestServerContextSingleton context = SuperRestServerContextSingleton.getInstance();
		GraphDatabaseService databaseService = context.getDataBaseFactory().getGraphDatabase();
		
		try(Transaction transaction =  databaseService.beginTx()){
			Node node = databaseService.createNode();
			node.setProperty("userName", userBean.getUsername());
			transaction.success();
		};
		
		return "SUCCESS";
	}
}
