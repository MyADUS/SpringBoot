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
	<div class="layui-form" style="padding: 20px 30px 0 0;">
		<!-- 隐藏字段 -->
		<input id="id" name="id" class="layui-input layui-hide"/>
	
		<div class="layui-form-item">
			<label class="layui-form-label" style="width: 120px;">真实姓名</label>
			<div class="layui-input-inline">
       			<input id="name" name="name" placeholder="姓名" style="width:533px" class="layui-input" lay-verify="required">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label" style="width: 120px;">年龄</label>
			<div class="layui-input-inline">
       			<input id="age" name="age" placeholder="年龄" style="width:533px" class="layui-input" lay-verify="age">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label" style="width: 120px;">性别</label>
			<div class="layui-input-inline" style="width:533px;">
				<select name="sex">
					<option value="">性别</option>
					<option value="1">男</option>
					<option value="0">女</option>
				</select>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label" style="width: 120px;">生日</label>
			<div class="layui-input-inline">
       			<input id="birth" name="birth" style="width:533px;" class="layui-input" lay-verify="date" placeholder="YYYY-MM-dd">
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
		layui.use(['form','laydate'],function(){
			//获取form表单信息
			var form = layui.form;
			var laydate = layui.laydate;
			
			/* var type = "${type}"; */
			<%-- var type = "<%=type %>"; --%>
			var type = "<%=request.getParameter("type")%>";
			var SEX = "<%=request.getParameter("SEX")%>";
			if(type == "show"){
				$("#btn").hide();
			}
			
			$("option[value='"+SEX+"']").attr("selected", "selected");
			form.render();
			
			laydate.render({
				elem: "#birth",
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
					url: "<%=basePath%>updateOrAdd",
					data: data.field,
					type: "POST",
					cache: false,
					async: false,
					success: function(res){
						if(res == "yes"){
							parent.layer.alert("保存成功！",{title: "系统提示",icon: 1,closeBtn: 0},function(){
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