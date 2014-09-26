package com.dk.core.common.upload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.dk.core.common.utils.FileUploadUtils;

public class FileUploadHandler {

	private static final Logger logger = Logger
			.getLogger(FileUploadHandler.class);

	public static final Properties uploadConf = new Properties();

	private static boolean uploadEnable = false;

	public static UploadConfig UPLOAD_CONFIG;

	private static int index = 0;

	private static final Object LOCK = new Object();

	static {
		InputStream is = FileUploadHandler.class
				.getResourceAsStream("/upload.properties");
		if (is != null) {
			uploadEnable = true;
			UPLOAD_CONFIG = new UploadConfig();
			try {
				uploadConf.load(is);
				UPLOAD_CONFIG.setImgStorageType(uploadConf
						.getProperty("img.storage.type"));
				UPLOAD_CONFIG.setImgStorageDir(uploadConf
						.getProperty("img.storage.dir"));
				UPLOAD_CONFIG.setImgVirtualDir(uploadConf
						.getProperty("img.virtual.dir"));
				UPLOAD_CONFIG.setImgStorageTable(uploadConf
						.getProperty("img.storage.table"));
				UPLOAD_CONFIG.setFileStorageType(uploadConf
						.getProperty("file.storage.type"));
				UPLOAD_CONFIG.setFileStorageDir(uploadConf
						.getProperty("file.storage.dir"));
				UPLOAD_CONFIG.setFileVirtualDir(uploadConf
						.getProperty("file.virtual.dir"));
				UPLOAD_CONFIG.setDownloadParamName(uploadConf
						.getProperty("download.param.name"));
				UPLOAD_CONFIG.setPicUrlPrefix(uploadConf
						.getProperty("pic.url.prefix"));
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * @param img
	 * @param dir
	 *            if null,is default dir else storage.dir+dir
	 * @return
	 */
	public static String uploadImg(byte[] img, String dir) {
		if (uploadEnable) {
			StringBuilder imgId = new StringBuilder();
			if (FileUploadUtils.validate_Image(img)) {
				if ("disk".equalsIgnoreCase(UPLOAD_CONFIG.getImgStorageType())) {
					String imgRootDir = UPLOAD_CONFIG.getImgStorageDir();
					if (dir != null) {
						dir = dir.startsWith("/") ? dir : "/" + dir;
						dir = dir.endsWith("/") ? dir : dir + "/";
					} else {
						dir = "/";
					}
					String finalDir = imgRootDir + dir;
					File imgFileDir = new File(finalDir);
					if (!imgFileDir.exists()) {
						imgFileDir.mkdirs();
					}
					imgId.append(dir);
					imgId.append(System.currentTimeMillis());
					imgId.append("_");
					imgId.append(getIndex());
					imgId.append(".img");
					File imgFile = new File(imgRootDir + imgId.toString());
					try {
						FileOutputStream fos = new FileOutputStream(imgFile);
						fos.write(img);
						fos.close();
					} catch (Exception e) {
						e.printStackTrace();
						logger.error(e.getMessage(), e);
					}

				} else if ("hbase".equalsIgnoreCase(UPLOAD_CONFIG
						.getImgStorageType())) {
					// to do something
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
					throw new Exception("File doing uploading is not a Image!");
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					e.printStackTrace();
				}
			}
			return imgId.toString();
		} else {
			try {
				throw new Exception(
						"can't find upload.properties ,  upload is not enable!");
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				e.printStackTrace();
			}
		}
		return null;
	}

	public static String uploadFile(byte[] file, String dir, String fileName)
			throws Exception {
		if (uploadEnable) {
			StringBuilder filepath = new StringBuilder();
			if ("disk".equalsIgnoreCase(UPLOAD_CONFIG.getFileStorageType())) {
				String fileRootDir = UPLOAD_CONFIG.getFileStorageDir();
				if (dir != null) {
					dir = dir.startsWith("/") ? dir : "/" + dir;
					dir = dir.endsWith("/") ? dir : dir + "/";
				} else {
					dir = "/";
				}
				String finalDir = fileRootDir + dir;
				File imgFileDir = new File(finalDir);
				if (!imgFileDir.exists()) {
					imgFileDir.mkdirs();
				}
				filepath.append(dir);
				if (!fileName.contains("/")) {
					filepath.append(fileName);
				} else {
					throw new Exception("Invalid fileName,can't contains '/' !");
				}
				File upload_file = new File(fileRootDir + filepath.toString());
				try {
					FileOutputStream fos = new FileOutputStream(upload_file);
					fos.write(file);
					fos.close();
				} catch (Exception e) {
					e.printStackTrace();
					logger.error(e.getMessage(), e);
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
			return filepath.toString();
		} else {
			try {
				throw new Exception(
						"can't find upload.properties ,  upload is not enable!");
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				e.printStackTrace();
			}
		}
		return null;
	}

	private static int getIndex() {
		int i = index;
		synchronized (LOCK) {
			if (index == 9) {
				index = 0;
			} else {
				index++;
			}
		}
		return i;
	}

	public static boolean isUploadEnabled() {
		return uploadEnable;
	}
}
