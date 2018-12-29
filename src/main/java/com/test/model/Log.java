package com.test.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.alibaba.fastjson.annotation.JSONField;

@Entity
public class Log {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// 解决了 SET IDENTITY_INSERT ON 该报错。
	// 是否进行插入和更新
	@Column(insertable = false)
	private Integer id;
	
	//使用方法的ip地址
	private String ip;
	
	//分别是否为登录操作  0 不是 1 是
	private String islogin;
	
	//调用方法
	private String method;
	
	private String czr;
	
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date czrq;

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getIslogin() {
		return islogin;
	}

	public void setIslogin(String islogin) {
		this.islogin = islogin;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getCzr() {
		return czr;
	}

	public void setCzr(String czr) {
		this.czr = czr;
	}

	public Date getCzrq() {
		return czrq;
	}

	public void setCzrq(Date czrq) {
		this.czrq = czrq;
	}

	public Log(Integer id, String ip, String islogin, String method, String czr, Date czrq) {
		super();
		this.id = id;
		this.ip = ip;
		this.islogin = islogin;
		this.method = method;
		this.czr = czr;
		this.czrq = czrq;
	}

	public Log() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
