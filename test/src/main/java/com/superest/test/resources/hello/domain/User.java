package com.superest.test.resources.hello.domain;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.Label;

import com.restcluster.superest.util.DateAdapter;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@ApiModel(value="会员实体")
public class User{
	
	public static Label User = DynamicLabel.label("User");
	
	public static final String RELATIONSHIP_HAS="HAS";
	
	@XmlElement
	@ApiModelProperty(value="用户id",position=1)
	private Long id;
	public static final String ID="id";
	
	@XmlElement
	@ApiModelProperty(value="用户名",position=2)
	private String username;
	public static final String USER_NAME="username";
	
	@XmlElement
	@ApiModelProperty(value="密码",position=3)
	private String password;
	public static final String PASSWORD="password";
	
	@XmlElement
	@ApiModelProperty(value="创建时间",position=4)
	@XmlJavaTypeAdapter(DateAdapter.class)
	private Date createTime = new Date(System.currentTimeMillis());
	public static final String CREATE_TIME="createTime";

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
}