package com.test.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.test.service.RedisService;
import com.test.service.userService;

@Controller
public class controller {
	
	@Autowired
	private userService userService;
	
	/*@Autowired
    private RedisUtil redisUtil;*/
	
	@Autowired
    private RedisService redisService;
    
	@RequestMapping("/JSPindex")
	public String JSPindex() {
		return "JSPindex";
	}
	
	@ResponseBody
	@RequestMapping("/setsecondary")
	public int setSecondary() {
		int i = userService.setSecondary();
		return i;
	}
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping("/getsecondary")
	public List getSecondary() throws Exception{
		List ss = userService.getSecondary();
		/*redisUtil.lSet("list", ss);
		Object s = redisUtil.lGet("list",0,-1);
		System.out.println(s);*/
		/*redisTemplate.opsForValue().set("cs", "测试");
		String s = (String) redisTemplate.opsForValue().get("cs");
		System.out.println(s);*/
		redisService.set("list", ss);
		//redisService.remove("list");
		return ss;
	}
	
	@ResponseBody
	@RequestMapping("/removeRedis")
	public String removeRedis() {
		redisService.remove("list");
		redisService.remove("mapManagerList");
		redisService.remove("mapPerson");
		return "yes";
	}
	
	@ResponseBody
	@RequestMapping("/testRedis")
	public Object testRedis() {
		Object s = redisService.get("list");
		return s;
	}
	
	/*@ResponseBody
	@RequestMapping("/hello")
	public String hello() {
		return "Hello World!!!";
	}*/
	
	/*@ResponseBody
	@RequestMapping("/errorTest")
	public String errorTest(int i) {
		int j = 1 / i ;
		return "success?j=" + j;
	}*/

	/*@Value("${name}")
    private String name;
	@Value("${age}")
	private String age;*/
 
	/*@ResponseBody
    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    public String tell(){
        return name + "今年" + age + "岁！";
    }*/
	
	/*@ResponseBody
	@RequestMapping(value = "/hello",method = RequestMethod.GET)
	public String say() {
		return userTest.getName() + userTest.getAge();
	}*/
	
}
