package com.test.dao.mapper.main;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.test.config.MyMapper;
import com.test.model.user;
import com.test.model.userTest;

// @Mapper与启动类中的@MapperScan("com.test.dao.mapper")写一个即可
// 但是，若不用@MapperScan("com.test.dao.mapper")，则需要在每个mapper接口中添加@Mapper注解
public interface userMapper extends MyMapper<user>{

	int updatePasswordByUsername(String username, String newpassword);

	user getByUsername(String username);

	int rePassword(int id, String password);

	user getUserByTwoName(String truename, String username);

	ArrayList getCount0(String manager);

	List mapManager0(String manager);

	int ShouQuan(int id, String newmanager);

	List mapManager();
	
}
