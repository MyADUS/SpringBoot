package com.test.dao.mapper.main;

import java.util.List;

import com.test.config.MyMapper;
import com.test.model.Log;

import io.lettuce.core.dynamic.annotation.Param;

public interface LogMapper extends MyMapper<Log>{

	List getloginlog(String param);
	
	List getcaozuolog(String param);

}
