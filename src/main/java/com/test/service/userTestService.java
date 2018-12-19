package com.test.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.test.dao.mapper.main.userTestMapper;
import com.test.model.userTest;

@Service
public class userTestService {

	@Autowired
	private userTestMapper userDao;
	
	public List<userTest> getAll() {
		// TODO Auto-generated method stub
		return userDao.selectAll();
	}

	//@CachePut(value="mapPerson", key="#p0.name")
	public int add(userTest userTest) throws Exception{
		// TODO Auto-generated method stub
		int i = userDao.insert(userTest);
		return i;
	}
	
	public int update(userTest userTest) {
		// TODO Auto-generated method stub
		return userDao.updateByPrimaryKey(userTest);
	}

	public int delete(int id) {
		// TODO Auto-generated method stub
		return userDao.deleteByPrimaryKey(id);
	}

	public userTest getPerson(int id) {
		// TODO Auto-generated method stub
		return userDao.getPerson(id);
		// 在resources包下的META-INF下，创建spring-devtools.properties文件
		// 文件中写restart.include.companycommonlibs=tk/mybatis.*
		// 即可解决selectByPrimaryKey报错 实体A无法转换类型为实体A 
		/*return userDao.selectByPrimaryKey(id);*/
	}

	@SuppressWarnings("rawtypes")
	public List getPieList() {
		// TODO Auto-generated method stub
		return userDao.getPieList();
		
	}

	public List getMapBySex(int sex) {
		// TODO Auto-generated method stub
		return userDao.getMapBySex(sex);
	}

}
