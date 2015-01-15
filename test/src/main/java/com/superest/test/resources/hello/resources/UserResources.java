package com.superest.test.resources.hello.resources;

import java.util.ArrayList;
import java.util.HashMap;
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
import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import com.superest.test.resources.hello.data.Labels;
import com.superest.test.resources.hello.data.UserBean;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

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
			@ApiParam(name = "userName", defaultValue = "test001", required = true, value = "用户名")
			@FormParam("userName") String userName,

			@ApiParam(name = "password", defaultValue = "111111", required = true, value = "密码")
			@FormParam("password") String password
			) {

		GraphDatabaseService databaseService = super.getDatabase();

		try (Transaction transaction = databaseService.beginTx()) {
			Node node = databaseService.createNode(Labels.User);
			node.setProperty("userName", userName);
			node.setProperty("password", password);
			node.setProperty("createTime",System.currentTimeMillis());
			transaction.success();
		}
		;

		return "SUCCESS";
	}

	@PUT
	@Path("/user")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	@PermitAll
	@ApiOperation(value = "修改会员", consumes = MediaType.APPLICATION_JSON, produces = MediaType.TEXT_PLAIN, notes = "修改会员")
	public String updateUser(UserBean userBean) {

		GraphDatabaseService databaseService = super.getDatabase();

		try (Transaction transaction = databaseService.beginTx()) {
			Node node = databaseService.getNodeById(userBean.getId());
			node.setProperty("password", userBean.getPassword());
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
	public List<Map<String, Object>> findUser(
			@ApiParam(name = "startTime", defaultValue = "2015-01-01 00:00:00", required = true, value = "startTime") @PathParam("startTime") String startTime,

			@ApiParam(name = "endTime", defaultValue = "2018-01-01 00:00:00", required = true, value = "结束时间") @PathParam("endTime") String endTime,

			@ApiParam(name = "pageIndex", defaultValue = "0", required = true, value = "分页索引") @PathParam("pageIndex") Integer pageIndex,

			@ApiParam(name = "pageSize", defaultValue = "10", required = true, value = "分页大小") @PathParam("pageSize") Integer pageSize) {

		GraphDatabaseService databaseService = super.getDatabase();
		ExecutionEngine engine = super.getExecutionEngine();

		ExecutionResult executionResult = null;
		List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
		try (Transaction transaction = databaseService.beginTx()) {

			String query = "match (n)  "
					+ "where n.createTime>{startTime} "
					+ " and n.createTime<{endTime} "
					+ "return id(n) AS id,n.userName AS userName,n.password AS password,n.createTime AS createTime "
					+ "order by n.createTime desc skip {pageIndex} limit {pageSize}";

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("startTime", DateUtil.pasreString(startTime).getTime());
			params.put("endTime", DateUtil.pasreString(endTime).getTime());
			params.put("pageIndex", pageIndex * pageSize);
			params.put("pageSize", pageSize);

			executionResult = engine.execute(query, params);

			for (Map<String, Object> row : executionResult) {
				rows.add(row);
			}

			transaction.success();
		}
		;

		return rows;
	}
}
