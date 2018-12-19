<%@page import="com.test.util.OS"%>
<%@page import="com.test.util.StringUtil"%>
<%@page import="com.test.util.FileUtil"%>
<%@page import="com.test.util.SystemConstant"%>
<%@page import="java.io.File"%>
<%@ page language="Java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="java.util.*,java.net.*,java.lang.reflect.*"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	//获取文件的路径
	String fileName = request.getParameter("path");//文件名
    String fileAllPath = "";
    File filetemp = null;
	byte[] fileContent = null;
	fileAllPath = OS.getOS()+fileName;//文件路径
	filetemp = new File(fileAllPath);//文件路径
	if(filetemp.exists()){
		fileContent = FileUtil.getBytes(filetemp);
	}
	
	if(fileContent==null){
		response.getWriter().write("文件未找到！");
		return;
	} 
	response.setHeader("Content-Disposition",
			"attachment; filename=" + new String(fileName.getBytes("utf-8"), "iso-8859-1"));
	response.getOutputStream().write(fileContent,0,fileContent.length);
	response.getOutputStream().flush();
	response.getOutputStream().close();
	out.clear();
	out = pageContext.pushBody();
	//删除物理文件
	filetemp.delete();
%>