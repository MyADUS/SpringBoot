<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="views/public/public.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录初始页面</title>
</head>
<body style="background-color: pink">
	<div style="float:left;">
		<h1 style="margin-left: 25%;">我的小可乐！<a onclick="XBBshow()">&nbsp;</a></h1>
		<img alt="" src="<%=basePath%>img/kele.jpeg" height="500px">
	</div>
	<shiro:hasRole name="2">
		<div id="xbb" style="display:none">
			<h1 style="margin-left: 25%;">我的小宝贝！</h1>
			<img onclick="XBBhide()" alt="" src="<%=basePath%>img/xbb.jpg" height="500px">
		</div>
	</shiro:hasRole>

	<script type="text/javascript">
	
		function XBBhide(){
			$("#xbb").attr("style","display:none");
		}
		
		function XBBshow(){
			$("#xbb").attr("style","display:inline;float:left;");
		}
	
	</script>
</body>
</html>