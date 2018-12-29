/**
 * @notes: 给form表单赋值
 */
(function(){
	var formSetData = function(options){
		this.options = $.extend({
		"Type" : "",
		"Object" : "",
		},options);
		this.SetData();
	};
	formSetData.prototype.SetData = function(){
		var objSr = this.options.Object;
		var objs = JSON.parse(objSr); 
		
		//用javascript的for/in循环遍历对象的属性 
		for(var i in objs){
			var name = i; 
			var vaule = objs[i]; 
			if(this.options.Type == 1){
				//统一转换成小写
				var Name = name.toLowerCase();
				//给checked单选框赋值
				if(Name == "sex"){
					$('body [name=' + name + '][value=' + vaule + ']').prop("checked",true);
				}else{
					//给form表单其他字段赋值
					$('body [name=' + name + ']').val(vaule);
				}
			}else if(this.options.Type == 2){
				var body = parent.layer.getChildFrame("body");
				//统一转换成小写
				var Name = name.toLowerCase();
				//适用于Table-grid-编辑
				if(body.find('input[type="radio"]')){
					if(vaule == 1){
						body.find('input[title="男"]').val(vaule).prop("checked",true);
						body.find('input[title="扣分"]').val(vaule).prop("checked",true);
					}else if(vaule == 2){
						body.find('input[title="女"]').val(vaule).prop("checked",true);
						body.find('input[title="得分"]').val(vaule).prop("checked",true);
					}
				}
				if(vaule != null){//判断值是否为空，不判断，当值为空时，vaule.length报错
					//从数据库查询返回的日期值，格式为2000-01-01 00:00:00
					if(vaule.length == 19 && vaule.substring(4,5) == "-" && vaule.substring(13,14) == ":"){
						//时间框赋值	格式为yyyy-MM-dd
						body.find('input[name="'+Name+'"]').val(vaule.substring(0,10));
					}else{
						body.find('input[name="'+Name+'"]').val(vaule);
						body.find('textarea[name="'+Name+'"]').val(vaule);
					}
				}
			}else if(this.options.Type == 3){
				var body = parent.layer.getChildFrame("body");
				//统一转换成小写
				var Name = name.toLowerCase();
				//适用于select下拉选择框，且只有一个select选择框
				if(body.find('select[name="'+Name+'"]')){
					body.find('option[value="'+vaule+'"]').attr("selected", "selected");
				}
				if(vaule != null){//判断值是否为空，不判断，当值为空时，vaule.length报错
					if(vaule.length == 19 && vaule.substring(4,5) == "-" && vaule.substring(13,14) == ":"){
						//时间框赋值	格式为yyyy-MM-dd
						body.find('input[name="'+Name+'"]').val(vaule.substring(0,10));
					}else{
						body.find('input[name="'+Name+'"]').val(vaule);
						body.find('textarea[name="'+Name+'"]').val(vaule);
					}
				}
			}
		}
  };
  window.formSetData = formSetData;
}());
