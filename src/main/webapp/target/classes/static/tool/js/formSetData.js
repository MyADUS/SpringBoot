/**
 * @author: 张跃帅
 * @notes: 给form表单赋值
 * @date: 2017年12月12日
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
				body.find('input[name="'+Name+'"]').val(vaule);
				body.find('textarea[name="'+Name+'"]').val(vaule);
			}
			//不稳定！
			/*else if(this.options.Type == 3){
				var body = parent.layer.getChildFrame("body");
				//统一转换成小写
				var Name = name.toLowerCase();
				//适用于select下拉选择框
				if(body.find('select[name="'+Name+'"]')){
					body.find("option[value='"+vaule+"']").attr("selected", "selected");
				}
				body.find('input[name="'+Name+'"]').val(vaule);
				body.find('textarea[name="'+Name+'"]').val(vaule);
			}*/
		}
  };
  window.formSetData = formSetData;
}());