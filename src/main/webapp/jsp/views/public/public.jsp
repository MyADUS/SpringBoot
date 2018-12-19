<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" +request.getServerPort() + path + "/";
%>

<!-- 引入shiro jsp标签库 -->
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!-- 引入layui css样式 -->
<link rel="stylesheet" href="<%=basePath%>tool/css/layui.css">

<!-- jQuery -->
<script language="javascript" src="<%=basePath%>tool/jquery-1.9.0.js"></script>
<!-- layui -->
<script language="javascript" src="<%=basePath%>tool/layui.js"></script>
<!-- 自定义js -->
<script language="javascript" src="<%=basePath%>tool/js/toDateString.js"></script>
<script language="javascript" src="<%=basePath%>tool/js/formSetData.js"></script>
<!-- echarts图表js -->
<script src="<%=basePath%>echarts/echarts.min.js"></script>
