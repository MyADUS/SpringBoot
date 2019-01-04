package com.test.Aop;

import java.util.Arrays;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.test.dao.mapper.main.LogMapper;
import com.test.model.Log;
import com.test.model.user;
import com.test.service.LogService;
import com.test.util.getRealIp;

/**
* 日志切面
*/
//作用是把当前类标识为一个切面供容器读取
@Aspect
@Component
public class AopConfig {
	
	@Autowired
	private LogService logservice;
	
	@Autowired
	private LogMapper logmapper;
	
	// 获取真实ip地址（貌似不管用）
	@Autowired
	private getRealIp getRealIp;
		
	/*除了@Around外，
	每个方法里都可以加或者不加参数JoinPoint，
	如果有用JoinPoint的地方就加，不加也可以，
	JoinPoint里包含了类名、被切面的方法名，参数等属性，可供读取使用。
	
	一般常用的有before和afterReturn组合，或者单独使用Around，即可获取方法开始前和结束后的切面。*/
	
	
	// public * com.test.controller..*.*(..))
	// springboot项目中 execution() 必须使用这种格式才能正确
	// 后面加上 && !execution(* com.test..*.init*(..)) 可以排除init方法
	@Pointcut("execution(public * com.test.controller..*.*(..)) && !execution(* com.test.controller..*.init*(..))")
	public void webLog(){}
	
	//标识一个前置增强方法，相当于BeforeAdvice的功能
	@Before("webLog()")  
    public void deBefore(JoinPoint joinPoint) throws Throwable {
		//获取登录后存储的用户信息
		Subject subject = SecurityUtils.getSubject();
		if(subject.getPrincipal() != null) {
			user user = (com.test.model.user)subject.getPrincipal() ;
			String username = user.getUsername();
			// 接收到请求，记录请求内容  
	        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();  
	        HttpServletRequest request = attributes.getRequest();
	        if(request.getMethod().equals("POST")) {
	        	/*System.out.println(username);
	        	// 记录下请求内容
	            System.out.println("URL : " + request.getRequestURL().toString());
	            System.out.println("HTTP_METHOD : " + request.getMethod());
	            System.out.println("IP : " + request.getRemoteAddr());
	            System.out.println("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
	            System.out.println("ARGS : " + Arrays.toString(joinPoint.getArgs()));*/
	            
	            //向log表中存储信息记录各用户进行了什么操作
	            Log log = new Log();
	            //log.setIp(request.getRemoteAddr());
	            log.setIp(getRealIp.RealIp());
	            log.setIslogin("0");
	            log.setMethod(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
	            log.setCzr(username);
	            log.setCzrq(new Date());
	            logmapper.insert(log);
	        }
		}
    }
	
	//后置增强，相当于AfterReturningAdvice，方法退出时执行
	//@AfterReturning方法里，可以加returning = “XXX”，XXX即为在controller里方法的返回值
	@AfterReturning(returning = "ret", pointcut = "webLog()")  
    public void doAfterReturning(Object ret) throws Throwable {  
        // 处理完请求，返回内容  
        System.out.println("方法的返回值 : " + ret);  
    }  
  
    /*//后置异常通知
	//异常抛出增强，相当于ThrowsAdvice
	//@AfterThrowing方法里，可以加throwing = "XXX"，供读取异常信息
    @AfterThrowing("webLog()")  
    public void throwss(JoinPoint jp){  
        System.out.println("方法异常时执行.....");  
    }
	 @AfterThrowing(throwing = "ex", pointcut = "webLog()")
	public void throwss(JoinPoint jp, Exception ex){
		System.out.println("方法异常时执行.....");
	}
  
    //后置最终通知,final增强，不管是抛出异常或者正常退出都会执行  
    @After("webLog()")  
    public void after(JoinPoint jp){  
        System.out.println("方法最后执行.....");  
    }  
  
    //环绕通知,环绕增强，相当于MethodInterceptor  
    //@Around参数必须为ProceedingJoinPoint，pjp.proceed相应于执行被切面的方法。
    @Around("webLog()")  
    public Object arround(ProceedingJoinPoint pjp) {  
        System.out.println("方法环绕start.....");  
        try {  
            Object o =  pjp.proceed();  
            System.out.println("方法环绕proceed，结果是 :" + o);  
            return o;  
        } catch (Throwable e) {  
            e.printStackTrace();  
            return null;  
        }  
    }*/
}
