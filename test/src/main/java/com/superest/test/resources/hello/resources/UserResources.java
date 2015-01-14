package com.superest.test.resources.hello.resources;

import javax.annotation.security.PermitAll;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

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
	@ApiOperation(value="/user",httpMethod="post",consumes="application/json")
	@PermitAll
	public String createUser( UserBean userBean){
		
		SuperRestServerContextSingleton context = SuperRestServerContextSingleton.getInstance();
		GraphDatabaseService databaseService = context.getDataBaseFactory().getGraphDatabase();
		
		Transaction transaction =  databaseService.beginTx();
		Node node = databaseService.createNode();
		node.setProperty("userName", userBean.getUsername());
		transaction.success();
		transaction.failure();
		
		return null;
	}
}
