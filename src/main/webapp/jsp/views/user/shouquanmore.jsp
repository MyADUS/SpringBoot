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
	String id = user.getId()+"";
%>
<%@include file="../public/public.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<h2 align="center" style="color: red;font-weight:bold;">想想还是我比较难受</h2>
	<!-- datagrig列表 -->
	<div class="layui-fluid">
		<div class="layui-card">
			<div class="layui-col-md12 layui-col-space10">
				<div class="Table layui-card-body">
					<span class="layui-btn layui-btn-sm" data-type="rePassword">
						<i class="layui-icon">&#xe601;</i>批量重置密码
					</span>
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
		
		var MANAger = '<%=manager %>';
		var TRUEname = '<%=truename %>';
		//获取参数
		//datagrid列表
		table.render({
			elem: '#datagrid',
			url: '<%=basePath%>mapManager',
		    cols: [[
		    	{type:'checkbox'},
				{type:'numbers', title: '序号'},
				{title: '操作',align:'center',templet: '#Operation',width:'260'},
				{title: '姓名',width:'120',templet:
					function(d){
						var str = "<a lay-event='Show' class='layui-table-link'>"+ d.truename +"</a>";
						return str;
					},	
				},
				{field:'username', title: '用户名',width:'120'},
				{field:'manager', title: '自行领悟吧',templet: '#Manager'},
			]],
		    id:'grid',
		});
		
		//grid列表操作
		var $ = layui.$,active = {
			//批量重置密码
			rePassword: function(){
				var checkStatus = table.checkStatus('grid'),
				data = checkStatus.data;
				var ids= [];
				if(data.length == 0){
					parent.layer.msg("请勾选要重置密码的数据",{icon: 5,anim: 6});
            		return;
				}else{
					for(var i=0;i<data.length;i++){
 						var r = data[i];
 						var Id = "<%=id %>";
 						if(r.id == Id){
 							parent.layer.msg("不能对自己重置密码！！！",{icon: 5,anim: 6});
 		            		return;
 						}else{
 							ids.push(r.id);
 						}
					}
					var id = ids.join(',');
					layer.confirm('确定批量重置密码吗？',{btn:['确定','取消']},function(){
						$.ajax({
							url : "<%=basePath%>rePassword",
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
			},
		}
		table.on('tool(Grid)', function(obj){
			var data = obj.data;
			var layevent = obj.event;
			
			if(layevent == "None"){
				if(MANAger == data.manager) {
					if(TRUEname == data.truename){
						parent.layer.msg("不能对本人进行权限操作！！！");
					}else{
						parent.layer.msg("级别相同，无法执行操作！！！");
					}
				}else{
					parent.layer.confirm('确定格式化吗？',{title: "系统提示",anim: 6,icon: 3,closeBtn: 0},function(){
						$.ajax({									
							url : "<%=basePath%>ShouQuan?id="+data.id+"&manager="+"0",		
							type: "POST",								
							cache: false,							
							async: false,							
							success: function(arg){
								if(arg.trim() == "yes"){
									parent.layer.alert("授权成功！",{icon: 1, anim: 6},function(index){
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
			}else if(layevent == "ShouQuan"){
				parent.layer.confirm('确定升级为管理员吗？',{title: "系统提示",anim: 6,icon: 3,closeBtn: 0},function(){
					$.ajax({									
						url : "<%=basePath%>ShouQuan?id="+data.id+"&manager="+"1",		
						type: "POST",								
						cache: false,							
						async: false,							
						success: function(arg){
							if(arg.trim() == "yes"){
								parent.layer.alert("升级成功！",{icon: 1, anim: 6},function(index){
									location.reload();
									parent.layer.closeAll();
								});
							}else{
								parent.layer.alert(arg,{icon: 2, anim: 6});
							}
						},
					})
				});
			}else if(layevent == "ShouQuanMore"){
				parent.layer.confirm('确定释放权限吗？',{title: "系统提示",anim: 6,icon: 3,closeBtn: 0},function(){
					$.ajax({									
						url : "<%=basePath%>ShouQuan?id="+data.id+"&manager="+"2",		
						type: "POST",								
						cache: false,							
						async: false,							
						success: function(arg){
							if(arg.trim() == "yes"){
								parent.layer.alert("授权成功！",{icon: 1, anim: 6},function(index){
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
		{{#if(d.manager == "0"){ }}
			<span class="layui-btn layui-btn-normal layui-btn-xs" lay-event="ShouQuan">升级管理</span>
			<span class="layui-btn layui-btn-warm layui-btn-xs" lay-event="ShouQuanMore">最高级</span>
		{{#  }else if(d.manager == "1"){ }}
			<span class="layui-btn layui-btn-danger layui-btn-xs" lay-event="None">格式化权限</span>
			<span class="layui-btn layui-btn-warm layui-btn-xs" lay-event="ShouQuanMore">最高级</span>
		{{#  }else if(d.manager == "2"){ }}
			<span class="layui-btn layui-btn-danger layui-btn-xs" lay-event="None">格式化权限</span>
		{{#  } }}
	</script>

	<!-- 性别 -->
	<script type="text/html" id="Manager">
		{{#if(d.manager == "0"){ }}
			<lable>0级小号</span>
		{{#  }else if(d.manager == "1"){ }}
			<lable>管理员</span>
		{{#  }else if(d.manager == "2"){ }}
			<lable>超级管理员</span>
		{{#  } }}
	</script>
</html>