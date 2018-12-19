package com.test.util;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class FileUtil {
	public static String encoding = "UTF-8";
	public static void write(String path, String content){
		try
        {                     
            OutputStreamWriter out = new OutputStreamWriter(
            		new FileOutputStream(path),"UTF-8");
            //out.write("\n"+content);
            out.write(content);
            out.flush();
            out.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException("输出异常");
        }
	}
	public static String getFileName(String fileName){
		String[] ss = fileName.split(".");
		fileName = ss[0];
		String[] ss2 = fileName.split("/");
		fileName = ss[ss.length-1];
		return fileName;
	}
	/** 
     * 获得指定文件的byte数组 
     */  
    public static byte[] getBytes(File f){  
        byte[] buffer = null;  
        try {  
            //File file = new File(filePath);  
            FileInputStream fis = new FileInputStream(f);  
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);  
            byte[] b = new byte[1000];  
            int n;  
            while ((n = fis.read(b)) != -1) {  
                bos.write(b, 0, n);  
            }  
            fis.close();  
            bos.close();  
            buffer = bos.toByteArray();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return buffer;  
    }
    /**
     * 将arrayList转换成byte数组
     * @param ArrayList<Byte>  byte型数组的arrayList
     * @return byte型数组。
     */
    public static byte[] arrayListTobytes(ArrayList al){
    	byte[] aa = new byte[al.size()];
    	for(int i=0;i<al.size();i++)
    	{
    	   aa[i]=(byte)Byte.parseByte(al.get(i).toString());
    	}
    	return aa;
    }
    /**
     * 将Object转换成byte数组
     * 适用范围:注意只适用于javaobject对象,不适用于数据库读出的二进制流
     * @param obj :Object
     * @return
     */
    public static byte[] objectTobytes(Object obj){ 
    	byte[] bytes = null;      
        ByteArrayOutputStream bos = new ByteArrayOutputStream();      
        try {        
            ObjectOutputStream oos = new ObjectOutputStream(bos);         
            oos.writeObject(obj);        
            oos.flush();         
            bytes = bos.toByteArray ();      
            oos.close();         
            bos.close();        
        } catch (IOException ex) {        
            ex.printStackTrace();   
        }      
        return bytes;
    }
    /** 
     * 根据byte数组，生成文件 
     */  
    public static File createFile(byte[] bfile, String filePath,String fileName) {  
        BufferedOutputStream bos = null;  
        FileOutputStream fos = null;  
        File file = null;  
        try {  
            File dir = new File(filePath);  
            if(!dir.exists()&&dir.isDirectory()){//判断文件目录是否存在  
                dir.mkdirs();  
            }  
            file = new File(filePath+"/"+fileName);  
            
            fos = new FileOutputStream(file);  
            bos = new BufferedOutputStream(fos);  
            bos.write(bfile);  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            if (bos != null) {  
                try {  
                    bos.close();  
                } catch (IOException e1) {  
                    e1.printStackTrace();  
                }  
            }  
            if (fos != null) {  
                try {  
                    fos.close();  
                } catch (IOException e1) {  
                    e1.printStackTrace();  
                }  
            }  
        }  
        return file;
    } 
    public static byte[] imageToByte(BufferedImage bi,String format) throws IOException
    {
	    ByteArrayOutputStream baos=new ByteArrayOutputStream();
	    ImageIO.write(bi, format, baos);
	    return baos.toByteArray();	
    }
    
    /** 
     * 编码 
     * @param bstr 
     * @return String 
     */  
    public static String encode(byte[] bstr){  
    return new sun.misc.BASE64Encoder().encode(bstr);  
    }  
  
    /** 
     * 解码 
     * @param str 
     * @return string 
     */  
    public static byte[] decode(String str){  
    byte[] bt = null;  
    try {  
        sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();  
        bt = decoder.decodeBuffer( str );  
    } catch (IOException e) {  
        e.printStackTrace();  
    }  
  
        return bt;  
    }  
}
