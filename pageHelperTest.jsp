<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- 路径错误，非同一demo项目 -->
<!-- jQuery -->
<script src="/SBoot/tool/js/jquery-1.9.0.js"></script>
<!-- pagination分页插件js -->
<script type="text/javascript" src="/SBoot/tool/js/jquery.pagination.js"></script>
<!-- pagination分页插件css -->
<link rel="stylesheet" href="/SBoot/tool/css/pagination.css" media="all">

<!-- 还需在pom.xml中引入依赖 -->
<!-- 分页插件 -->
<!-- <dependency>
  <groupId>com.github.pagehelper</groupId>
  <artifactId>pagehelper-spring-boot-starter</artifactId>
  <version>1.2.3</version>
</dependency> -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>冬天来了</title>
</head>
<body>
<table border="1" style="width: 516px;">
	<tr>
		<th>ID</th>
		<th>用户名</th>
		<th>真实姓名</th>
		<th>密码</th>
	</tr>
</table>
<table border="1" id="tr">
</table>
<div class="m-style M-box4" id="test"></div>
<script type="text/javascript">
	//总页数
	var pages = 0; 
	//每页条数
	var limit = 5;
	
	//查询所有user信息
	function getAllUser(pagenum,pagesize){
		$.ajax({
			url: "/SBoot/all",
			data: {pageNum:pagenum,pageSize:pagesize},
			type: "POST",
			cache: false,
			async: false,
			success: function(res){
				var list = res.list;
				pages = res.pages;
				limit = res.pageSize;
				for(var i=0;i<list.length;i++){
					var id = list[i].id;
					var username = list[i].username;
					var truename = list[i].truename;
					var password = list[i].password;
					var td = "<tr><td style='width: 60px;'>"+id+"</td><td style='width: 142px;'>"+username+"</td><td style='width: 187px;'>"+truename+"</td><td style='width: 99px;'>"+password+"</td></tr>";
					$("#tr").append(td);
				}
			},
	 	});
	}
	
	getAllUser(pages,limit);
	
	$('#test').pagination({
	    pageCount: pages,
	    mode: 'fixed',
	    jump: true,
	    coping: true,
	    homePage: '首页',
	    endPage: '末页',
	    //prevContent: '上页',
	    //nextContent: '下页',
	    callback: function (api) {
			$("#tr").empty();
			getAllUser(api.getCurrent(),limit);
	    }
	});
</script>
</body>
</html>
