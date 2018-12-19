<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="public/public.jsp" %>
<%@page import="org.apache.shiro.SecurityUtils"%>
<%@page import="org.apache.shiro.subject.Subject"%>
<%@page import="com.test.model.user"%>
<%
	Subject subject = SecurityUtils.getSubject();
	user user = (user)subject.getPrincipal();
	String username = user.getUsername();
	String truename = user.getTruename();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<link rel="stylesheet" href="<%=basePath%>tool/css/admin.css">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body class="layui-layout-body">
	<div id="LAY_app">
		<div class="layui-layout layui-layout-admin">
			<div class="layui-header">
				<!-- 头部区域 -->
				<ul class="layui-nav layui-layout-left">
					<li class="layui-nav-item layadmin-flexible" lay-unselect>
						<a href="javascript:;" layadmin-event="flexible" title="侧边伸缩">
							<i class="layui-icon layui-icon-shrink-right" id="LAY_app_flexible"></i>
						</a>
					</li>
					<li class="layui-nav-item layui-hide-xs" lay-unselect>
						<a href="fly/views/index.jsp" target="_blank" title="前台">
							<i class="layui-icon layui-icon-website"></i>
						</a>
					</li>
					<li class="layui-nav-item" lay-unselect>
						<a href="javascript:;" layadmin-event="refresh" title="刷新">
							<i class="layui-icon layui-icon-refresh-3"></i>
						</a>
					</li>
				</ul>
				<ul class="layui-nav layui-layout-right" lay-filter="layadmin-layout-right">
					<!-- 登录用户 -->
					<li style="float:left; line-height:50px;">
						<a href="javascript:;" style="cursor:default">
							<i class="layui-icon layui-icon-biaoqing" style="color:#00AA00;">&nbsp;&nbsp;<%=truename %>，您好！</i>
						</a>
					</li>
					<!-- 个人信息 -->
					<li style="cursor:pointer;" class="layui-nav-item layui-hide-xs" lay-unselect>
						<a onclick="Personal()" title="个人信息">
							<i class="layui-icon layui-icon-zzaddress-book-o"></i>
						</a>
					</li>
					<!-- 修改密码 -->
					<li style="cursor:pointer;" class="layui-nav-item layui-hide-xs" lay-unselect>
						<a onclick="Password()" title="修改密码">
							<i class="layui-icon layui-icon-password"></i>
						</a>
					</li>
					<!-- 注销退出系统 -->
					<li style="cursor:pointer;" class="layui-nav-item layui-hide-xs" lay-unselect>
						<a onclick="Logout()" title="注销退出">
							<i class="layui-icon layui-icon-zzpower-off"></i>
						</a>
					</li>
					<shiro:hasRole name="2">
						<!-- 清除缓存数据 -->
						<li style="cursor:pointer;" class="layui-nav-item layui-hide-xs" lay-unselect>
							<a onclick="removeRedis()" title="清除缓存数据">
								<i class="layui-icon layui-icon-delete"></i>
							</a>
						</li>
					</shiro:hasRole>
				</ul>
			</div>
      
			<!-- 侧边菜单 -->
			<div class="layui-side layui-side-menu">
				<div class="layui-side-scroll">
					<!-- 标题 -->
					<div class="layui-logo">
						<span>可以说比较难受了</span>
					</div>
          			<!-- 菜单 -->
					<ul class="layui-nav layui-nav-tree" lay-shrink="all" id="LAY-system-side-menu" lay-filter="layadmin-system-side-menu">
						<shiro:hasAnyRoles name="1,2">
			           		<li class="layui-nav-item">
								<a href="javascript:;" lay-tips="管理员单元" lay-direction="2">
									<i class="layui-icon">&#xe770;</i>
									<cite>管理员单元</cite>
								</a>
								<shiro:hasAnyRoles name="1,2">
									<shiro:hasRole name="2">
										<dl class="layui-nav-child">
											<dd><a lay-href="<%=basePath%>jsp/views/user/shouquanmore.jsp">超级管理员操作</a></dd>
										</dl>
									</shiro:hasRole>
									<dl class="layui-nav-child">
										<dd><a lay-href="<%=basePath%>jsp/views/user/shouquan.jsp">管理员操作</a></dd>
									</dl>
								</shiro:hasAnyRoles>
			           		</li>
			           	</shiro:hasAnyRoles>
						<li class="layui-nav-item">
							<a href="javascript:;" lay-tips="一级菜单" lay-direction="2">
								<i class="layui-icon">&#xe656;</i>
								<cite>一级菜单</cite>
							</a>
							<dl class="layui-nav-child">
								<dd>
									<a>二级菜单</a>
									<dl class="layui-nav-child">
										<dd>
											<a href="javascript:alert('并没有三级菜单');" style="text-align: left;">三级菜单</a>
												<dl class="layui-nav-child">
													<dd>
														<a href="javascript:alert('并没有四级菜单');" style="text-align: left;margin-left: 18px;">四级菜单</a>
													</dd>
												</dl>
										</dd>
									</dl>
								</dd>
							</dl>
							<dl class="layui-nav-child">
								<dd><a lay-href="<%=basePath%>jsp/views/test/test.jsp">测试单元</a></dd>
							</dl>
							<dl class="layui-nav-child">
								<dd><a lay-href="<%=basePath%>jsp/views/test/test_echart.jsp">图表</a></dd>
							</dl>
		           		</li>
		           		<li class="layui-nav-item">
							<a href="javascript:;" lay-tips="文件上传" lay-direction="2">
								<i class="layui-icon">&#xe67c;</i>
								<cite>文件上传</cite>
							</a>
							<dl class="layui-nav-child">
								<dd><a lay-href="<%=basePath%>jsp/upload.jsp">文件上传</a></dd>
							</dl>
		           		</li>
					</ul>
				</div>
			</div>

			<!-- 页面标签 -->
			<div class="layadmin-pagetabs" id="LAY_app_tabs">
				<div class="layui-icon layadmin-tabs-control layui-icon-prev" layadmin-event="leftPage"></div>
				<div class="layui-icon layadmin-tabs-control layui-icon-next" layadmin-event="rightPage"></div>
				<div class="layui-icon layadmin-tabs-control layui-icon-down">
					<ul class="layui-nav layadmin-tabs-select" lay-filter="layadmin-pagetabs-nav">
						<li class="layui-nav-item" lay-unselect>
							<a href="javascript:;"></a>
							<dl class="layui-nav-child layui-anim-fadein">
								<dd layadmin-event="closeThisTabs"><a href="javascript:;">关闭当前标签页</a></dd>
								<dd layadmin-event="closeOtherTabs"><a href="javascript:;">关闭其它标签页</a></dd>
								<dd layadmin-event="closeAllTabs"><a href="javascript:;">关闭全部标签页</a></dd>
 							</dl>
						</li>
					</ul>
				</div>
				<div class="layui-tab" lay-unauto lay-allowClose="true" lay-filter="layadmin-layout-tabs">
					<ul class="layui-tab-title" id="LAY_app_tabsheader">
						<li lay-id="<%=basePath%>jsp/JSPindex.jsp" class="layui-this"><i class="layui-icon layui-icon-home"></i></li>
					</ul>
				</div>
			</div>
      
			<!-- 主体内容 -->
			<div class="layui-body" id="LAY_app_body">
				<div class="layadmin-tabsbody-item layui-show">
					<iframe src="<%=basePath%>jsp/JSPindex.jsp" frameborder="0" class="layadmin-iframe"></iframe>
				</div>
			</div>
      
			<!-- 辅助元素，一般用于移动设备下遮罩 -->
			<div class="layadmin-body-shade" layadmin-event="shade"></div>
		</div>
	</div>

	<script type="text/javascript">
	
		layui.config({
			base: '/JSPdemo/tool/layuiadmin/' //静态资源所在路径
		}).extend({
			index: 'lib/index' //主入口模块
		}).use(['index', 'user']);
		
		//个人信息修改
		function Personal(){
			parent.layer.open({
				title: "个人信息",
				type: 2,
				area: ['650px', '280px'],
				content: '<%=basePath%>jsp/views/user/user_self.jsp',
			});
		};
		
		//修改密码
		function Password(){
			parent.layer.open({
				title: "密码修改",
				type: 2,
				area: ['600px','450px'],
				content: '<%=basePath%>jsp/views/user/user_self_password.jsp',
			});
		};
		
		//退出
		function Logout(){
			parent.layer.confirm('确定注销吗？',{btn:['确定','取消']},function(){
				$.ajax({
					url : "<%=basePath%>logout",
					type: "POST",
					cache: false,
					async: false,
					success: function(arg){
						if(arg.trim() == "success"){
							location.href = "<%=basePath%>jsp/login.jsp";
						}else{
							parent.layer.msg("注销失败！");
						}
					},
				})
			});
		};
		
		//清除缓存数据信息
		function removeRedis(){
			parent.layer.confirm('确定清除缓存中的数据吗？',{btn:['确定','取消']},function(){
				$.ajax({
					url : "<%=basePath%>removeRedis",
					type: "POST",
					cache: false,
					async: false,
					success: function(arg){
						parent.layer.msg("成功清除缓存数据信息！");
					},
				})
			});
		};
	</script>
</body>
</html>