package com.test.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SimpleUtil {
	
	/**
	* @Title: getSimplString
	* @Description: (导入数据  处理时间方法 )
	* @param @param data : 日期
	* @param @param year:  年份 例如  2018
	 */
	public static String getSimplString(String data,String year) throws Exception{
		if(data.length()>20){
			int indexOf1 = data.lastIndexOf("：")+1;
			int indexOf2 = data.lastIndexOf(":")+1;
			
			data=data.substring(indexOf1>indexOf2?indexOf1:indexOf2,data.length());
		}
		StringBuffer buf = new StringBuffer();
		String[] str1 = data.split("-");
		String[] str2 = data.split("/");
		String[] str3 = data.split("\\.");
		String[] str = new String[3];
		if(str1.length == 3){
			str[0] = str1[0];
			str[1] = str1[1];
			str[2] = str1[2];
		}else if(str2.length == 3){
			str[0] = str2[0];
			str[1] = str2[1];
			str[2] = str2[2];
		}else if(str3.length == 3){
			str[0] = str3[0];
			str[1] = str3[1];
			str[2] = str3[2];
		}
		for (int i = 0; i < str.length; i++) {
			if(i==0){
				buf.append(year);
				if(str[0].length()==4){
					buf.append(str[0].substring(2, 4));
				}else{
					buf.append(str[0]);
				}
				buf.append("-");
			}else if(i==1){
				if(str[1].length()==2){
					buf.append(str[1]);
				}else{
					buf.append("0"+str[1]);
				}
				buf.append("-");
			}else if(i==2){
				if(str[2].length()==2){
					buf.append(str[2]);
				}else{
					buf.append("0"+str[2]);
				}
			}
		}
		return buf.toString();
	}
	
	/**
	* @Title: getSimpl
	* @Description: (日期格式字符串转换为自己想要的类型字符串)
	* @param @param data	: 字符串  例如:2018-12-12  2018/12/12
	* @param @param type	: 转换的格式 例如 : yyyy-MM-dd
	 */
	public static String getSimpl(String data,String type) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(data);
		SimpleDateFormat sdf2 = new SimpleDateFormat(type);
		String str = sdf2.format(date);
		return str;
	}
	
	/**
	* @Title: getSimpl
	* @Description: (字符串日期转换为  日期Date格式)
	 */
	public static Date getSimpl(String data) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(data);
		return date;
	}
	
	/**
	* @Title: getSimpl2
	* @Description: (将 2018-08-08 转换为 2018/08/08)
	* @param @param data
	 */
	public static String getSimpl2(String data){
		if(data==null||"".equals(data))return "";
		data = data.substring(0,10);
		data = data.replace("-", "/");
		return data;
	}
	
	/**
	* @Title: getSimpDateToString
	* @Description: TODO(将日期 date转换为 y格式的字符串)
	* @param  date : 日期	例如  new Date();
	* @param  y    : 想要转换的格式 例如: yyyy-MM-dd
	 */
	public static String getSimpDateToString(Date date,String y){
		SimpleDateFormat sdf = new SimpleDateFormat(y);
		String str = sdf.format(date);
		return str;
	}
	
	/**
	* @Title: getSimpDateToDate
	* @Description: TODO(将日期 date 转换为 y格式的日期)
	* @param  date 	: 日期	例如  new Date();
	* @param  y		: 想要转换的格式 例如: yyyy-MM-dd
	 */
	public static Date getSimpDateToDate(Date date,String y){
		SimpleDateFormat sdf = new SimpleDateFormat(y);
		String str = sdf.format(date);
		Date parse = null;
		try {
			parse = sdf.parse(str);
		} catch (ParseException e) {
			throw new RuntimeException("getSimpDateToDate()日期转换出错!");
		}
		return parse;
	}
	
	/**
	 * 判断字符串是否是日期格式
	 * String  str 要判断的字符串
	 * String  format 日期格式  yyyy/MM/dd  或者 yyyy/MM/dd HH:mm:ss 区分大小写
	 * boolean isLenient 是否是宽松的验证 默认值是 false  设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
	 * @return
	 */
	public static boolean isValidDate(String str,String format,boolean isLenient) {
	      boolean convertSuccess=true;
	      // 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
	       SimpleDateFormat sdf = new SimpleDateFormat(format);
	       try {
	    	   // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
	          if(isLenient){
	        	  sdf.setLenient(false);
	          }
	    	   sdf.parse(str);
	       } catch (ParseException e) {
	          // e.printStackTrace();
	       // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
	           convertSuccess=false;
	       } 
	       return convertSuccess;
	}
}
