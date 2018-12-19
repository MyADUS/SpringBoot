package com.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class jspController {

	// 开始页面
	@RequestMapping(value = "/start")
	public String start() {
		return "test/test";
	}

	// 通过ModelAndView跳转页面及传递值
	// 在页面 <script type="text/javascript"></script>标签中
	// var type = "${type}"; 获取值
	/*
	 * @RequestMapping(value = "/testEdit") public ModelAndView
	 * testEdit(@RequestParam("type") String type) { ModelAndView mav = new
	 * ModelAndView("test/test_edit"); mav.addObject("type", type); return mav; }
	 */

	// 简单的跳转页面
	@RequestMapping(value = "/testEdit")
	public String testEdit() {
		return "test/test_edit";
	}

	// 跳转Excel下载页面（导出Excel，下载通用）
	@RequestMapping(value = "/testExcelExport")
	public String testExcelExport() {
		return "test/Excel_Export_Download";
	}
}
