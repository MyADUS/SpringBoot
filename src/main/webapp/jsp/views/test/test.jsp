<%@page import="org.apache.shiro.SecurityUtils"%>
<%@page import="org.apache.shiro.subject.Subject"%>
<%@page import="com.test.model.user"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	Subject subject = SecurityUtils.getSubject();
	user user = (user)subject.getPrincipal();
	String username = user.getUsername();
	String truename = user.getTruename();
	String manager = user.getManager();
%>
<%@include file="../public/public.jsp" %>
<%-- <%
	String username = request.getSession().getAttribute("username").toString();
%> --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style>
	body{
		overflow-y: scroll;
	}
</style>
</head>
<body>
	<h3 align="center" style="color: red;font-weight:bold;">你好啊，我的宝贝------<%=truename %>
		<!-- 注销按钮 -->
		<!-- <button id="logout" class="layui-btn layui-btn-sm layui-btn-danger"><i class="layui-icon">&#xe617;</i></button> -->
	</h3>
	<h2 align="center" style="color: red;font-weight:bold;">难受，巨难受。</h2>
	<!-- datagrig列表 -->
	<div class="layui-fluid">
		<div class="layui-card">
			<div class="layui-col-md12 layui-col-space10">
				<div class="Table layui-card-body">
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
					<shiro:hasAnyRoles name="1,2">
						<span class="layui-btn layui-btn-sm" data-type="DeleteMany">
							<i class="layui-icon">&#xe601;</i>批量删除
						</span>
						<!-- <span class="layui-btn layui-btn-sm" data-type="rePassword">
							<i class="layui-icon">&#xe601;</i>批量重置密码
						</span> -->
						<shiro:hasRole name="2">
							<!-- <span class="layui-btn layui-btn-primary layui-btn-sm" id="SQ" data-type="ShouQuan">
								升级管理<span class="layui-badge" id="SQDL"></span>
							</span> -->
							<span class="layui-btn layui-btn-sm" data-type="XBB">
								<i class="layui-icon">&#xe601;</i>小宝贝
							</span>
							<!-- <span class="layui-btn layui-btn-primary layui-btn-sm" data-type="ShouQuanMore">
								更多权限
							</span> -->
						</shiro:hasRole>
					</shiro:hasAnyRoles>
					<!-- grid列表 -->
					<table id="datagrid" class="layui-table" lay-filter="Grid"></table>
				</div>
			</div>
			<div class="layui-col-md12 layui-col-space10">
				<div id="lll">
				</div>
			</div>
		</div>
	</div>
	<script language="javascript">
	
		/* $.ajax({
			url: "/JSPdemo/listPerson",
			type: "POST",
			async: false,
			cache: false,
			success: function(str){
				var l = '';
				for(var i = 0;i<str.length;i++){
					l += '<div><label>姓名</label><input width="200px" value="'+str[i].name+'" readonly="readonly"><label>年龄</label><input width="200px" value="'+str[i].age+'" readonly="readonly"></div>';
				}
				$("#lll").append(l);
			}
		}); */
		
		//需登录权限的人数	即manager=0
		/* var SQDLNUMS = 0;
		$.ajax({
			url: "/JSPdemo/getCount0",
			type: "POST",
			async: false,
			cache: false,
			success: function(str){
				if(str != null){
					SQDLNUMS = str[0].NUMS;
				}
			}
		});
		$("#SQDL").html(SQDLNUMS);
		if(SQDLNUMS == 0){
			$("#SQ").attr("style","display: none;");
		} */
		
		//加载Layui
		layui.use(['table'],function(){
			var table = layui.table;
			
			//datagrid列表
			table.render({
				elem: '#datagrid',
				url: '<%=basePath%>do/mapPerson',
			    cols: [[
					{type:'checkbox'},
					{type:'numbers', title: '序号'},
					{title: '操作',align:'center',templet: '#Operation'},
					{title: '姓名',templet:
						function(d){
							var str = "<a lay-event='Show' class='layui-table-link'>"+ d.name +"</a>";
							return str;
						},	
					},
					{field:'sex', title: '性别',templet: '#Sex'},
					{field:'age', title: '年龄',sort:true},
					{field:'birth', title: '生日',templet:'<div>{{toDateString(d.birth)}}</div>'},
				]],
			    id:'grid',
			    page: true,
			    limit: 5,
			    limits: [5,10]
			});
			
			//grid列表操作
			var $ = layui.$,active = {
				//升级管理
				/* ShouQuan: function(){
					parent.layer.open({
						title: "需升级为管理员",
			  			type: 2,
			  			maxmin: true,
						shade: [0.8, '#393D49'], // 遮罩  不要可以不写 或默认 0 
						shift: 5, // 渐显  
			  			area: ['740px', '450px'],
			  			content: '/jsp/views/user/shouquan.jsp',
			  			end: function(){
							location.reload();
			  			}
					});
				}, */
				//更多权限
				/* ShouQuanMore: function(){
					parent.layer.open({
						title: "更多权限信息页面",
			  			type: 2,
			  			maxmin: true,
						shade: [0.8, '#393D49'], // 遮罩  不要可以不写 或默认 0 
						shift: 5, // 渐显  
			  			area: ['850px', '450px'],
			  			content: '/jsp/views/user/shouquanmore.jsp',
			  			end: function(){
							location.reload();
			  			}
					});
				}, */
				//添加质量奖
				add: function(){
					parent.layer.open({
						title: "测试添加页面",
						type: 2,
						maxmin: true,
						shade: [0.8, '#393D49'], // 遮罩  不要可以不写 或默认 0 
						shift: 5, // 渐显  
						area: ['740px', '450px'],
						content: '<%=basePath%>jsp/views/test/test_edit.jsp?type='+"add",
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
								        content: '<%=basePath%>jsp/views/test/Excel_Export_Download.jsp?path='+result.trim(),
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
									        content: '<%=basePath%>jsp/views/test/Excel_Export_Download.jsp?path='+result.trim(),
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
								url : "<%=basePath%>do/DeleteMany",
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
				//批量重置密码
				/* rePassword: function(){
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
						layer.confirm('确定批量重置密码吗？',{btn:['确定','取消']},function(){
							$.ajax({
								url : "/rePassword",
								data: {ids:id},
								type: "POST",
								cache: false,
								async: false,
								success: function(arg){
									if(arg == data.length){
										layer.alert("批量重置密码成功！",{icon: 1, anim: 6},function(index){
											location.reload();
										});
									}else if(arg == -1){
										layer.alert("批量重置密码失败！请重试！",{icon: 2, anim: 6},function(index){
											location.reload();
										});
									}
								},
							})
						});
					}
				}, */
				//？？？
				XBB: function(){
					parent.layer.open({
						title: "我的小宝贝呢！！！",
						type: 2,
						maxmin: true,
						shade: [0.8, '#393D49'], // 遮罩  不要可以不写 或默认 0 
						shift: 5, // 渐显  
						area: ['740px', '450px'],
						content: '<%=basePath%>jsp/views/test/test_pic.jsp',
					});
				},
			}
			
			table.on('tool(Grid)', function(obj){
				var data = obj.data;
				var layevent = obj.event;
				
				if(layevent == "Edit"){
					parent.layer.open({
						title: "修改姓名为"+data.name+"的页面",
			  			type: 2,
			  			maxmin: true,
						shade: [0.8, '#393D49'], // 遮罩  不要可以不写 或默认 0 
						shift: 5, // 渐显  
			  			area: ['740px', '450px'],
			  			content: '<%=basePath%>jsp/views/test/test_edit.jsp?type='+"edit"+"&SEX="+data.sex,
			  			success:function(layero) {
			  				var Data = JSON.stringify(data);
			  				new formSetData({Type:"2",Object:Data});
							/* var childname = $("#name", layero.find("iframe")[0].contentWindow.document);
							childname.attr("value", data.name );
							var childage = $("#age", layero.find("iframe")[0].contentWindow.document);
							childage.attr("value", data.age ); 
							var childid = $("#birth", layero.find("iframe")[0].contentWindow.document);
							childid.attr("value", data.birth.substring(0,10) );*/
							
			  			},
			  			end: function(){
							location.reload();
			  			}
					});
				}else if(layevent == "Show"){
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
			  				new formSetData({Type:"3",Object:Data});
			  			},
					});
				}else if(layevent == "Delete"){
					parent.layer.confirm('确定删除吗？',{title: "系统提示",anim: 6,icon: 3,closeBtn: 0},function(){
						$.ajax({									
							url : "<%=basePath%>do/deletePerson?id="+data.id,		
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
			
			/* $("#logout").click(function(){
				//注销操作
				parent.layer.confirm('确定注销吗？',{btn:['确定','取消']},function(){
					$.ajax({
						url : "/logout",
						type: "POST",
						cache: false,
						async: false,
						success: function(arg){
							location.href = "/jsp/login.jsp";
						},
					})
				});
			}); */
			
		});
	</script>
	
	<!-- 操作按钮 -->
	<script type="text/html" id="Operation">
		<span class="layui-btn layui-btn-normal layui-btn-xs" lay-event="Edit">编辑</span>
		<span class="layui-btn layui-btn-danger layui-btn-xs" lay-event="Delete">删除</span>
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
