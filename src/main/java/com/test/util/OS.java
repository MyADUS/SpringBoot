package com.test.util;

import java.util.Properties;
/**
 * @author 判断操作系统，用于上传时候保存的文件夹路径
 *
 */
public class OS {
	public static String getOS(){
		Properties prop = System.getProperties();
		//操作系统名称  
		String osName = prop.getProperty("os.name");
		if(osName.toLowerCase().startsWith("win")){
			osName = SystemConstant.SYSTEM_WINDOWS_PATH;
		}else{
			osName = SystemConstant.SYSTEM_LINUX_PATH;
		}
		return osName;
	}
}
