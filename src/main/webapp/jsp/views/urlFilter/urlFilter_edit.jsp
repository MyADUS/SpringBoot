<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../public/public.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
</head>
<body>
	<div class="layui-form" style="padding: 20px 30px 0 0;">
		<!-- 隐藏字段 -->
		<input name="id" class="layui-input layui-hide">
		
		<div class="layui-form-item">
			<label class="layui-form-label" style="width: 120px;">拦截方法</label>
			<div class="layui-input-inline">
       			<input name="filter" placeholder="拦截方法" style="width:220px" class="layui-input" lay-verify="required">
			</div>
			<label class="layui-form-label">排序</label>
			<div class="layui-input-inline">
       			<input name="px" placeholder="排序" style="width:222px" class="layui-input" lay-verify="isnumber">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label" style="width: 120px;">拦截路径</label>
			<div class="layui-input-inline">
       			<input name="url" placeholder="拦截路径" style="width:533px" class="layui-input" lay-verify="required">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label" style="width: 120px;">备注</label>
			<div class="layui-input-inline" style="width:533px" >
       			<textarea name="bz" placeholder="请输入内容" class="layui-textarea" lay-verify="required"></textarea>
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
		
		var type = "<%=request.getParameter("type")%>";
		if(type == "show"){
			$("#btn").hide();
		}
		
		form.verify({  
			isnumber: function(value){
				var reg = /^[1-9]\d*$|^0$/;
				if(reg.test(value) == false){
					return "请输入正确的数字";
				}
			},
		});
		
		//监听提交
		form.on('submit(submit)', function(data){
			//防止重复增加
			$(".layui-btn layui-btn-normal").addClass("layui-btn-disabled");
			
			$.ajax({
				url: "<%=basePath%>updateOrAddUrlFilter",
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
