package com.test.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.service.RedisService;
import com.test.service.userTestService;

@Service
public class RedisUpdate {
	
	@Autowired
	private userTestService userTestService;
	
	//缓存处理工具
	@Autowired
    private RedisService redisService;
	
	public void redisUpdate(String redisname) {
		if(redisname == "mapPerson") {
			redisService.remove(redisname);
			List list = userTestService.getAll();
			redisService.set(redisname, list);
		}
	}
}
