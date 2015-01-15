package com.superest.test.resources.hello.domain;

import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.Label;

public class Role {
	
	public static final Label Role = DynamicLabel.label("Role");
	
	public static final String ROLE_USER="user";
	

	private String roleName;
	public static final String ROLE_NAME="ROLE_NAME";
	
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	
}
