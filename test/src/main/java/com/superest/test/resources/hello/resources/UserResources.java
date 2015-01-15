package com.superest.test.resources.hello.resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.restcluster.superest.resources.AbstractResources;
import com.restcluster.superest.util.DateUtil;
import com.restcluster.superest.util.DigestUtil;

import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;

import com.superest.test.resources.hello.domain.RelationType;
import com.superest.test.resources.hello.domain.Role;
import com.superest.test.resources.hello.domain.User;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

@Path("/userResources")
@Api(value = "/userResources", description = "会员资源")
public class UserResources extends AbstractResources {

	@POST
	@Path("/user")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	@PermitAll
	@ApiOperation(value = "创建会员", consumes = MediaType.APPLICATION_FORM_URLENCODED, produces = MediaType.TEXT_PLAIN, notes = "创建会员")
	public String createUser(
			@ApiParam(name = User.USER_NAME, defaultValue = "test001", required = true, value = "用户名") @FormParam(User.USER_NAME) String userName,

			@ApiParam(name = User.PASSWORD, defaultValue = "111111", required = true, value = "密码") @FormParam(User.PASSWORD) String password) {

		GraphDatabaseService databaseService = super.getDatabase();

		try (Transaction transaction = databaseService.beginTx()) {
			Node node = databaseService.createNode(User.User);
			node.setProperty(User.USER_NAME, userName);
			node.setProperty(User.PASSWORD, DigestUtil.sha256_base64(password));
			node.setProperty(User.CREATE_TIME, System.currentTimeMillis());
			
			Node role = getOrCreateRole(Role.ROLE_USER);
			node.createRelationshipTo(role, RelationType.HAS);
			
			transaction.success();
		}
		;

		return "SUCCESS";
	}

	private Node getOrCreateRole(String roleName ) {
		Iterator<Node> iterator = super.getDatabase().findNodesByLabelAndProperty(Role.Role, Role.ROLE_NAME, "user").iterator();
		Node role = null;
		if( iterator.hasNext() ){
			role=iterator.next();
		}else{
			role = super.getDatabase().createNode(Role.Role);
			role.setProperty(Role.ROLE_NAME, "roleName");
		}
		return role;
	}

	@PUT
	@Path("/user")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	@ApiOperation(value = "修改会员", consumes = MediaType.APPLICATION_FORM_URLENCODED, produces = MediaType.TEXT_PLAIN, notes = "修改会员")
	public String updateUser(@FormParam("id") Long id,
			@FormParam(User.PASSWORD) String password) {

		GraphDatabaseService databaseService = super.getDatabase();

		try (Transaction transaction = databaseService.beginTx()) {
			Node node = databaseService.getNodeById(id);
			node.setProperty(User.PASSWORD, password);
			transaction.success();
		}
		;

		return "SUCCESS";
	}

	@GET
	@Path("/user/{startTime}/{endTime}/{pageIndex}/{pageSize}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	@ApiOperation(value = "查询会员", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON, notes = "查询会员")
	@ApiResponses({ @ApiResponse(code = 200, message = "返回查询结果", response = User.class) })
	public List<Map<String, Object>> findUser(
			@ApiParam(name = "startTime", defaultValue = "2015-01-01 00:00:00", required = true, value = "startTime") @PathParam("startTime") String startTime,

			@ApiParam(name = "endTime", defaultValue = "2018-01-01 00:00:00", required = true, value = "结束时间") @PathParam("endTime") String endTime,

			@ApiParam(name = "pageIndex", defaultValue = "0", required = true, value = "分页索引") @PathParam("pageIndex") Integer pageIndex,

			@ApiParam(name = "pageSize", defaultValue = "10", required = true, value = "分页大小") @PathParam("pageSize") Integer pageSize) {

		ExecutionEngine engine = super.getExecutionEngine();

		ExecutionResult executionResult = null;
		List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();

		String query = "match (n:" + User.User + ")  " + "where n."
				+ User.CREATE_TIME + ">{startTime} " + " and n."
				+ User.CREATE_TIME + "<{endTime} " + "return id(n) AS "
				+ User.ID + ",n." + User.USER_NAME + " AS "
				+ User.USER_NAME + ",n." + User.PASSWORD + " AS "
				+ User.PASSWORD + ",n." + User.CREATE_TIME + " AS "
				+ User.CREATE_TIME + " " + "order by n."
				+ User.CREATE_TIME
				+ " desc skip {pageIndex} limit {pageSize}";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("startTime", DateUtil.pasreString(startTime).getTime());
		params.put("endTime", DateUtil.pasreString(endTime).getTime());
		params.put("pageIndex", pageIndex * pageSize);
		params.put("pageSize", pageSize);

		executionResult = engine.execute(query, params);

		for (Map<String, Object> row : executionResult) {
			rows.add(row);
		}

		return rows;
	}
}
