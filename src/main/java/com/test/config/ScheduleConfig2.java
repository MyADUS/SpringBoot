package com.test.config;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.test.Scheduled.ScheduledTest;


//	并行任务

/*@Component
@Configurable
@EnableScheduling
@EnableAsync
public class ScheduleConfig2 {

	private static final Logger logger = LoggerFactory.getLogger(ScheduleConfig2.class);
	
	@Async
	@Scheduled(cron="0/10 * * * * ?")
	public void test1() throws Exception {
		logger.info("###info");
		logger.debug("###debug");
		logger.error("###error");
	}
	
	@Async
	@Scheduled(cron="0/10 * * * * ?")
	public void test2() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
		System.out.println(sdf.format(new Date())+",test2");
	}
}*/
