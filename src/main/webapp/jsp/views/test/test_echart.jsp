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
<%-- <%
	String username = request.getSession().getAttribute("username").toString();
%> --%>
<%@include file="../public/public.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<h2 align="center" style="color: red;font-weight:bold;">难受，巨难受。</h2>
	<!-- datagrig列表 -->
	<div class="layui-fluid">
		<div class="layui-card">
			<div class="layui-col-md12 layui-col-space10">
				<div class="layui-col-md12">
					<div id="echarts" style="height:455px;">
					</div>
				</div>
			</div>
		</div>
	</div>
	<script language="javascript">
	
		// 绘制图表---饼状图
		var myChart = echarts.init(document.getElementById('echarts'));
		
		var Sex = [];
		var Value = [];
		
		$.ajax({
			url: "<%=basePath%>getPieList",
			type: "POST",
			async: false,
			success: function(str){
				for(var i=0;i<str.length;i++){
					var obj = new Object();
					Sex.push(str[i].SEX);
					obj.name = str[i].SEX;  
                    obj.value = str[i].NUMS;
                    Value.push(obj);
	        	}
			}
		});
		
		var option = {
		    tooltip: {
		        trigger: 'item',
		        formatter: "{a} <br/>{b}: {c} ({d}%)"
		    },
		    toolbox: { //可视化的工具箱
				show: true,
				feature: {
				    dataView: { //数据视图
				        show: true
				    },
				    saveAsImage: {//保存图片
				        show: true
				    },
				}
			},
		    legend: {
		        orient: 'vertical',
		        x: 'left',
		        data:Sex
		    },
		    series: [
		        {
		            name:'男女比例',
		            type:'pie',
		            radius: ['0%', '60%'],
		            avoidLabelOverlap: false,
		            label: {
		            	normal: {
			                formatter: '{b}: {c}  \n({d}%)'
			            },
		            },
		            labelLine: {
		                normal: {
		                    show: false
		                }
		            },
		            data:Value
		        }
		    ]
		};
		
		myChart.setOption(option);
		
		// 图表点击跳转事件
		myChart.on('click', function (params) {
			var sex = params.name;
			if(sex == "男"){
				parent.layer.open({
					title: "男生信息页面",
		  			type: 2,
		  			maxmin: true,
					shade: [0.8, '#393D49'], // 遮罩  不要可以不写 或默认 0 
					shift: 5, // 渐显  
		  			area: ['740px', '450px'],
		  			content: '<%=basePath%>jsp/views/test/test_sex.jsp?sex='+sex,
				});
			}else if(sex == "女"){
				parent.layer.open({
					title: "女生信息页面",
		  			type: 2,
		  			maxmin: true,
					shade: [0.8, '#393D49'], // 遮罩  不要可以不写 或默认 0 
					shift: 5, // 渐显  
		  			area: ['740px', '450px'],
		  			content: '<%=basePath%>jsp/views/test/test_sex.jsp?sex='+sex,
				});
			}
		});
			
	</script>
</body>
</html>