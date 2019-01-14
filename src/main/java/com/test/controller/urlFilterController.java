package com.test.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.model.urlFilter;
import com.test.service.urlFilterService;
import com.test.shiro.ShiroService;
import com.test.util.getResult;

@RestController
public class urlFilterController {

	@Autowired
	private urlFilterService urlFilterService;
	
	@Autowired
    private HttpSession session;
	
	@Autowired
	private ShiroService shiroservice;
	
	@Autowired
	private ShiroFilterFactoryBean shiroFilterFactoryBean;
	
	/*
	 * 表单中的日期字符串和JavaBean的Date类型的转换，而SpringMVC默认不支持这个格式的转换，所以需要手动配置，自定义数据的绑定才能解决这个问题。
	 * */
	@InitBinder
	public void init(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH-mm-ss"), true));
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/getUrlFilter")
	public HashMap getUrlFilter(int page, int limit) throws Exception{
		HashMap map = new HashMap();
		List list = urlFilterService.getUrlFilter();
		map = getResult.getMapResult(list,page-1,limit);
		return map;
	}
	
	@RequestMapping(value="/updateOrAddUrlFilter")
	public String updateOrAddUrlFilter(urlFilter urlfilter) throws Exception{
		urlfilter.setCzr((String) session.getAttribute("username"));
		urlfilter.setCzrq(new Date());
		int i = 0;
		if(urlfilter.getId() != null) {
			i = urlFilterService.updateUrlFilter(urlfilter);
		}else {
			i = urlFilterService.addUrlFilter(urlfilter);
		}
		String res = "";
		if(i > 0) {
			shiroservice.updatePermission(shiroFilterFactoryBean);
			res = "yes";
		}else {
			res = "no";
		}
		return res;
	}
	
	@RequestMapping(value="/deleteUrlFilter")
	public String deleteUrlFilter(int id) throws Exception{
		int i = urlFilterService.deleteUrlFilter(id);
		String res = "";
		if(i > 0) {
			shiroservice.updatePermission(shiroFilterFactoryBean);
			res = "ok";
		}else {
			res = "删除失败！";
		}
		return res;
	}
}
