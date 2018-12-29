<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="views/public/public.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录页面</title>
</head>
<body style="background-color: pink;">
	<h2 style="color: #ff0000;" align="center">难受还是我难受啊</h2>
	<div class="layadmin-user-login-box layadmin-user-login-body layui-form" style="width: 33%;margin-left: 33%;margin-top: 10%;">
		<!-- 单文件上传 -->
		<div class="layui-form-item" align="center">
			<span class="layui-btn layui-btn-normal" id="upload">上传文件</span>
		</div>
		<hr>
		<!-- 单图片上传 -->
		<div class="layui-form-item" align="center">
			<span class="layui-btn layui-btn-normal" id="chooseIMG">选择图片</span>
			<span class="layui-btn layui-btn-xs layui-btn-danger" id="deleteIMG">X</span>
		</div>
		<div class="layui-upload-list" align="center" id="img">
		</div>
		<div class="layui-form-item" align="center">
			<span class="layui-btn layui-btn-normal" id="uploadIMG">上传图片</span>
		</div>
		<hr>
		<!-- 多文件上传 -->
		<div class="layui-upload">
			<div class="layui-form-item" align="center">
				<button type="button" class="layui-btn layui-btn-normal" id="testList">选择多文件</button>
			</div>
			<div class="layui-upload-list">
				<table class="layui-table">
					<thead>
						<tr>
							<th>文件名</th>
							<th>大小</th>
							<th>状态</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody id="demoList"></tbody>
				</table>
			</div>
			<div class="layui-form-item" align="center">
				<button type="button" class="layui-btn" id="testListAction">开始上传</button>
			</div>
		</div>
	</div>
	
	<script type="text/javascript">
		//引用Layui
		layui.use(['upload'],function(){
			var $ = layui.jquery,
			upload = layui.upload;
			
			showhide();
			
			//单文件上传
			upload.render({ //允许上传的文件后缀
				elem: '#upload',
				url: '<%=basePath%>upload',
				accept: 'file',
				before: function(){
					parent.layer.msg('数据正在导入中，请稍等...',{icon: 16,shade:0.4});
				},
				method: 'POST',
				multiple: true,
				done: function(res){
					parent.layer.msg(res.msg);
				},
				error: function(res){ //每个文件提交一次触发一次。详见“请求成功的回调”
					parent.layer.msg("上传文件异常");
				}
			});
			
			//单图片上传
			upload.render({ //允许上传的文件后缀
				elem: '#chooseIMG',
				url: '<%=basePath%>upload',
				accept: 'images',
				auto: false,
				bindAction: '#uploadIMG',
				multiple: false,
				method: 'POST',
				choose: function(obj){
					obj.preview(function(index, file, result){
						$('#img').empty();
			        	$('#img').append('<img height="90" src="'+ result +'" alt="'+ file.name +'" class="layui-upload-img">');
			        	showhide();
					});
				},
				done: function(res){
					$("#img").empty();
					showhide();
					parent.layer.msg(res.msg);
				},
				error: function(res){
					var imgText = $('#imgText');
					imgText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
					imgText.find('.demo-reload').on('click', function(){
						uploadInst.upload();
					});
				}
			});
			
			//多文件上传
			var i = 0;
			var demoListView = $('#demoList');
			upload.render({ //允许上传的文件后缀
				elem: '#testList',
				url: '<%=basePath%>uploadFiles',
				auto: false,// 设置选择文件后不自动上传
				bindAction: '#testListAction',// 与auto:false配合使用，绑定上传按钮
				accept: 'file',
				field: 'file',
				before: function(){
					parent.layer.msg('数据正在导入中，请稍等...',{icon: 16,shade:0.4});
				},
				method: 'POST',
				multiple: true,
				number: 3,
				choose: function(obj){
					if(i == 6){
						parent.layer.msg("一次最多上传三个文件！");
					}else{
						var files = this.files = obj.pushFile(); //将每次选择的文件追加到文件队列
						//判断files(Object类型)的数目
					    for (const index in files) {
					        i ++ 
					    }
				    	//读取本地文件
						obj.preview(function(index, file, result){
						  var tr = $(['<tr id="upload-'+ index +'">'
						    ,'<td>'+ file.name +'</td>'
						    ,'<td>'+ (file.size/1014).toFixed(1) +'kb</td>'
						    ,'<td>等待上传</td>'
						    ,'<td>'
						      ,'<button class="layui-btn layui-btn-xs demo-reload layui-hide">重传</button>'
						      ,'<button class="layui-btn layui-btn-xs layui-btn-danger demo-delete">删除</button>'
						    ,'</td>'
						  ,'</tr>'].join(''));
						  
						  //单个重传
						  tr.find('.demo-reload').on('click', function(){
						    obj.upload(index, file);
						  });
						  
						  //删除
						  tr.find('.demo-delete').on('click', function(){
						    delete files[index]; //删除对应的文件
						    tr.remove();
						    uploadListIns.config.elem.next()[0].value = ''; //清空 input file 值，以免删除后出现同名文件不可选
						  });
						  
						  demoListView.append(tr);
						});
					}
				},
				done: function(res, index, upload){ //每个文件提交一次触发一次。详见“请求成功的回调”
					if(res.code == 0){ //上传成功
				        var tr = demoListView.find('tr#upload-'+ index)
				        ,tds = tr.children();
				        tds.eq(2).html('<span style="color: #5FB878;">上传成功</span>');
				        tds.eq(3).html(''); //清空操作
				        return delete this.files[index]; //删除文件队列已经上传成功的文件
			      	}
			      	this.error(index, upload);
				},
				allDone: function(obj){//当文件全部被提交后，才触发
				    console.log(obj.total); //得到总文件数
				    console.log(obj.successful); //请求成功的文件数
				    console.log(obj.aborted); //请求失败的文件数
				    if(obj.aborted == 0 && obj.successful == obj.total){
				    	parent.layer.msg("文件上传成功");
				    }else{
				    	parent.layer.msg("上传文件异常");
				    }
				},
				error: function(index, upload){
					var tr = demoListView.find('tr#upload-'+ index)
					,tds = tr.children();
					tds.eq(2).html('<span style="color: #FF5722;">上传失败</span>');
					tds.eq(3).find('.demo-reload').removeClass('layui-hide'); //显示重传
				}
			});
			
			$("#deleteIMG").click(function(){
				$("#img").empty();
				showhide();
			});
			
			function showhide(){
				if($('#img').children('img').length > 0){
					$("#deleteIMG").show();
				}else{
					$("#deleteIMG").hide();
				}
			}
		});
	</script>
</body>
</html>
