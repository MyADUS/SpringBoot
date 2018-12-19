package com.test.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONField;

//@Table(name = "SPRINGBOOT_USERTEST")
@Entity
public class userTest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// 解决了 SET IDENTITY_INSERT ON 该报错。
	// 是否进行插入和更新
	@Column(insertable = false)
	private Integer id;

	private String name;

	private Integer age; // 0 女 1 男

	private Integer sex;

	private Date czrq;
	
	//加上该注解，保证从数据库查询数据时，日期类型展示为这样
	@JSONField(format="yyyy-MM-dd")
	private Date birth;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Date getCzrq() {
		return czrq;
	}

	public void setCzrq(Date czrq) {
		this.czrq = czrq;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public userTest(Integer id, String name, Integer age, Integer sex, Date czrq, Date birth) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.sex = sex;
		this.czrq = czrq;
		this.birth = birth;
	}

	public userTest() {
		super();
		// TODO Auto-generated constructor stub
	}

}
