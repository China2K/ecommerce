package com.dk.core.common.upload;

public class UploadConfig {

	private String imgStorageType;

	private String imgStorageDir;
	private String imgStorageTable;

	private String imgVirtualDir;

	private String fileStorageType;

	private String fileStorageDir;

	private String fileVirtualDir;
	
	private String downloadParamName;
	
	private String picUrlPrefix;
	
	public String getPicUrlPrefix() {
		return picUrlPrefix;
	}

	public void setPicUrlPrefix(String picUrlPrefix) {
		this.picUrlPrefix = picUrlPrefix;
	}

	public String getDownloadParamName() {
		return downloadParamName;
	}

	public void setDownloadParamName(String downloadParamName) {
		this.downloadParamName = downloadParamName;
	}

	public String getImgStorageType() {
		return imgStorageType;
	}

	public void setImgStorageType(String imgStorageType) {
		this.imgStorageType = imgStorageType;
	}

	public String getImgStorageDir() {
		return imgStorageDir;
	}

	public void setImgStorageDir(String imgStorageDir) {
		this.imgStorageDir = imgStorageDir;
	}

	public String getImgStorageTable() {
		return imgStorageTable;
	}

	public void setImgStorageTable(String imgStorageTable) {
		this.imgStorageTable = imgStorageTable;
	}

	public String getImgVirtualDir() {
		return imgVirtualDir;
	}

	public void setImgVirtualDir(String imgVirtualDir) {
		this.imgVirtualDir = imgVirtualDir;
	}

	public String getFileStorageType() {
		return fileStorageType;
	}

	public void setFileStorageType(String fileStorageType) {
		this.fileStorageType = fileStorageType;
	}

	public String getFileStorageDir() {
		return fileStorageDir;
	}

	public void setFileStorageDir(String fileStorageDir) {
		this.fileStorageDir = fileStorageDir;
	}

	public String getFileVirtualDir() {
		return fileVirtualDir;
	}

	public void setFileVirtualDir(String fileVirtualDir) {
		this.fileVirtualDir = fileVirtualDir;
	}
	
	
}
