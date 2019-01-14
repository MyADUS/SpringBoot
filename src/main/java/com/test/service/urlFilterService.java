package com.test.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.test.dao.mapper.main.urlFilterMapper;
import com.test.model.urlFilter;;

@Service
public class urlFilterService {

	@Autowired
	private urlFilterMapper urlFilterMapper;

	public List getUrlFilter() {
		// TODO Auto-generated method stub
		return urlFilterMapper.getUrlFilter();
	}

	public int updateUrlFilter(urlFilter urlfilter) {
		// TODO Auto-generated method stub
		int i = urlFilterMapper.updateByPrimaryKey(urlfilter);
		return i;
	}

	public int addUrlFilter(urlFilter urlfilter) {
		// TODO Auto-generated method stub
		int i = urlFilterMapper.insert(urlfilter);
		return i;
	}

	public int deleteUrlFilter(int id) {
		// TODO Auto-generated method stub
		//return urlFilterMapper.deleteUrlFilter(id);
		return urlFilterMapper.deleteByPrimaryKey(id);
	}
	
}
