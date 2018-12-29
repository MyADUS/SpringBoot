package com.test.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.dao.mapper.main.LogMapper;
import com.test.model.Log;

@Service
public class LogService {

	@Autowired
	private LogMapper logmapper;
	
	public int setLog(Log log) {
		int i = logmapper.insert(log);
		return i;
	}

	public List getloginlog(String param) {
		// TODO Auto-generated method stub
		return logmapper.getloginlog(param);
	}

	public List getcaozuolog(String param) {
		// TODO Auto-generated method stub
		return logmapper.getcaozuolog(param);
	}

	
}
