package com.test.shiro;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * Shiro的配置类
 * @author mayn
 *
 */
@Configuration
public class ShiroConfig {

	/**
	 * 
	 * 创建ShiroFilterFactoryBean
	 */
	@Bean
	public ShiroFilterFactoryBean getShiroFilterFactoryBean(/*@Qualifier("securityManager")*/ DefaultWebSecurityManager securityManager) {
		System.out.println("1");
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		//设置安全管理器
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		//添加Shiro内置过滤器
		/**
		 * Shiro内置过滤器，可以实现权限相关的拦截器
		 * 	常用过滤器：
		 * 		anon:无需认证（登录）可以访问
		 * 		authc:必须认证才可以访问
		 * 		user:如果使用了rememberMe的功能可以直接访问
		 * 		perms:该资源必须得到资源权限才可以访问
		 * 		roles:该资源必须得到角色权限才可以访问
		 */
		Map<String, String> filterMap = new LinkedHashMap<String, String>();
		// 对多级别jsp页面进行拦截
		filterMap.put("/jsp/views/**", "authc");
		filterMap.put("/jsp/upload.jsp", "authc");
		//资源授权
		//filterMap.put("/jsp/test/*", "perms[1]");
		//角色授权
		//filterMap.put("/jsp/test/*", "roles[1]");
		//修改默认的login.jsp
		shiroFilterFactoryBean.setLoginUrl("/jsp/login.jsp");
		//修改默认的提示没有权限页面
		shiroFilterFactoryBean.setUnauthorizedUrl("/jsp/unAuth.jsp");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
		return shiroFilterFactoryBean;
	}
	/**
	 * 
	 * 创建DefaultWebSecurityManager
	 */
	@Bean/*(name="securityManager")*/
	public DefaultWebSecurityManager getDefaultWebSecurityManager(/*@Qualifier("userRealm")*/ userRealm userRealm) {
		System.out.println("2");
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		//关联Realm
		securityManager.setRealm(userRealm);
		return securityManager;
	}
	
	/**
	 * 
	 * 创建Realm
	 */
	@Bean/*(name="userRealm")*/
	public userRealm getRealm() {
		System.out.println("3");
		return new userRealm();
	}
}
