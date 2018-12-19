package com.test.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.dao.mapper.main.userMapper;
import com.test.model.user;

@Service
public class userService {

	@Autowired
	private userMapper userDao;
	
	@Autowired
	private com.test.dao.mapper.secondary.secondaryMapper secondaryMapper;
	
	public int updatePasswordByUsername(String username, String password) {
		// TODO Auto-generated method stub
		return userDao.updatePasswordByUsername(username,password);
	}
	
	public user getByUsername(String username) {
		// TODO Auto-generated method stub
		return userDao.getByUsername(username);
	}

	public int rePassword(int id, String password) {
		// TODO Auto-generated method stub
		return userDao.rePassword(id,password);
	}

	public user getUserByTwoName(String truename, String username) {
		// TODO Auto-generated method stub
		return userDao.getUserByTwoName(truename,username);
	}

	public int newPerson(user user) {
		// TODO Auto-generated method stub
		return userDao.insert(user);
	}

	public ArrayList getCount0(String manager) {
		// TODO Auto-generated method stub
		return userDao.getCount0(manager);
	}

	public List mapManager0(String manager) {
		// TODO Auto-generated method stub
		return userDao.mapManager0(manager);
	}

	public int ShouQuan(int id,String newmanager) {
		// TODO Auto-generated method stub
		return userDao.ShouQuan(id,newmanager);
	}

	public List mapManager() {
		// TODO Auto-generated method stub
		return userDao.mapManager();
	}

	public user sss() {
		// TODO Auto-generated method stub
		return userDao.getByUsername("lll");
	}

	public int setSecondary() {
		// TODO Auto-generated method stub
		return secondaryMapper.setSecondary();
	}

	public List<Object> getSecondary() {
		// TODO Auto-generated method stub
		return secondaryMapper.getSecondary();
	}
}
