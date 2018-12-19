package com.test.dao.mapper.secondary;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface secondaryMapper {

	//@Update("UPDATE USER SET MANAGER = 222 WHERE ID = 1")
	int setSecondary();

	//@Select("SELECT USERNAME,PASSWORD FROM USER WHERE 1=1")
	List<Object> getSecondary();

}
