package com.dk.core.common.upload;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dk.core.common.utils.DateUtils;
import com.dk.core.common.base.sys.SpringDispatcherEnhancer;

@Controller
@RequestMapping("/upload")
public class FileController {

	private static final Logger logger = Logger.getLogger(FileController.class);

	private static UploadConfig UPLOAD_CONFIG = FileUploadHandler.UPLOAD_CONFIG;

	@RequestMapping("/img.do")
	public void getImg(HttpServletRequest request, HttpServletResponse response) {
		String imgId = request.getParameter(UPLOAD_CONFIG.getDownloadParamName());
		if (UPLOAD_CONFIG != null) {
			if ("disk".equalsIgnoreCase(UPLOAD_CONFIG.getImgStorageType())) {
				String imgRootDir = UPLOAD_CONFIG.getImgStorageDir();
				File f = new File(imgRootDir + imgId);
				if (f.exists()) {
					try {
						FileInputStream fis = new FileInputStream(f);
						byte[] bytes = new byte[1024];
						while (fis.read(bytes) != -1) {
							response.getOutputStream().write(bytes);
						}
						fis.close();
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
						e.printStackTrace();
					}
				}
			} else if ("hbase".equalsIgnoreCase(UPLOAD_CONFIG
					.getImgStorageType())) {
				
			} else {
				try {
					throw new Exception(
							"unknown img.upload.type only  'hbase','disk' available,'nfs' belong 'disk'!");
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					e.printStackTrace();
				}
			}
		} else {
			try {
				throw new Exception(
						"can't find upload.properties ,  upload is not enable!");
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				e.printStackTrace();
			}
		}
	}

	@RequestMapping("/file.do")
	public void getFile(HttpServletRequest request,
			HttpServletResponse response, @PathVariable String filpath) {
		String filepath = request.getParameter("p");
		if (UPLOAD_CONFIG != null) {
			if ("disk".equalsIgnoreCase(UPLOAD_CONFIG.getFileStorageType())) {
				String fileRootDir = UPLOAD_CONFIG.getFileStorageDir();
				File f = new File(fileRootDir + filepath);
				if (f.exists()) {
					try {
						String fileName = filepath.substring(filepath
								.lastIndexOf("/") + 1);
						FileInputStream fis = new FileInputStream(f);
						response.setContentType("application/octet-stream");
						response.addHeader("Content-Length", "" + f.length());
						response.addHeader("Content-Disposition",
								"attachment; filename=\"" + fileName + "\"");
						byte[] bytes = new byte[1024];
						while (fis.read(bytes) != -1) {
							response.getOutputStream().write(bytes);
						}
						fis.close();
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
						e.printStackTrace();
					}
				}
			} else if ("hdfs".equalsIgnoreCase(UPLOAD_CONFIG
					.getFileStorageType())) {
				// to do something
			} else {
				try {
					throw new Exception(
							"unknown file.upload.type only  'hdfs','disk' available,'nfs' belong 'disk'!");
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					e.printStackTrace();
				}
			}
		} else {
			try {
				throw new Exception(
						"can't find upload.properties ,  upload is not enable!");
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
	}
	
	@RequestMapping("/uploadImg.do")
	public @ResponseBody
	String uploadMaterialImg(@RequestParam("imgFile") MultipartFile multipartFile)
	{
		String resp = null;
		String imgId = null;
		if (multipartFile != null)
		{
			try
			{
				
				String dirName=DateUtils.getCurrentTime("yyyyMMdd");
				
				String dir = "/product/"+dirName;
				
				byte[] file = multipartFile.getBytes();
				
				imgId = FileUploadHandler.uploadImg(file, dir);
				resp = SpringDispatcherEnhancer.imageUrlPrefix+imgId;
			
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return imgId;
	}
}
