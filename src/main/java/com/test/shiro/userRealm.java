package com.test.shiro;

import java.util.HashSet;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import com.test.model.user;
import com.test.service.userService;

/**
 * 
 * 自定义Realm
 * @author mayn
 *
 */
public class userRealm extends AuthorizingRealm{

	@Autowired
	private userService userService;
	
	/**
	 * 
	 * 执行授权逻辑
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		// TODO Auto-generated method stub
		System.out.println("授权逻辑。。。");
		//给资源授权
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		//添加资源的授权字符串
		//info.addStringPermission("1");
		
		//获取当前登录用户
		//subject.getPrincipal()获取的实体对象就是下面认证逻辑中return的SimpleAuthenticationInfo中第一个
		/*Subject subject = SecurityUtils.getSubject();
		user user1 = (user)subject.getPrincipal();*/
		user user = (user) arg0.getPrimaryPrincipal();
		// use1与user1 完全相同
		//资源授权
		//info.addStringPermission(userTest.getManager()+"");
		//角色授权
		//info.addRole(userTest.getManager());
		String manager = user.getManager();
		info.addRole(manager);
		//setRoles(roles) roles须是set集合
		/*String[] mng = manager.split(",");
		HashSet<String> roles = new HashSet<String>();
		for(int i=0;i<mng.length;i++) {
			roles.add(mng[i]);
		}
		info.setRoles(roles);*/
		return info;
	}

	/**
	 *
	 * 执行认证逻辑
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken arg0) throws AuthenticationException {
		// TODO Auto-generated method stub
		System.out.println("认证逻辑。。。");
		// Shiro判断逻辑，判断用户名和密码
		// 1.判断用户名
		UsernamePasswordToken token = (UsernamePasswordToken) arg0;
		System.out.println("查询数据库。。。");
		user user = userService.getByUsername((String)token.getUsername());
		if(user == null) {
			//用户名不存在
			return null;//Shiro底层会抛出UnKnowAccountException
		}
		//2.判断密码
		return new SimpleAuthenticationInfo(user,user.getPassword(),"");
	}

}
