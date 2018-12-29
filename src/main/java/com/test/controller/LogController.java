package com.test.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.service.LogService;
import com.test.util.StringUtil;
import com.test.util.getResult;

@RestController
public class LogController {

	@Autowired
	private LogService logservice;
	
	@Autowired
    private HttpSession session;
	
	/*
	 * 表单中的日期字符串和JavaBean的Date类型的转换，而SpringMVC默认不支持这个格式的转换，所以需要手动配置，自定义数据的绑定才能解决这个问题。
	 * */
	@InitBinder
	public void init(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
	}
	
	//查询所有登录信息
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/loginlog")
	public HashMap loginlog(String param, int page, int limit) throws Exception {
		HashMap map = new HashMap();
		List list = null;
		try {
			list = logservice.getloginlog(param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		map = getResult.getMapResult(list,page-1,limit);
		return map;
	}
	
	//查询不是登录操作的信息
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/caozuolog")
	public HashMap caozuolog(String param, int page, int limit) throws Exception {
		HashMap map = new HashMap();
		List list = null;
		try {
			list = logservice.getcaozuolog(param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		map = getResult.getMapResult(list,page-1,limit);
		return map;
	}
}
