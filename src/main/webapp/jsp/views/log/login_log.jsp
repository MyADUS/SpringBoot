<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../public/public.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录信息日志</title>
</head>
<body>
	<!-- 搜索文本框-->
	<div class="Table" style="text-align:center;margin-top:5px;">
		<div class="layui-inline">
			<input id="Information" class="layui-input" placeholder="请输入查询条件（支持：用户名/IP地址）" style="width:620px" autocomplete="off">
		</div>
		<br/>
		<div class="layui-inline" style="margin-top:5px;">
			<span class="layui-btn" data-type="search">
				<i class="layui-icon">&#xe615;</i>搜索
			</span>
			<span class="layui-btn layui-btn-primary" data-type="empty">
				<i class="layui-icon">&#xe639;</i>清空查询条件
			</span>
		</div>
	</div>
	<!-- datagrig列表 -->
	<div class="layui-fluid">
		<div class="layui-card">
			<div class="layui-col-md12 layui-col-space10">
				<div class="Table layui-card-body">
					<!-- grid列表 -->
					<table id="datagrid" class="layui-table" lay-filter="Grid"></table>
				</div>
			</div>
			<div class="layui-col-md12 layui-col-space10">
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	//加载Layui
	layui.use(['table'],function(){
		var table = layui.table;
		
		//获取参数
		//datagrid列表
		table.render({
			elem: '#datagrid',
			url: '<%=basePath%>loginlog',
		    cols: [[
				{type:'numbers', title: '序号'},
				{field:'czr', title: '用户名'},
				{field:'ip', title: 'IP地址'},
				{field:'method', title: '操作方法'},
				{field:'czrq', title: '登录时间',sort:true},
			]],
		    id:'grid',
		    page: true
		});
		
		//grid列表操作
		var $ = layui.$,active = {
			//查询
			search: function(){
		      	//执行重载
		      	var Information = $("#Information").val(); 
		      	table.reload('grid',{
	        		page: {curr: 1},
		        	where: {param: Information},
		      	});
		    },
			//清空查询条件
			empty: function(){
				$("#Information").val(""); 
			},
		}
		table.on('tool(Grid)', function(obj){
			var data = obj.data;
			var layevent = obj.event;
			
		});
		//控制按钮点击
		$('.Table .layui-btn').on('click', function(){
			var type = $(this).data('type');
			active[type] ? active[type].call(this) : '';
		});
	});

</script>
</html>
