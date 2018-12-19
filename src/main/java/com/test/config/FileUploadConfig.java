package com.test.config;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FileUploadConfig {
 
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        // 加入下列限制文件大小的配置之后，也可以在application.properties中加入文件大小限制配置
        factory.setMaxFileSize("100MB"); 
        factory.setMaxRequestSize("100MB");
        return factory.createMultipartConfig();
    }
}
