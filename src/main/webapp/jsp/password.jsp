<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="org.apache.shiro.SecurityUtils"%>
<%@page import="org.apache.shiro.subject.Subject"%>
<%@page import="com.test.model.user"%>
<%@include file="views/public/public.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改密码</title>
</head>
<body style="background-color: pink;">
	<h2 style="color: #ff0000;" align="center">难受还是我难受啊</h2>
	<div
		class="layadmin-user-login-box layadmin-user-login-body layui-form"  style="width: 33%;margin-left: 33%;margin-top: 10%;">
		<div class="layui-form-item" align="center">
			<label class="layadmin-user-login-icon layui-icon layui-icon-username" style="color: #1E9FFF;"></label>
			<input type="text" name="username" id="username" lay-verify="required" placeholder="用户名" class="layui-input">
		</div>
		<div class="layui-form-item" align="center">
			<label class="layadmin-user-login-icon layui-icon layui-icon-password" style="color: #1E9FFF;"></label>
			<input type="password" name="password" id="password" lay-verify="required" placeholder="原密码" class="layui-input">
		</div>
		<div class="layui-form-item" align="center">
			<label class="layadmin-user-login-icon layui-icon layui-icon-password" style="color: #1E9FFF;"></label>
			<input type="password" name="newpassword" id="newpassword" lay-verify="required" placeholder="新密码" class="layui-input">
		</div>
		<div class="layui-form-item" align="center">
			<label class="layadmin-user-login-icon layui-icon layui-icon-auz" style="color: #1E9FFF;"></label>
			<div class="layui-form-item">
				<input type="text" name="imagecode" id="imagecode" lay-verify="required" placeholder="请输入验证码" class="layui-input" style="width:75%;float: left;">
				<img id="img" alt="验证码" title="点击更换验证码" src="<%=basePath%>imageCode" style="width: 20%;height: 5.2%;margin-left: 5%;" onclick="imageCode()"/>
			</div>
		</div>
		<div class="layui-form-item" align="center">
			<span class="layui-btn layui-btn-normal" lay-submit lay-filter="submit">确认修改</span>
		</div>
		<hr>
		<p>
			<a href="<%=basePath%>jsp/login.jsp" class="layadmin-user-jump-change layadmin-link" style="margin-top:7px;">返回登录页面</a>
			<a href="<%=basePath%>jsp/zhuce.jsp" class="layadmin-user-jump-change layadmin-link" style="float: right;">新用户注册</a>
		</p>
	</div>

	<script type="text/javascript">
		
		//键盘事件，判断回车  
	    $('body').on('keydown',function(event){
			if(event.keyCode == 13){
				$(".layui-btn").click();
			}  
		});
		
		//引用Layui
		layui.use([ 'form' ], function() {
			//获取form表单信息
			var form = layui.form;

			//监听提交
			form.on('submit(submit)', function(data) {

				$.ajax({
					url : "<%=basePath%>updatePassword",
					data : data.field,
					type : "POST",
					cache : false,
					async : false,
					success : function(res) {
						if (res == "success") {
							//登入成功的提示与跳转
							parent.layer.alert("修改密码成功！",{title: "系统提示",icon: 1,closeBtn: 0},function(){
								cancel();
								location.href = "<%=basePath%>jsp/login.jsp";
							});
						} else {
							parent.layer.msg(res, {
								icon : 5,
								anim : 6
							});
						}
					},
				});
				return false;
			});

		});
		//取消按钮
		function cancel(){
			parent.layer.closeAll();
		};
		
		function imageCode(){
			$("#img").attr("src","<%=basePath%>imageCode?date="+Math.random())
		};
	</script>
</body>
</html>