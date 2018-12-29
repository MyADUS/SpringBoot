package com.test.controller;

import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.test.dao.mapper.main.LogMapper;
import com.test.model.Log;
import com.test.model.user;
import com.test.model.userTest;
import com.test.service.RedisService;
import com.test.service.userService;
import com.test.util.ImageCode;
import com.test.util.RedisUpdate;
import com.test.util.ShaUtil;
import com.test.util.getResult;;

@RestController
public class userController {
	
	@Autowired
	private userService userService;
	
	@Autowired
	private LogMapper logmapper;
	
	@Autowired
    private HttpSession session;
	
	@Autowired
    private HttpServletRequest request;
	
	@Autowired
	private HttpServletResponse response;
	
	//缓存方法类
	@Autowired
    private RedisService redisService;
	
	// 注入更新缓存的方法
	@Autowired
	private RedisUpdate redisUpdate;
		
	//登录
	@RequestMapping(value = "/noneed/toLogin")
	public String toLogin(String username,String password,String imagecode) throws Exception {
		//1.获取Subject
		Subject subject = SecurityUtils.getSubject();
		//对密码进行加密
		//ShaUtil.shaEncode(password);
		//2.封装用户数据
		UsernamePasswordToken token = new UsernamePasswordToken(username,ShaUtil.shaEncode(password));
		//3.执行登录方法
		try {
			if(imagecode.toLowerCase().equals(session.getAttribute("imagecode").toString().toLowerCase())) {
				System.out.println("执行登录方法。。。");
				subject.login(token);
				//登录成功，进行日志记录
				Log log = new Log();
				log.setIp(request.getRemoteAddr());
				log.setIslogin("1");
	            log.setMethod("登录");
	            log.setCzr(username);
	            log.setCzrq(new Date());
				logmapper.insert(log);
				//session.setAttribute("username", username);
				return "yes";
			}else {
				return "验证码输入错误！";
			}
		} catch (UnknownAccountException e) {
			// 登录失败：用户名不存在
			return "用户名不存在！";
		} catch (IncorrectCredentialsException e) {
			// 登录失败：密码错误
			return "密码错误！";
		}
	}
	
	//注销
	@RequestMapping(value = "/logout")
	public String logout() {
		Subject subject = SecurityUtils.getSubject();
		if(subject != null) {
			subject.logout();
		}
		return "success";
	}
	
	//修改密码
	@RequestMapping(value = "/noneed/updatePassword")
	public String updatePassword(String username,String password,String newpassword,String imagecode) throws Exception {
		String value = toLogin(username,password,imagecode);
		String i = null;
		if(value == "yes") {
			int s = userService.updatePasswordByUsername(username,ShaUtil.shaEncode(newpassword));
			if(s > 0) {
				logout();
				i = "success";
			}else {
				i = "密码更改失败！";
			}
		}else {
			return value;
		}
		return i;
	}
	
	//头部，个人信息页面修改操作，查询赋值
	@RequestMapping(value="/noneed/getUserself")
	public user getUserself() throws Exception{
		Subject subject = SecurityUtils.getSubject();
		user user = (user)subject.getPrincipal();
		return user;
	}
	
	//批量重置密码，仅manager含有2的有权
	@RequestMapping(value = "/rePassword")
	public int rePassword(String[] ids) throws Exception{
		int d = 0;
		for(int i=0;i<ids.length;i++) {
			int id = Integer.parseInt(ids[i]);
			String password = ShaUtil.shaEncode("000");
			int t = userService.rePassword(id,password);
			if(t>0) {
				//logger.info("成功删除id为"+id);
				redisUpdate.redisUpdate("mapManagerList");
				d = d+1;
			}else {
				return -1;
			}
		}
		return d;
    }
	
	//新用户注册
	@RequestMapping(value = "/noneed/user_newPerson")
	public String user_newPerson(user user, String imagecode) throws Exception {
		String truename = user.getTruename();
		String username = user.getUsername();
		user dbuser = userService.getUserByTwoName(truename,username);
		String str = "";
		if(dbuser == null) {
			if(imagecode.toLowerCase().equals(session.getAttribute("imagecode").toString().toLowerCase())) {
				user.setManager("0");
				user.setPassword(ShaUtil.shaEncode(user.getPassword()));
				int i = userService.newPerson(user);
				if(i>0) {
					redisUpdate.redisUpdate("mapManagerList");
					str = "success";
				}else {
					str = "注册失败，请重试！！！";
				}
			}else {
				str = "验证码错误，请重试！！！";
			}
		}else {
			str = "用户名或真实姓名已被注册，请重新尝试！！！";
		}
		return str;
	}
	
	//查询注册了，但没有登录权限的人数
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getCount0")
	public ArrayList getCount0() throws Exception {
		ArrayList list = userService.getCount0("0");
		if(list != null) {
			return list;
		}else {
			return null;
		}
	}
	
	//获取需要授权登录的人
	@RequestMapping(value = "/mapManager0")
	public HashMap mapManager0(int page,int limit) throws Exception {
		HashMap map = new HashMap();
  		List list = userService.mapManager0("0");
  		map = getResult.getMapResult(list,page-1,limit);
  		return map;
	}
	
	//获取所有人员信息
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/mapManager")
	public HashMap mapManager(int page,int limit) throws Exception {
		HashMap map = new HashMap();
		List list = null;
		List mapManagerList = (List) redisService.get("mapManagerList");
		if(mapManagerList == null) {//缓存为空
	  		list = userService.mapManager();
	  		redisService.set("mapManagerList", list);
		}else {
			list = mapManagerList;
		}
  		map = getResult.getMapResult(list,page-1,limit);
  		return map;
	}
	
	//授权登录
	@RequestMapping(value = "/ShouQuan")
	public String ShouQuan(int id,String manager) throws Exception {
		String str = "";
		/*String newmanager = manager + ",1";*/
		//manager从前台jsp页面传递的值为"0","1","2"
		if(id > 0) {
			int i = userService.ShouQuan(id,manager);
			if(i > 0) {
				redisUpdate.redisUpdate("mapManagerList");
				str = "yes";
			}else {
				str = "授权失败！";
			}
		}
  		return str;
	}
	
	//添加或更新操作
	@RequestMapping("/updateOrAddUser")
	public String updateOrAddUser(user user) throws Exception{
		int i = 0;
		Date time = new Date();
		if(user.getId() != null) {
			i = userService.update(user);
		}else {
			i = userService.add(user);
		}
		if(i > 0) {
			redisUpdate.redisUpdate("mapManagerList");
			//更改成功后，调用logout方法，重新登录，以确保shiro认证中的token信息正确显示
			logout();
			//redisUpdate("mapPerson");
			return "yes";
		}else {
			return "no";
		}
    }
	
	@RequestMapping(value = "/noneed/imageCode")
	public void imageCode() throws Exception{
		Object[] obj = ImageCode.createImage();
		session.setAttribute("imagecode", obj[0]);
		BufferedImage img = (BufferedImage) obj[1];
		response.setContentType("image/png");
		OutputStream os= response.getOutputStream();
		ImageIO.write(img, "png", os);
	}
	
	@RequestMapping(value="/noneed/sss")
	public String sss() throws Exception{
		user user = userService.sss();
		String jsonString = JSONObject.toJSONString(user);
		JSONObject jsonObject = JSONObject.parseObject(jsonString);
		String pwd = (String) jsonObject.get("password");
		String un = (String) jsonObject.get("username");
		String username = user.getUsername();
		String password = user.getPassword();
		return "用户名：" + username + "@@@@@@密码：" + password;
	}
}
