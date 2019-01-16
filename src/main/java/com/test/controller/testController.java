package com.test.controller;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
//import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.model.userTest;
import com.test.service.RedisService;
import com.test.service.userTestService;
import com.test.util.ExcelExportUtil;
import com.test.util.RedisUpdate;
import com.test.util.ShaUtil;
import com.test.util.getResult;

@RestController
public class testController {
	
	//private static Logger logger = Logger.getLogger(testController.class);
	
	@Autowired
	private userTestService userTestService;
	
	//缓存处理工具
	@Autowired
    private RedisService redisService;
	
	// 注入更新缓存的方法
	@Autowired
	private RedisUpdate redisUpdate;
	
	/*
	 * 表单中的日期字符串和JavaBean的Date类型的转换，而SpringMVC默认不支持这个格式的转换，所以需要手动配置，自定义数据的绑定才能解决这个问题。
	 * */
	@InitBinder
	public void init(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
	}
	
	// 当对表中数据进行更新、添加、删除等操作时，调用此方法，重新缓存数据信息。
	/*public void redisUpdate(String redisname) {
		if(redisname == "mapPerson") {
			redisService.remove(redisname);
			List list = getAll();
			redisService.set(redisname, list);
		}
	}*/
	
	//测试mapper.xml
	@RequestMapping(value = "/do/getPerson")
	public userTest getPerson(int id) {
		return userTestService.getPerson(id);
	}
	
	//查询所有，返回list集合
	@RequestMapping(value = "/do/listPerson")
    public List<userTest> getAll() {
        return userTestService.getAll();
    }
	
	//查询所有，返回map集合
  	@SuppressWarnings({"rawtypes" })
  	@RequestMapping(value = "/do/mapPerson")
  	public HashMap getMapAll(int page,int limit) throws Exception {
  		HashMap map = new HashMap();
  		List list = null;
  		List mapPerson = (List) redisService.get("mapPerson");
  		if(mapPerson == null) {
  			list = userTestService.getAll();
  	  		redisService.set("mapPerson", list);
  		}else {
  			list = mapPerson;
  		}
  		map = getResult.getMapResult(list,page-1,limit);
  		return map;
	}  
    
	//添加或更新操作
	@RequestMapping("/do/updateOrAdd")
	public String updateOrAdd(userTest userTest) throws Exception{
		int i = 0;
		Date time = new Date();
		userTest.setCzrq(time);
		if(userTest.getId() != null) {
			i = userTestService.update(userTest);
		}else {
			i = userTestService.add(userTest);
		}
		if(i > 0) {
			redisUpdate.redisUpdate("mapPerson");
			//redisUpdate("mapPerson");
			return "yes";
		}else {
			return "no";
		}
    }
	
	//删除操作
	@RequestMapping(value = "/do/deletePerson")
	public String delete(HttpServletRequest request){
		String ID = request.getParameter("id");
		int id = Integer.parseInt(ID);
		int i = userTestService.delete(id);
		if(i > 0) {
			//logger.info("删除成功");
			redisUpdate.redisUpdate("mapPerson");
			//redisUpdate("mapPerson");
			return "yes";
		}else {
			return "删除失败！";
		}
    }
	
	//Excel导出
	@RequestMapping(value = "/do/ExcelExport")
	public String ExcelExport(String data) throws Exception {
		String path = ExcelExportUtil.getPoiExcelExportPath(ExcelExportUtil.getColumnWeigth(),ExcelExportUtil.getXzxkTitleName(),data);
		return path;
    }
	
	//批量，删除操作
	@RequestMapping(value = "/do/DeleteMany")
	public int DeleteMany(String[] ids){
		int d = 0;
		for(int i=0;i<ids.length;i++) {
			int id = Integer.parseInt(ids[i]);
			int t = userTestService.delete(id);
			if(t>0) {
				//logger.info("成功删除id为"+id);
				d = d+1;
			}else {
				return -1;
			}
		}
		if(d > 0) {
			redisUpdate.redisUpdate("mapPerson");
			//redisUpdate("mapPerson");
		}
		return d;
    }
	
	//男女比例查询
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/do/getPieList")
	public List getPieList(){
		List list = userTestService.getPieList();
		return list;
    }
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/do/getMapBySex")
  	public HashMap getMapBySex(int sex,int page,int limit) throws Exception {
  		HashMap map = new HashMap();
  		List list = userTestService.getMapBySex(sex);
  		map = getResult.getMapResult(list,page-1,10000000);
  		return map;
	}  
}
