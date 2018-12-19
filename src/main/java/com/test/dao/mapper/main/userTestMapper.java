package com.test.dao.mapper.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.test.config.MyMapper;
import com.test.model.userTest;

// @Mapper与启动类中的@MapperScan("com.test.dao.mapper")写一个即可
// 但是，若不用@MapperScan("com.test.dao.mapper")，则需要在每个mapper接口中添加@Mapper注解
public interface userTestMapper extends MyMapper<userTest>{

	//测试Mapper.xml
	userTest getPerson(int id);

	List getPieList();

	List getMapBySex(int sex);

	//@Select("SELECT * FROM SPRINGBOOT_USERTEST ")
	//ArrayList<userTest> getAll();

	/*@Select("SELECT * FROM SPRINGBOOT_USERTEST WHERE ID = #{id}")
	userTest getById(int id);*/

	/*@Insert("INSERT INTO SPRINGBOOT_USERTEST (NAME,AGE) VALUES (#{name},#{id})")
	int add(String name, int age);*/

	/*@Update("UPDATE SPRINGBOOT_USERTEST SET AGE = 99 WHERE NAME = #{name}")
	int update(String name);*/

	/*@Delete("DELETE FROM SPRINGBOOT_USERTEST WHERE ID = #{id}")
	int delete(int id);*/

}
