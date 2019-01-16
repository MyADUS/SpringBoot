<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- <%
	String type = request.getParameter("type");
%> --%>
<%@include file="../public/public.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<div class="layui-form" lay-filter="form" style="padding: 20px 30px 0 0;">
		<!-- 隐藏字段 -->
		<input id="id" name="id" class="layui-input layui-hide"/>
		<input name="password" class="layui-input layui-hide"/>
		<input name="manager" class="layui-input layui-hide"/>
	
		<div class="layui-form-item">
			<label class="layui-form-label" style="width: 120px;">真实姓名</label>
			<div class="layui-input-inline">
       			<input id="truename" name="truename" placeholder="姓名" style="width:400px" class="layui-input" lay-verify="required">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label" style="width: 120px;">用户名</label>
			<div class="layui-input-inline">
       			<input id="username" name="username" placeholder="用户名" style="width:400px" class="layui-input" lay-verify="required">
			</div>
		</div>
		<br/>
		<br/>
		<!-- 底部按钮 -->
		<div id="btn" class="layui-form-item">
			<div style="text-align:center;">
				<span class="layui-btn layui-btn-normal" lay-submit lay-filter="submit">保存</span>
				<span class="layui-btn layui-btn-primary" onclick="cancel()">取消</span>
			</div>
		</div>
	</div>
	
	<script type="text/javascript">
		
		
		//引用Layui
		layui.use(['form'],function(){
			//获取form表单信息
			var form = layui.form;
			var $ = layui.$;
			
			//查询个人信息
			$.ajax({
				url: "<%=basePath%>noneed/getUserself",
				type: "post",
				cache: false,
				async: false,
				success: function(reg){
					form.val("form",reg);
				}
			});
			
			form.verify({  
				age: function(value){
					if(value){
						if(parseInt(value) < 18 || parseInt(value) > 100){
							return "请输入正确的年龄";
						}
					}else{
						return "请输入正确的年龄";
					}
				},
			});
			
			//监听提交
			form.on('submit(submit)', function(data){
				//防止重复增加
				$(".layui-btn layui-btn-normal").addClass("layui-btn-disabled");
				
				$.ajax({
					url: "<%=basePath%>do/updateOrAddUser",
					data: data.field,
					type: "POST",
					cache: false,
					async: false,
					success: function(res){
						if(res == "yes"){
							parent.layer.alert("保存成功！请刷新页面，重新登录。",{title: "系统提示",icon: 1,closeBtn: 0},function(){
								cancel();
							});
						}else{
							parent.layer.alert("保存失败！",{title: "系统提示",icon: 2,closeBtn: 0});
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
		
	</script>

</body>
</html>
