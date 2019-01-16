package com.test.shiro;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.SimpleCommandLinePropertySource;

import com.test.model.urlFilter;
import com.test.service.urlFilterService;

/**
 * 
 * Shiro的配置类
 * @author mayn
 *
 */
@Configuration
public class ShiroConfig {

	//rememberme无效	暂时不用
	/*public SimpleCookie remembermeCookie() {
		System.out.println("4");
		//这个参数是cookie的名称，对应前端的checkbox的name = rememberme
		SimpleCookie simpleCookie = new SimpleCookie("rememberme");
		//<!-- 记住我cookie生效时间30天 ,单位秒;-->
		simpleCookie.setMaxAge(2592000);
		return simpleCookie;
	}
	
	@Bean
	public CookieRememberMeManager rememberMeManager(){
	    CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
	    cookieRememberMeManager.setCookie(remembermeCookie());
	    //rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
	    cookieRememberMeManager.setCipherKey(Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));
	    return cookieRememberMeManager;
	}*/
		
	/**
	 * 
	 * 创建ShiroFilterFactoryBean
	 */
	@Bean
	public ShiroFilterFactoryBean getShiroFilterFactoryBean(/*@Qualifier("securityManager")*/ DefaultWebSecurityManager securityManager,ShiroService shiroservice) {
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
		
		// 现在通过调用shiroservice.loadFilterChainDefinitions()方法，查询数据库表（url_filter），动态赋予拦截信息，
		// 并且在有权限的人员在页面中对url_filter表进行增删改操作时，可以不用重启服务器，在urlFilterController中，当增删改操作正确执行后，
		// 调用shiroservice.updatePermission(shiroFilterFactoryBean)方法，重新加载拦截权限，避免修改权限就要重启服务器造成的不便。
		
		/*// 对直接请求后台方法进行拦截
		// 先对验证码等无需登录即可访问的方法设置无需拦截
		filterMap.put("/noneed/*", "anon");
		// 防止直接调用后台方法对数据库进行操作
		filterMap.put("/*", "authc");
		// 对多级别jsp页面进行拦截
		// 防止未登录状态访问部分页面
		filterMap.put("/jsp/views/**", "authc");
		filterMap.put("/jsp/upload.jsp", "authc");
		// 拦截所有无需登录验证的方法，避免多级访问如（/test/test...）等访问路径不经过登录验证访问后台方法
		// 在所有无需登录验证的方法前添加/do进行拦截控制
		filterMap.put("/do/**", "authc");*/
		//资源授权
		//filterMap.put("/jsp/test/*", "perms[1]");
		//角色授权
		//filterMap.put("/jsp/test/*", "roles[1]");
		//修改默认的login.jsp
		shiroFilterFactoryBean.setLoginUrl("/jsp/login.jsp");
		//修改默认的提示没有权限页面
		shiroFilterFactoryBean.setUnauthorizedUrl("/jsp/unAuth.jsp");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(shiroservice.loadFilterChainDefinitions());
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
		
		//securityManager.setRememberMeManager(rememberMeManager());
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
