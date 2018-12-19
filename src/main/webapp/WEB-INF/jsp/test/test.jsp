<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<link rel="stylesheet" href="/tool/css/layui.css">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script language="javascript" src="/tool/jquery-1.9.0.js"></script>
<script language="javascript" src="/tool/layui.js"></script>
<script language="javascript" src="/tool/js/formSetData.js"></script>
</head>
<body>
	<h2 align="center" style="color: red;font-weight:bold;">难受，巨难受。</h2>
	<!-- datagrig列表 -->
	<div class="layui-fluid">
				<div class="layui-card">
					<div class="Table layui-card-body" style="width:580px;">
						<!-- 按钮 -->
						<span class="layui-btn layui-btn-primary layui-btn-sm" data-type="add">
							<i class="layui-icon">&#xe61f;</i>增加
						</span>
						<span class="layui-btn layui-btn-sm" data-type="Export">
							<i class="layui-icon">&#xe601;</i>导出excel数据
						</span>
						<span class="layui-btn layui-btn-sm" data-type="SomeExport">
							<i class="layui-icon">&#xe601;</i>导出部分excel数据
						</span>
						<span class="layui-btn layui-btn-sm" data-type="DeleteMany">
							<i class="layui-icon">&#xe601;</i>批量删除
						</span>
						<!-- grid列表 -->
						<table id="datagrid" class="layui-table" lay-filter="Grid"></table>
					</div>
		</div>
	</div>
	<div id="lll">
	</div>
	<script language="javascript">
	
		
		$.ajax({
			url: "/listPerson",
			type: "POST",
			async: false,
			cache: false,
			success: function(str){
				var l = '';
				for(var i = 0;i<str.length;i++){
					l += "<div><label>姓名</label><input width=200px value='"+str[i].name+"' readonly=readonly><label>年龄</label><input width=200px value='"+str[i].age+"' readonly=readonly></div>";
				}
				$("#lll").append(l);
			}
		});
		
		//加载Layui
		layui.use(['table'],function(){
			var table = layui.table;
			
			//获取参数
			//datagrid列表
			table.render({
				elem: '#datagrid',
				url: '/mapPerson',
			    cols: [[
					{type:'checkbox'},
					{type:'numbers', title: '序号', width:70},
					{title: '操作',align:'center',templet: '#Operation',width:240},
					{title: '姓名',width:100,templet:
						function(d){
							var str = "<a lay-event='Show' class='layui-table-link'>"+ d.name +"</a>";
							return str;
						},	
					},
					{field:'age', title: '年龄',sort:true},
				]],
			    id:'grid',
			});
			
			//grid列表操作
			var $ = layui.$,active = {
				//添加质量奖
				add: function(){
					parent.layer.open({
						title: "测试添加页面",
						type: 2,
						maxmin: true,
						shade: [0.8, '#393D49'], // 遮罩  不要可以不写 或默认 0 
						shift: 5, // 渐显  
						area: ['740px', '280px'],
						content: '/testEdit?type='+"add",
						end: function(){
							location.reload();
			  			}
					});
				},
				//导出为excel
				Export: function(){
					Data = JSON.stringify(table.cache.grid);
					parent.layer.msg('数据正在导出中，请稍等...',{icon: 16,shade:0.4},function(){
						$.ajax({
							url:'/ExcelExport',
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
								        content: '/testExcelExport?path='+result.trim(),
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
				//部分数据，导出为excel
				SomeExport: function(){
					var checkStatus = table.checkStatus('grid'),
					data = checkStatus.data;
					Data = JSON.stringify(data);
					if(data.length == 0){
						parent.layer.msg("请勾选要导出的数据",{icon: 5,anim: 6});
	            		return;
					}else{
						parent.layer.msg('数据正在导出中，请稍等...',{icon: 16,shade:0.4},function(){
							$.ajax({
								url:'/ExcelExport',
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
									        content: '/testExcelExport?path='+result.trim(),
										});
										parent.layer.closeAll();
									}else{
									  location.reload();
									}
								},
							});
							return false;
						});
					}
				},
				//批量删除
				DeleteMany: function(){
					var checkStatus = table.checkStatus('grid'),
					data = checkStatus.data;
					var ids= [];
					if(data.length == 0){
						parent.layer.msg("请勾选要导出的数据",{icon: 5,anim: 6});
	            		return;
					}else{
						for(var i=0;i<data.length;i++){
	 						var r = data[i];
	 						ids.push(r.id);
						}
						var id = ids.join(',');
						layer.confirm('确定批量删除吗？',{btn:['确定','取消']},function(){
							$.ajax({
								url : "/DeleteMany",
								data: {ids:id},
								type: "POST",
								cache: false,
								async: false,
								success: function(arg){
									if(arg == data.length){
										layer.alert("批量删除成功！",{icon: 1, anim: 6},function(index){
											location.reload();
										});
									}else if(arg == -1){
										layer.alert("批量删除失败！请重试！",{icon: 2, anim: 6},function(index){
											location.reload();
										});
									}
								},
							})
						});
					}
				},
			}
			
			table.on('tool(Grid)', function(obj){
				var data = obj.data;
				var layevent = obj.event;
				
				if(layevent == "Edit"){
					parent.layer.open({
						title: "测试编辑页面",
			  			type: 2,
			  			maxmin: true,
						shade: [0.8, '#393D49'], // 遮罩  不要可以不写 或默认 0 
						shift: 5, // 渐显  
			  			area: ['740px', '280px'],
			  			content: '/testEdit?type='+"edit",
			  			success:function(layero) {
			  				var Data = JSON.stringify(data);
			  				new formSetData({Type:"2",Object:Data});
							/* var childname = $("#name", layero.find("iframe")[0].contentWindow.document);
							childname.attr("value", data.name );
							var childage = $("#age", layero.find("iframe")[0].contentWindow.document);
							childage.attr("value", data.age );
							var childid = $("#id", layero.find("iframe")[0].contentWindow.document);
							childid.attr("value", data.id ); */
			  			},
			  			end: function(){
							location.reload();
			  			}
					});
				}if(layevent == "Show"){
					parent.layer.open({
						title: "测试查看页面",
			  			type: 2,
			  			maxmin: true,
						shade: [0.8, '#393D49'], // 遮罩  不要可以不写 或默认 0 
						shift: 5, // 渐显  
			  			area: ['740px', '220px'],
			  			content: '/testEdit?type='+"show",
			  			success:function(layero) {
			  				var Data = JSON.stringify(data);
			  				new formSetData({Type:"2",Object:Data});
			  			},
					});
				}else if(layevent == "Delete"){
					parent.layer.confirm('确定删除吗？',{title: "系统提示",anim: 6,icon: 3,closeBtn: 0},function(){
						$.ajax({									
							url : "/deletePerson?id="+data.id,		
							type: "POST",								
							cache: false,							
							async: false,							
							success: function(arg){
								if(arg.trim() == "yes"){
									parent.layer.alert("删除成功！",{icon: 1, anim: 6},function(index){
										location.reload();
										parent.layer.closeAll();
									});
								}else{
									parent.layer.alert(arg,{icon: 2, anim: 6});
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
			
		});
	</script>
	
	<!-- 操作按钮 -->
	<script type="text/html" id="Operation">
		<span class="layui-btn layui-btn-normal layui-btn-xs" lay-event="Edit">编辑</span>
		<span class="layui-btn layui-btn-danger layui-btn-xs" lay-event="Delete">删除</span>
	</script>
	
</body>

</html>