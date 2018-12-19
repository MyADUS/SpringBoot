 /**
 * @Author: 朱卫士
 * @Createtime: 2017年10月24日 上午10:06:55
 * @Copyright: Copyright (c) 2016
 * @Company: 北京永杰友信科技有限公司
 * @Version：1.0
 * @Description: 
 */
package com.test.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
* @ClassName: getResult
* @Description: TODO(分页公共方法)
* @author zhuweishi
* @date 2017年10月24日 上午10:10:04
*
 */
public class getResult {
	//sql 查询
	public static HashMap getMapResult(List dataAll,int page,int limit) throws Exception{
		ArrayList data = new ArrayList();
        int start = page * limit, end = start + limit;
        for (int i = 0, l = dataAll.size(); i < l; i++)//能获得数据
        {
        	 if (dataAll.get(i) == null) continue;
	            //获取需要返回的当前页记录
	            if (start <= i && i < end)
	            {
	                data.add(dataAll.get(i));
	            }
        }
          HashMap result = new HashMap();
          result.put("code", 0);
          result.put("msg", "");
          result.put("data", data);
          result.put("count", dataAll.size());
		  return result;
	}
	// 实体转对象 
	/*public static HashMap getMapResultEntity(List dataAll,int page, int limit) throws Exception{
		ArrayList data = new ArrayList();
		int start = page * limit, end = start + limit;
		for (int i = 0, l = dataAll.size(); i < l; i++)
		{
			HashMap record = new HashMap();			
			if (dataAll.get(i) == null)
			record = HashmapAndEntityTransfer.entityTranserToHashmap(dataAll.get(i));		
			if (start <= i && i < end) {
				data.add(record);
			}
		}
		HashMap result = new HashMap();
		 result.put("msg", "");
         result.put("data", data);
         result.put("count", dataAll.size());
		return result;
	}*/
	
	// sql 语句中分页查询  封装 
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static HashMap getMapDataPage(List dataAll){
		HashMap result = new HashMap();
		if(dataAll.size()>0){
			HashMap dbMap = (HashMap)dataAll.get(0);
			result.put("count", dbMap.get("TOTAL"));
		}else{
			result.put("count", 0);
		}
		result.put("code", 0);
		result.put("msg", "");
        result.put("data", dataAll);
        return result;
	}
}
