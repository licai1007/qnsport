package com.qingniao.console.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.qingniao.core.common.FastDFSClient;
import com.qingniao.core.common.IDUtils;
import com.qingniao.core.common.SERVER_URL;

import net.fckeditor.response.UploadResponse;

@Controller
@RequestMapping("/upload")
public class UploadController {
	@ResponseBody
	@RequestMapping("/uploadImg.do")
	public void upload(@RequestParam MultipartFile picture,HttpServletRequest request,HttpServletResponse response){
		String filename = picture.getOriginalFilename();
		String ext = filename.substring(filename.lastIndexOf(".")+1);
		String name = IDUtils.genImageName();
		String path = "/imgs/"+name + "." + ext;
		String url = request.getSession().getServletContext().getRealPath("") + path;
		
		try {
			InputStream in = picture.getInputStream();
			OutputStream out = new FileOutputStream(new File(url));
			byte[] b = new byte[1024];
			int len;
			while((len = in.read(b)) != -1){
				out.write(b, 0, len);
			}
			in.close();
			out.close();
			
			JSONObject jo = new JSONObject();
			jo.put("path",path);
			response.getWriter().write(jo.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@ResponseBody
	@RequestMapping("/uploadImgtoDFS.do")
	public void uploadByDFS(@RequestParam MultipartFile picture,HttpServletResponse response){
		try {
			FastDFSClient fastDFSClient = new FastDFSClient("classpath:properties/fastdfs.properties");
			String filename = picture.getOriginalFilename();
			String ext = filename.substring(filename.lastIndexOf(".")+1);
			String path = fastDFSClient.uploadFile(picture.getBytes(),ext);
			String url = SERVER_URL.IMG_URL+path;
			JSONObject jo = new JSONObject();
			jo.put("path", path);
			jo.put("url", url);
			response.getWriter().write(jo.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	@RequestMapping("/productDesc.do")
	public void productDesc(HttpServletRequest request,HttpServletResponse response){
		try {
			FastDFSClient fastDFSClient = new FastDFSClient("classpath:properties/fastdfs.properties");
			MultipartRequest multipartRequest = (MultipartRequest)request;
			Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
			Set<Entry<String, MultipartFile>> file = fileMap.entrySet();
			for (Entry<String, MultipartFile> entry : file) {
				MultipartFile pic = entry.getValue();
				String path = fastDFSClient.uploadFile(pic.getBytes(),FilenameUtils.getExtension(pic.getName()));
				String url = SERVER_URL.IMG_URL+path;
				UploadResponse ok = UploadResponse.getOK(url);
				response.getWriter().print(ok);	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
