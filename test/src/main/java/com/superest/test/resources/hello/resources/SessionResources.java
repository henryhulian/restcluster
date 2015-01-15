package com.superest.test.resources.hello.resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.cypher.javacompat.ExecutionResult;
import com.restcluster.superest.domain.Session;
import com.restcluster.superest.resources.AbstractResources;
import com.restcluster.superest.util.DateUtil;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;


@Path("/sessionResources")
@Api(value = "/sessionResources",description="会话资源")
public class SessionResources extends AbstractResources{
	
	@Context
	private HttpServletRequest request;
	
	@Context
	private HttpServletResponse response;

	@HEAD
	@Path("/session")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	@PermitAll
	@ApiOperation(value = "创建会话", 
	consumes = MediaType.APPLICATION_FORM_URLENCODED, 
	produces = MediaType.TEXT_PLAIN, 
	notes = "创建会话")
	public String createSession( ) {

		super.getSession(request,response);

		return "SUCCESS";
	}
	
	
	@GET
	@Path("/user/{startTime}/{endTime}/{pageIndex}/{pageSize}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	@ApiOperation(value = "查询会员", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON, notes = "查询会话")
	public List<Map<String, Object>> findSession(
			@ApiParam(name="startTime",defaultValue="2015-01-01 00:00:00",required=true,value="startTime") 
			@PathParam("startTime") String startTime,
			
			@ApiParam(name="endTime",defaultValue="2018-01-01 00:00:00",required=true,value="结束时间") 
			@PathParam("endTime") String endTime,
			
			@ApiParam(name="pageIndex",defaultValue="0",required=true,value="分页索引") 
			@PathParam("pageIndex") Integer pageIndex,
			
			@ApiParam(name="pageSize",defaultValue="10",required=true,value="分页大小") 
			@PathParam("pageSize") Integer pageSize) {

		ExecutionEngine engine = super.getExecutionEngine();
		
		ExecutionResult executionResult = null;
		List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();

			String query = "match (n:"+Session.Session+")  "
					+ "where n."+Session.CREATE_TIME+">{startTime} "
					+ " and n."+Session.CREATE_TIME+"<{endTime} "
					+ "return "
					+ "n."+Session.SESSION_ID+" as "+Session.SESSION_ID+", "
					+ "n."+Session.SESSION_IP+" as "+Session.SESSION_IP+", "
					+ "n."+Session.SESSION_SIGN+" as "+Session.SESSION_SIGN+", "
					+ "n."+Session.USER_NAME+" as "+Session.USER_NAME+", "
					+ "n."+Session.CREATE_TIME+" as "+Session.CREATE_TIME+", "
					+ "n."+Session.LASS_ACCESS_TIME+" as "+Session.LASS_ACCESS_TIME+" "
					+ "order by n."+Session.CREATE_TIME+" desc"
					+ " skip {pageIndex} limit {pageSize}";
			
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
