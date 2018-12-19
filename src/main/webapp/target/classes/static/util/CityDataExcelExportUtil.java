 /**
 * @Author: 朱卫士
 * @Createtime: 2018年3月28日 上午9:40:15
 * @Copyright: Copyright (c) 2016
 * @Company: 北京永杰友信科技有限公司
 * @Version：1.0
 * @Description: 行政许可导出
 * 					> 发改委
 */
package com.yongjie.xyxx.util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.json.JSONArray;

public class CityDataExcelExportUtil {
	public static String getPoiExcelExportPath(ArrayList<Integer> counluWeigth,ArrayList<String> titleName,JSONArray jsonArray) throws Exception{
		String url="";
        // 1.创建一个webbook，对应一个Excel文件  
        HSSFWorkbook wb = new HSSFWorkbook();  
        // 2.在webbook中添加一个sheet,对应Excel文件中的sheet  
        HSSFSheet sheet = wb.createSheet("sheet1");  
		
        // 样式1 
        HSSFCellStyle style = wb.createCellStyle(); 
        style.setWrapText(true); //设置自动换行
        
        // 设置标题栏宽度  > 序号..许可决定书.....
        for (int i = 0; i < counluWeigth.size(); i++) {
        	sheet.setColumnWidth(i, counluWeigth.get(i));
		}
        
        // 设置字体
        HSSFFont font = wb.createFont();  // 字体样式 
        font.setFontName("宋体"); 
        font.setBold(true);;//粗体显示
        font.setFontHeightInPoints((short) 12); 
		style.setFont(font);//选择需要用到的字体格式
        
        
		HSSFRow row = sheet.createRow((int) 0);  //第一行  
        HSSFCell cell = row.createCell((short) 0); //第一行第一列
        HSSFRichTextString text = new HSSFRichTextString();
        
        // 标题栏
        for (int i = 0; i < titleName.size(); i++) {
        	cell = row.createCell((short) i); //第一行第一列
        	text = new HSSFRichTextString(titleName.get(i));
        	cell.setCellValue(text);
        	cell.setCellStyle(style);
		}
        
        // 样式2
        HSSFCellStyle style1 = wb.createCellStyle();
        style1.setWrapText(true); //设置自动换行
        //style1.setVerticalAlignment(VerticalAlignment.CENTER);//设置垂直居中
        style1.setAlignment(HorizontalAlignment.CENTER);//设置居中对齐.水平对齐居中，这意味着文本居中。
        HSSFFont font1 = wb.createFont();  // 字体样式 
        font1.setFontName("宋体"); 
        font1.setFontHeightInPoints((short) 12); 
		style1.setFont(font1);
        
		// 内容遍历
		for (int i = 0; i < jsonArray.length(); i++) {
			Object rb = jsonArray.get(i);
			
			row = sheet.createRow((int) i+1);//新开一行
        	row.setHeight((short)(256*2));//设置行的高度
        	HSSFCell celli = row.createCell((short) 0); // 具体单元格
        	
        	//序号
        	text = new HSSFRichTextString(i+1+"");
            celli.setCellValue(text);
            celli.setCellStyle(style1);
            
            HSSFCell cell1 = row.createCell(1);
            cell1.setCellValue(jsonArray.getJSONObject(i).getString("NAME"));
            cell1.setCellStyle(style1);

            HSSFCell cell2 = row.createCell(2);
            int hhblr = jsonArray.getJSONObject(i).getInt("AGE");
            String HHB_LR = Integer.toString(hhblr);
            cell2.setCellValue(HHB_LR);
            cell2.setCellStyle(style1);
            
		}
		try {  
        	String wjj = SimpleUtil.getSimpDateToString(new Date(), "yyyy-MM-dd");
        	String path =  OS.getOS()+SystemConstant.ZJJ_NUMS_URL;
        	File dir = new File(path+wjj); 
        	if(!dir.exists()){
        	    dir.mkdirs();
        	}
        	url = wjj+"/"+UUID.randomUUID()+".xls";
            FileOutputStream fout = new FileOutputStream(path+url); 
            wb.write(fout);  
            fout.close();
            wb.close();
        } catch (Exception e)  {  
            e.printStackTrace();  
    	}  
		return SystemConstant.ZJJ_NUMS_URL+url;
	}
	
	
	// 设置导出Excel的宽度   >> 发改委
	public static ArrayList<Integer> getColumnWeigth() {
		 ArrayList<Integer> columnWeigth = new ArrayList<>();
		 columnWeigth.add( 256*10);		// 序号
		 columnWeigth.add( 256*20);		// 姓名
		 columnWeigth.add( 256*20);		// 年龄
		 return columnWeigth;
	}
	
	// 标题栏名称 
	public static ArrayList<String> getXzxkTitleName(){
		ArrayList<String> titleData = new ArrayList<>(); 
			titleData.add("序号");
			titleData.add("姓名");
			titleData.add("年龄");
		return titleData;
	}
}
