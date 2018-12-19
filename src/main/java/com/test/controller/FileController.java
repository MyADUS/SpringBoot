package com.test.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.test.util.LayuiMap;

@RestController
public class FileController {
	
	private static final Logger logger = LoggerFactory.getLogger(FileController.class);
	
    /**
     * 单文件上传
     *
     * @param file
     * @param request
     * @return
     * @throws Exception 
     */
    @PostMapping("/upload")
    @ResponseBody
    public HashMap upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws Exception {
    	String saveFileName = file.getOriginalFilename();
    	if (!file.isEmpty()) {
            //截取文件后缀
            //创建新的文件名
            SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
            String newFilename = sdf.format(new Date()) + "-" + saveFileName;
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            //request.getSession().getServletContext().getRealPath("/upload/")
            File saveFile = new File("D:/upload/uploadFile/" + sdf1.format(new Date()) + "/" + newFilename);
            //getParentFile()
            //假设文件名字为a.txt saveFile为D:\\upload\a.txt
            //则saveFile.getParentFile()为D:\\upload\，判断这个路径是否存在（exists），不存在则创建（mkdirs）
            if (!saveFile.getParentFile().exists()) {
            	System.out.println(saveFile.getParentFile());
                saveFile.getParentFile().mkdirs();
            }
            try {
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(saveFile));
                out.write(file.getBytes());
                out.flush();
                out.close();
                logger.info("单文件上传：文件：《" + saveFileName + "》，上传成功。");
                return LayuiMap.LayuiUploadMap("上传成功",saveFile.toString());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                logger.info("单文件上传：文件：《" + saveFileName + "》，上传失败。");
                return LayuiMap.LayuiUploadMap("上传失败","");
            } catch (IOException e) {
                e.printStackTrace();
                logger.info("单文件上传：文件：《" + saveFileName + "》，上传失败。");
                return LayuiMap.LayuiUploadMap("上传失败","");
            }
        } else {
        	logger.info("单文件上传：文件：《" + saveFileName + "》，上传失败，因为文件为空。");
        	return LayuiMap.LayuiUploadMap("上传失败，因为文件为空","");
        }
    }
    

    @PostMapping("/uploadFiles")
    @ResponseBody
    public HashMap uploadFiles(HttpServletRequest request) throws Exception {
    	// request.getSession().getServletContext().getRealPath("/upload/")
    	//创建新的文件名
        SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
        MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
        List<MultipartFile> files = multiRequest.getFiles("file");
        MultipartFile file = null;
        BufferedOutputStream stream = null;
        File saveFile = null;
        for (int i = 0; i < files.size(); ++i) {
            file = files.get(i);
            if (!file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();
                    String newFilename = sdf.format(new Date()) + "-" + file.getOriginalFilename();
                    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                    File savePath = new File("D:/upload/uploadFiles/" + sdf1.format(new Date()) + "/");
                    if (!savePath.exists()) {
                        savePath.mkdirs();
                    }
                    saveFile = new File(savePath, newFilename);
                    stream = new BufferedOutputStream(new FileOutputStream(saveFile));
                    stream.write(bytes);
                    stream.close();
                } catch (Exception e) {
                    if (stream != null) {
                        stream.close();
                        stream = null;
                    }
                    logger.info("多文件上传：文件：《"+ file.getOriginalFilename() + "》，上传出错。" + e.getMessage());
                    return LayuiMap.LayuiUploadMap("文件：《"+ file.getOriginalFilename() + "》，上传出错。" + e.getMessage(),"");
                }
            } else {
            	logger.info("多文件上传：文件：《"+ file.getOriginalFilename() + "》为空。");
                return LayuiMap.LayuiUploadMap("文件：《"+ file.getOriginalFilename() + "》为空。","");
            }
        }
        logger.info("多文件上传：文件：《" + file.getOriginalFilename() + "》，上传成功");
        return LayuiMap.LayuiUploadMap("文件" + file.getOriginalFilename() + "上传成功",saveFile + "");
    }
}
