<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../public/public.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>根据性别排表</title>
</head>
<body>
	<!-- datagrig操作按钮 -->
	<div class="layui-fluid">
		<div class="layui-row layui-col-space15">
			<div class="layui-col-md12">
				<div class="layui-card">
					<div class="Table layui-card-body">
						<!-- 按钮 -->
						<span class="layui-btn layui-btn-normal" data-type="Export">
							<i class="layui-icon">&#xe601;</i>导出excel数据
						</span>
						<!-- grid列表 -->
						<table id="datagrid" class="layui-table" lay-filter="Grid"></table>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<script type="text/javascript">
	
		//加载Layui
		layui.use(['table'],function(){
			var table = layui.table;
			
			var sex = "<%=request.getParameter("sex")%>";
			if(sex == "男"){
				sex = 1;
			}else{
				sex = 0;
			}
			
			//datagrid列表
			table.render({
				elem: '#datagrid',
				url: '<%=basePath%>do/getMapBySex?sex='+sex,
			    cols: [[
					{type:'numbers', title: '序号'},
					{field:'name', title: '姓名'},
					// 点击打开窗口，调用formSetData方法无法正常对form表单赋值
					/* {title: '姓名', templet:
						function(d){
							var str = "<a lay-event='Show' class='layui-table-link'>"+ d.name +"</a>";
							return str;
						},
					}, */
					{field:'sex', title: '性别',templet: '#Sex'},
					{field:'birth', title: '生日',templet:'<div>{{toDateString(d.birth,"yyyy-MM-dd")}}</div>'},
					{field:'age', title: '年龄',sort:true},
				]],
			    id:'grid',
			    height: 'full-98',
			});
			
			//grid列表操作
			var $ = layui.$,active = {
					
				//导出为excel
				Export: function(){
					Data = JSON.stringify(table.cache.grid);
					parent.layer.msg('数据正在导出中，请稍等...',{icon: 16,shade:0.4},function(){
						$.ajax({
							url:'<%=basePath%>do/ExcelExport',
							data:{data:Data},
							type: "POST",
							cache: false,
							async: false,
							success: function(result){
								if(result != "-1"){// 下载 
									parent.layer.open({	
								        type: 2 ,
								        area: ['680px', '580px'],
								        shade: 0.5,
								        content: 'Excel_Export_Download.jsp?path='+result.trim(),
									});
									parent.layer.closeAll();
								}else{
								  location.reload();
								}
							},
						});
						return false;
					});
				},
			};
			
			table.on('tool(Grid)', function(obj){
				var data = obj.data;
				var layevent = obj.event;
				
				if(layevent == "Show"){
					parent.layer.open({
						title: "测试查看页面",
			  			type: 2,
			  			maxmin: true,
						shade: [0.8, '#393D49'], // 遮罩  不要可以不写 或默认 0 
						shift: 5, // 渐显  
			  			area: ['740px', '400px'],
			  			content: '<%=basePath%>jsp/views/test/test_edit.jsp?type='+"show"+"&SEX="+data.sex,
			  			success:function(layero) {
			  				var Data = JSON.stringify(data);
			  				new formSetData({Type:"2",Object:Data});
			  			},
					});
				}
				
			});
			
			//控制按钮点击
			$('.Table .layui-btn').on('click', function(){
				var type = $(this).data('type');
				active[type] ? active[type].call(this) : '';
			});
		});
	</script>
	
	<!-- 性别 -->
	<script type="text/html" id="Sex">
		{{#if(d.sex == 0){ }}
			<lable>女</span>
  		{{#  }else if(d.sex == 1){ }}
			<lable>男</span>
		{{#  } }}
	</script>
</body>
</html>
