package com.test.util;

import java.util.Collection;
import java.util.Iterator;

public class StringUtil {
	public static boolean isNullOrEmpty(Object obj) {	
		return obj == null || "".equals(obj.toString()) || "null".equalsIgnoreCase(obj.toString());
	}
	public static String toString(Object obj){
		if(obj == null) return "null";
		return obj.toString();
	}
	public static String join(Collection s, String delimiter) {
        StringBuffer buffer = new StringBuffer();
        Iterator iter = s.iterator();
        while (iter.hasNext()) {
            buffer.append(iter.next());
            if (iter.hasNext()) {
                buffer.append(delimiter);
            }
        }
        return buffer.toString();
    }
	//将首字母变成小写
	public static String changeFirstCharToLowercase(String name){
		String result = name.substring(3);
		result = result.replace(result.charAt(0), (char)(result.charAt(0)+32));
		return result;
	}
	//get/set 方法里包含字段名,截取前三位后,剩下是字符全部变成大写,用于匹配数据库的字段(数据库里字段全是大写的);
	public static String changeFirstCharToUpercase(String name){
		String result = name.substring(3);
		result = result.toUpperCase();
		return result;
	}
}
