package com.superest.test.resources.hello.data;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.restcluster.superest.util.DateAdapter;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
@ApiModel(value="会员实体")
public class UserBean{
	
	@XmlElement
	@ApiModelProperty(value="用户id",position=1)
	private Long id;
	
	@XmlElement
	@ApiModelProperty(value="用户名",position=2)
	private String username;
	
	@XmlElement
	@ApiModelProperty(value="密码",position=3)
	private String password;
	
	@XmlElement
	@ApiModelProperty(value="创建时间",position=4)
	@XmlJavaTypeAdapter(DateAdapter.class)
	private Date createTime = new Date(System.currentTimeMillis());

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