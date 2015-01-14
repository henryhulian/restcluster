package com.superest.test.resources.hello.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;

import com.superest.test.resources.hello.data.UserBean;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@Path("/userResources")
@Api(value = "/userResources")
public class UserResources extends DatabaseAware{

	@POST
	@Path("/user")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	@PermitAll
	@ApiOperation(value="/user",consumes=MediaType.APPLICATION_JSON,produces=MediaType.TEXT_PLAIN,notes="创建会员")
	public String createUser( UserBean userBean){
		
		GraphDatabaseService databaseService = super.getDatabase();
			
		try(Transaction transaction =  databaseService.beginTx()){
			Node node = databaseService.createNode();
			node.setProperty("userName", userBean.getUsername());
			transaction.success();
		};
		
		return "SUCCESS";
	}
	
	@GET
	@Path("/user")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	@ApiOperation(value="/user",consumes=MediaType.APPLICATION_JSON,produces=MediaType.APPLICATION_JSON,notes="查询会员列表")
	public List<String> findUser(){
		
		GraphDatabaseService databaseService = super.getDatabase();
		ExecutionEngine engine = new ExecutionEngine( databaseService );
		
		ExecutionResult executionResult = null;
		List<String> rows= new ArrayList<>();
		try(Transaction transaction =  databaseService.beginTx()){
			
			executionResult = engine.execute("match (n) return n.userName ");
			for ( Map<String, Object> row : executionResult )
			{
			    for ( Entry<String, Object> column : row.entrySet() )
			    {
			    	rows.add((String)column.getValue());
			    }
			}
			
			transaction.success();
		};
		
		return rows;
	}
}
