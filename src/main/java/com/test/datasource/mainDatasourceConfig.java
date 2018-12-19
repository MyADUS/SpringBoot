package com.test.datasource;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
@MapperScan(basePackages = {"com.test.dao.mapper.main"}, sqlSessionFactoryRef = "mainSqlSessionFactory")
public class mainDatasourceConfig {
	@Bean(name="mainDatasource")
	// prefix值必须是application.properteis中对应属性的前缀
	@ConfigurationProperties(prefix="spring.datasource.main")
	//必须加此注解，不然报错，下一个类则不需要添加
	@Primary
	public DataSource mainDataSource() {
        return DataSourceBuilder.create().build();
    }
	
	@Primary
	@Bean(name="mainSqlSessionFactory")
	public SqlSessionFactory mainSqlSessionFactory(@Qualifier("mainDatasource") DataSource dataSource) throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);
		try {
			bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/main/*.xml"));
			return bean.getObject();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	@Bean(name="mainSqlSessionTemplate")
	public SqlSessionTemplate mainSqlSessionTemplate(@Qualifier("mainSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
		//使用上面配置的Factory
		SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactory);
		return template;
	}
}