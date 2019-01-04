<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="views/public/public.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style type="text/css">
/* .layui-form-checkbox span {
height: 0%;
} */
.layui-form-checkbox[lay-skin=primary] i {
top:0;
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录页面</title>
</head>
<body>
	<div class="layadmin-user-login-box layadmin-user-login-body layui-form" style="width: 33%;margin-left: 33%;margin-top: 10%;">
		<div class="layui-form-item" align="center">
			<label class="layadmin-user-login-icon layui-icon layui-icon-username" style="color: #1E9FFF;"></label>
			<input type="text" name="username" id="username" lay-verify="required" placeholder="用户名" class="layui-input">
		</div>
		<div class="layui-form-item" align="center">
			<label class="layadmin-user-login-icon layui-icon layui-icon-password" style="color: #1E9FFF;"></label>
			<input type="password" name="password" id="password" lay-verify="required" placeholder="密码" class="layui-input" align="middle">
		</div>
		<div class="layui-form-item" align="center">
			<label class="layadmin-user-login-icon layui-icon layui-icon-auz" style="color: #1E9FFF;"></label>
			<div class="layui-form-item">
				<input type="text" name="imagecode" id="imagecode" lay-verify="required" placeholder="请输入验证码" class="layui-input" style="width:75%;float: left;">
				<img id="img" alt="验证码" title="点击更换验证码" src="<%=basePath%>noneed/imageCode" style="width: 20%;height: 5.2%;margin-left: 5%;" onclick="imageCode()"/>
			</div>
		</div>
		<!-- <div class="layui-form-item" style="float: right;">
			<label class="layui-form-label">记住密码</label>
			<div class="layui-input-block">
				<input type="checkbox" name="rememberme" lay-skin="primary"> 
			</div>
		</div> -->
		<div class="layui-form-item" align="center">
			<span id="submit" class="layui-btn layui-btn-normal" lay-submit lay-filter="submit">登录</span>
		</div>
		<hr>
		<p>
			<a href="<%=basePath%>jsp/password.jsp" class="layadmin-user-jump-change layadmin-link" style="margin-top:7px;">修改密码？</a>
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
		layui.use(['form'],function(){
			//获取form表单信息
			var form = layui.form;
			
			//监听提交
			form.on('submit(submit)', function(data){
				
				//点击按钮之后，调用刷新验证码方法，防止多次点击按钮，多次调用登录方法
				$("#submit").attr("disabled",true);
				
				$.ajax({
					url: "<%=basePath%>noneed/toLogin",
					data: data.field,
					type: "POST",
					cache: false,
					async: false,
					success: function(res){
						if(res == "yes"){
							//登入成功的提示与跳转
							parent.layer.msg("正在登录，请稍等...",{icon: 16,time: 2500},function(value){
								location.href = "<%=basePath%>jsp/views/index.jsp";
							});
						}else{
							imageCode();
							parent.layer.msg(res,{icon: 5, anim: 6});
						}
					},
				});
				return false;
			});
			
		});
		
		function imageCode(){
			$("#img").attr("src","<%=basePath%>noneed/imageCode?date="+Math.random())
		};
	</script>
</body>
</html>
