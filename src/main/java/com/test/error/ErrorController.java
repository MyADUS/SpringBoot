package com.test.error;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackages="com.test.controller")
public class ErrorController {

	//捕获到异常，返回map集合
	//	@ExceptionHandler	该注解在一个页面中只能有一个！！
	/*@ExceptionHandler(RuntimeException.class)
	@ResponseBody
	public Map<String, Object> errorJSON(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("errorCode", "404");
		map.put("errorMsg", "全局捕获异常");
		return map;
	}*/
	
	//捕获到异常，返回错误页面
	@ExceptionHandler(RuntimeException.class)
	public String errorJSP() {
		return "errorJSP";
	}
}
