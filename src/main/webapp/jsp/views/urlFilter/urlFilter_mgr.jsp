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
	<div class="layui-fluid">
		<div class="layui-card">
			<div class="layui-col-md12 layui-col-space10">
				<div class="Table layui-card-body">
					<!-- 按钮 -->
					<shiro:hasRole name="2">
						<span class="layui-btn layui-btn-primary layui-btn-sm" data-type="add">
							<i class="layui-icon">&#xe61f;</i>增加
						</span>
					</shiro:hasRole>
					<!-- grid列表 -->
					<table id="datagrid" class="layui-table" lay-filter="Grid"></table>
				</div>
			</div>
		</div>
	</div>
	<script language="javascript">
		// 加载layui
		layui.use(['table'],function(){
			var table = layui.table;
			
			// datagrid列表
			table.render({
				id: 'grid',
				elem: '#datagrid',
				url: '<%=basePath%>getUrlFilter',
				cols: [[
					{type: 'checkbox'},
					{type: 'numbers', title: '序号'},
					{title: '操作', align: 'center', templet: '#Operation'},
					{field: 'url', title: '拦截路径', align: 'center'},
					{field: 'filter', title: '拦截方法', align: 'center'},
					{field: 'czr', title: '操作人', align: 'center'},
					{field: 'czrq', title: '操作日期', align: 'center'},
				]],
				page: true,
			});
			
			//grid列表操作
			var $ = layui.$,active = {
				// 添加操作
				add:function(){
					parent.layer.open({
						title: "增加新的拦截信息",
						type: 2,
						maxmin: true,
						shade: [0.8, '#393D49'], // 遮罩  不要可以不写 或默认 0
						shift: 5, // 渐显
						area: ['740px', '300px'],
						content: '<%=basePath%>jsp/views/urlFilter/urlFilter_edit.jsp?type='+"add",
						end: function(){
							location.reload();
			  			}
					});
				},
			};
			
			table.on('tool(Grid)', function(obj){
				var data = obj.data;
				var layevent = obj.event;
				
				if(layevent == "Show"){
					parent.layer.open({
						title: "查看详情信息",
						type: 2,
						maxmin: true,
						shade: [0.8, '#393D49'], // 遮罩  不要可以不写 或默认 0
						shift: 5, // 渐显
						area: ['740px', '230px'],
						content: '<%=basePath%>jsp/views/urlFilter/urlFilter_edit.jsp?type='+"show",
						success:function(layero) {
			  				var Data = JSON.stringify(data);
			  				new formSetData({Type:"2",Object:Data});
			  			},
					});
				}else if(layevent == "Edit"){
					parent.layer.open({
						title: "修改新的拦截信息",
						type: 2,
						maxmin: true,
						shade: [0.8, '#393D49'], // 遮罩  不要可以不写 或默认 0
						shift: 5, // 渐显
						area: ['740px', '300px'],
						content: '<%=basePath%>jsp/views/urlFilter/urlFilter_edit.jsp?type='+"edit",
						success:function(layero) {
			  				var Data = JSON.stringify(data);
			  				new formSetData({Type:"2",Object:Data});
			  			},
						end: function(){
							location.reload();
			  			}
					});
				}else if(layevent == "Delete"){
					parent.layer.confirm('确认删除吗',{title: "系统提示",anim: 6,icon: 3,closeBtn: 0},function(){
						$.ajax({
							url : "<%=basePath %>deleteUrlFilter",		
							data: {id:data.id},							
							type: "POST",								
							cache: false,							
							async: false,							
							success: function(arg){			
								if(arg.trim() == "ok"){
									parent.layer.alert("删除成功！",{icon: 1, anim: 6},function(){
										location.reload();
										parent.layer.closeAll();
									});
								}else{
									parent.layer.alert(arg.msg,{icon: 2, anim: 6});
								}
							},
						})
					});
				}
			});
			
			//控制按钮点击
			$('.Table .layui-btn').on('click', function(){
				var type = $(this).data('type');
				active[type] ? active[type].call(this) : '';
			});
		})
	</script>
	
	<!-- 操作按钮 -->
	<shiro:hasRole name="1">
		<script type="text/html" id="Operation">
			<span class="layui-btn layui-btn-xs" lay-event="Show">查看</span>
		</script>
	</shiro:hasRole>
	<shiro:hasRole name="2">
		<script type="text/html" id="Operation">
			<span class="layui-btn layui-btn-xs" lay-event="Show">查看</span>
			<span class="layui-btn layui-btn-normal layui-btn-xs" lay-event="Edit">编辑</span>
			<span class="layui-btn layui-btn-danger layui-btn-xs" lay-event="Delete">删除</span>
		</script>
	</shiro:hasRole>
</body>
</html>
