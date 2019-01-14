package com.test.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.alibaba.fastjson.annotation.JSONField;

@Entity
public class urlFilter {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// 解决了 SET IDENTITY_INSERT ON 该报错。
	// 是否进行插入和更新
	@Column(insertable = false)
	private Integer id;
	
	//拦截路径
	private String url;
	
	//拦截方法
	private String filter;
	
	private Integer px;
	
	private String czr;
	
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date czrq;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public Integer getPx() {
		return px;
	}

	public void setPx(Integer px) {
		this.px = px;
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

	public urlFilter(Integer id, String url, String filter, Integer px, String czr, Date czrq) {
		super();
		this.id = id;
		this.url = url;
		this.filter = filter;
		this.px = px;
		this.czr = czr;
		this.czrq = czrq;
	}

	public urlFilter() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
