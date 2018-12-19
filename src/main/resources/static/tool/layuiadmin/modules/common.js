/**

 @Name：layuiAdmin 公共业务
 @Author：贤心
 @Site：http://www.layui.com/admin/
 @License：LPPL
    
 */
layui.define(function(exports){
	var $ = layui.$;
	var layer = layui.layer;
	var laytpl = layui.laytpl;
	var setter = layui.setter;
	var view = layui.view;
	var admin = layui.admin;
  
	//公共业务的逻辑处理可以写在此处，切换任何页面都会执行
  
	//退出
	admin.events.logout = function(){
		//获取项目路径
		var localObj = window.location;
		var contextPath = localObj.pathname.split("/")[1];
		var basePath = localObj.protocol + "//" + localObj.host + "/"+ contextPath + "/";

		layer.confirm('确定注销？',{title: "系统提示",anim: 6,icon: 3,closeBtn: 0},function(index){
			//执行退出接口
			admin.req({
				url: basePath + 'qyUser_GoCancellation.action',
				type: 'POST',
				done: function(res){
					//这里要说明一下：done 是只有 response 的 code 正常才会执行。而 succese 则是只要 http 为 200 就会执行
	        
					//清空本地记录的 token，并跳转到登入页
					admin.exit(function(){
						location.href = basePath + "dist/views/index.jsp";
					});
				}
			});
		});
	};
  
	//对外暴露的接口
	exports('common', {});
});