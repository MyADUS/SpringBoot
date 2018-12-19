package com.test.Scheduled;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.test.service.userService;
import com.test.util.DeleteFile;
import com.test.util.ShaUtil;

// springboot自带定时器默认的单线程（串行）定时任务

@Component
public class ScheduledTest {
	
	private static final Logger logger = LoggerFactory.getLogger(ScheduledTest.class);
	
	@Autowired
	private userService userService;
	
	//@Scheduled(cron="0/10 * * * * ?")
	public void scheduling1() throws Exception {
		logger.info("###info");
		logger.debug("###debug");
		logger.error("###error");
		//范围[0,2]
		String ids = "123";
		//int i = (int)(Math.random()*(max-min+1))+min;
		int i = (int) (Math.random()*(3));
		Random ran = new Random();
		//int i = ran.nextInt(3);
		System.out.println("索引值为"+i);
		int id = ids.charAt(i) - '0';
		System.out.println("id为"+id);
		//8aefb06c426e07a0a671a1e2488b4858d694a730
		String password = ShaUtil.shaEncode("123");
		int s = userService.rePassword(id, password);
		if(s > 0) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			System.out.println(sdf.format(new Date())+",id为"+id+"的用户密码更改为123");
		}else {
			System.out.println("更改失败！！！");
		}
	}
	
	//@Scheduled(cron="0/10 * * * * ?")
	public void scheduling2() throws Exception{
	}
	
	@Scheduled(cron="0 0 16 ? * *")//每天16点执行
	public void deleteFiles() throws Exception{
		String f1 = "D:/upload/uploadFile";
		String f2 = "D:/upload/uploadFiles";
		try {
			DeleteFile.delFolder(f1);
			logger.info("定时删除单文件上传文件夹！！！");
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			DeleteFile.delFolder(f2);
			logger.info("定时删除多文件上传文件夹！！！");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
}
