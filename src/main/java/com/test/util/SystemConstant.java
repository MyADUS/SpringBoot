 /**
 * @Author: 朱卫士
 * @Createtime: 2017年9月19日 上午11:54:07
 * @Copyright: Copyright (c) 2016
 * @Company: 北京永杰友信科技有限公司
 * @Version：1.0
 * @Description: 
 */
package com.test.util;

/**
 * 
* @ClassName: SystemConstant
* @Description: TODO(系统静态常量,系统公用常量值,都在这里进行定义,名称大写,拼接字母用 _ 连接)
* @author zhuweishi
* @date 2017年9月19日 上午11:54:46
*
 */
public class SystemConstant {
	
	/*=============================系统静态常量Start===============================================*/
	// 登陆的首页面
	public static final String SYSTEM_LOGIN_JSP = "login.jsp";
	// 用户session 用户名
	public static final String SYSTEM_SESSION_NAME = "USERNAME";
	// 真实姓名
	public static final String SYSTEM_SESSION_TRUENAME = "TRUENAME";
	// 用户ID
	public static final String SYSTEM_SESSION_USERID = "USERID";
	// 登陆用户部门ID
	public static final String SYSTEM_SESSION_DEPTID = "DEPTID";
	// 登陆用户部门及子节点ID
	public static final String SYSTEM_SESSION_DEPTID_PARENTID = "DEPTID_PARENTID";
	// 登陆用户监管部门ID
	public static final String SYSTEM_SESSION_DEPTID_JGDEPTID = "JGDEPTID";
	// 用户部门名称
	public static final String SYSTEM_SESSION_DEPTNAME = "SESSION_DEPTNAME";
	// 用户部门的深度值  例如 0,7,9 不包含自己部门的值  
	public static final String SYSTEM_SESSION_MAX_DEPTID = "SESSION_MAX_DEPTID";
	// 用户初始 密码 zlxyxx888
	public static final String SYSBASE_INITIAL_PASSWORD = "1321e3e1bb50baa778aa3f79b9c97db0af6d9722";
	// 用来存储,用户是否是管理员用户
	public static final String SYSTEM_SESSION_ADMIN = "SYSTEM_SESSION_ADMIN";
	
	
	// apiKey : xml 推送key
	public static final String XML_API_KEY = "79f15c0c6aa6c52f3393134eb81dd35f";
	// 发改委接口: 根据统一社会信用代码 查询企业信息  api key
	public static final String FGW_API_KEY = "121792EE74CB464B9BB657D3AAAB46D4";
	
	// 地方编码  
	public static final String DFBM_HN = "410000";
	/*==============================附件相关静态常量End=================================================*/
	// 系统上传附件保存路径 windows下
	public static final String SYSTEM_WINDOWS_PATH = "D:/Zlxypt/Upload";
	public static final String SYSTEM_LINUX_PATH = "/usr/local/xyxx/tomcat/Upload/";
	
	public static final String EXCEL_FZJG_NAME = "河南省质量技术监督局";
	// 映射路径名称 
	public static final String LOCAL_PATH_MAPPING = "/UeditorPathfile/"; 
	// 信用信息 信用报告 ,pdf路径 
	public static final String XYXX_XYBG_PDF_URL = "/PDF/";
	//新闻
	public static final String XYXX_XinWen_URL = "/XinWen/";
	//Excel
	public static final String XYXX_Excel_URL = "/Excel/";
	//蓬勃
	public static final String XYXX_PengBo_URL = "/PengBo/";
	//联合奖罚
	public static final String XYXX_LHJF_URL = "/LHJF/";
	
	// pdf 水印图片路径
	public static final String XYXX_XYBG_PDF_IMAGE_URL = "dist/Tool/img/xyxx_xybg.jpg";
	
	public static final String XYXX_EXCEL_URL = "/EXCEL/EXPORT/";
	
	// 信用等级 
	public static final int QYJBXX_JBFZ = 80;
	
	
	//排行榜
	public static final String PHB_URL = "/PHB/";
	
	//市，县级质监局，导入数量信息
	public static final String ZJJ_NUMS_URL = "/ZJJNUMS/";
}

