package com.test.util;

import java.util.HashMap;

public class LayuiMap {
	
	public static HashMap LayuiUploadMap(String msg, String src) throws Exception{
		HashMap map = new HashMap();
		HashMap map1 = new HashMap();
		map1.put("src", src);
		map.put("code", 0);
		map.put("msg", msg);
		map.put("data", map1);
		return map;
	}
}
