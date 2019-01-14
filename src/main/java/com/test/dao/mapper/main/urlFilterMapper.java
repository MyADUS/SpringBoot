package com.test.dao.mapper.main;

import java.util.List;

import com.test.config.MyMapper;
import com.test.model.urlFilter;

public interface urlFilterMapper extends MyMapper<urlFilter>{

	List getUrlFilter();

	/*int deleteUrlFilter(int id);*/

}
