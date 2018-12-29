/**
 * table日期格式化 
 * datetime:日期
 * fmt:想要转换的格式
 */
//时间戳的处理
function toDateString(date){
	if(date){
		return date.substring(0,10);
	}else{
		return "";
	}
};
