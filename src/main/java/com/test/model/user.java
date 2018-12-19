package com.test.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONField;

//@Table(name = "SPRINGBOOT_USER")
@Entity
public class user {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// 解决了 SET IDENTITY_INSERT ON 该报错。
	// 是否进行插入和更新
	@Column(insertable = false)
	private Integer id;

	private String truename;
	
	private String manager;	// 0 普通用户（无权登录）	1 管理员（可登录，可对0授权登录）	2 暂时最高级的
	
	private String username;

	//transient无法被序列化，也就无法持久化，查询查不到，数据安全
	//private transient String password;
	//加上该注解，在数据序列化时，该字段数据无法被序列化。
	@JSONField(serialize=false)
	private String password;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTruename() {
		return truename;
	}

	public void setTruename(String truename) {
		this.truename = truename;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

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

	public user(Integer id, String truename, String manager, String username, String password) {
		super();
		this.id = id;
		this.truename = truename;
		this.manager = manager;
		this.username = username;
		this.password = password;
	}

	public user() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
