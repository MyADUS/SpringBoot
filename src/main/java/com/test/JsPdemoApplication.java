package com.test;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

@MapperScan("com.test.dao.mapper.*")
@SpringBootApplication
// @EnableScheduling 注解的作用是发现注解@Scheduled的任务并后台执行
@EnableScheduling
public class JsPdemoApplication {

	private static final Logger logger = LoggerFactory.getLogger(JsPdemoApplication.class);
	
	//添加配置
	//在实体类的字段上添加@JSONField(serialize=false)注解
	//若讲实体类序列化后，添加注解的字段无法返回，则表示配置成功。
	/*
	 * 要注意的是在整合fastjson之后，springboot自带的jackson的一些功能便失效了
	 * 例：application.properties配置文件中关于后台传递到前台的时间格式问题、以及时区问题则会失效
	 * 实力类中的@JsonFromat()等几个对返回时间的格式的注解会失效。
	 * 但是，我们可以使用fastjson中自带的@JSONField(format="yyyy-MM-dd HH:mm:ss")注解，完成相同的效果。
	 * 
	 * */
	@SuppressWarnings("static-access")
	@Bean
	public HttpMessageConverters fastJsonHttpMessageConverters() {
		//1.需要定义一个convert转换消息的对象
		FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
		//2.添加fastJson的配置信息
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
		// 报错com.alibaba.fastjson.JSONException: autoType is not support.
		// 打开autotype功能
		fastJsonConfig.getParserConfig().getGlobalInstance().setAutoTypeSupport(true);
		//3处理中文乱码问题
	    List<MediaType> fastMediaTypes = new ArrayList<>();
	    fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
		//4.在convert中添加配置信息
	    fastConverter.setSupportedMediaTypes(fastMediaTypes);
		fastConverter.setFastJsonConfig(fastJsonConfig);
		HttpMessageConverter<?> converter = fastConverter;
		return new HttpMessageConverters(converter);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(JsPdemoApplication.class, args);
	}
}
