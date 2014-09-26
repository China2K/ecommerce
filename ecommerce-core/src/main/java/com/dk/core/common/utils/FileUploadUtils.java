package com.dk.core.common.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import com.dk.core.common.upload.FileUploadHandler;

public class FileUploadUtils {

	private static Logger logger = Logger.getLogger(FileUploadUtils.class);

	private static final int DIRCOUNTLIMIT = 30000;

	public static boolean validate_Image(byte[] bytes) {
		boolean truth = false;
		WeakReference<ByteArrayInputStream> wrf = new WeakReference<ByteArrayInputStream>(
				new ByteArrayInputStream(bytes));
		try {
			if (ImageIO.read(wrf.get()) != null) {
				truth = true;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
			return false;
		} finally {
			try {
				wrf.get().close();
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
				e.printStackTrace();
			}
		}
		return truth;
	}

	/**
	 * 各类微行业下的图片
	 * 
	 * @param userId
	 * @return
	 */
	public static String genrateUserAppImgDir(String userId, String appId) {
		if (FileUploadHandler.isUploadEnabled()) {
			int code = userId.hashCode();
			int mod = code % DIRCOUNTLIMIT;
			String dir = "/_" + mod + "/user_" + userId + "/app_" + appId
					+ "/img";
			return dir;
		}
		return null;
	}

	/**
	 * 各类微行业下的文件
	 * 
	 * @param userId
	 * @return
	 */
	public static String generateUserAppFileDir(String userId, String appId) {
		if (FileUploadHandler.isUploadEnabled()) {
			int code = userId.hashCode();
			int mod = code % DIRCOUNTLIMIT;
			String dir = "/_" + mod + "/user_" + userId + "/app_" + appId
					+ "/file";
			return dir;

		}
		return null;
	}

	/**
	 * 素材图片
	 * 
	 * @param userId
	 * @return
	 */
	public static String genrateUserMaterialImgDir(String userId) {
		if (FileUploadHandler.isUploadEnabled()) {
			int code = userId.hashCode();
			int mod = code % DIRCOUNTLIMIT;
			String dir = "/_" + mod + "/user_" + userId + "/material/img";
			return dir;
		}
		return null;
	}

	/**
	 * 素材文件
	 * 
	 * @param userId
	 * @return
	 */
	public static String generateUserMaterialFileDir(String userId) {
		if (FileUploadHandler.isUploadEnabled()) {
			int code = userId.hashCode();
			int mod = code % DIRCOUNTLIMIT;
			String dir = "/_" + mod + "/user_" + userId + "/material/file";
			return dir;
		}
		return null;
	}

}
