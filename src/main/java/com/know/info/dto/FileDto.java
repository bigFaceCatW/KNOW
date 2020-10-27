package com.know.info.dto;

import java.util.Date;

/**
 * Demo class
 * 
 * @author test
 * @date 2018/10/31
 */
public class FileDto {
	/**
	 *  附件主键
	 */
	private long fileId;
	/**
	 * 文件名
	 */
	private String fileName;


	private String yFileName;
	/**
	 * 文件路径
	 */
	private String absolutepath;
	/**
	 *  客服服务器文件路径
	 */
	private String kfabsolutepath;
	/**
	 * 文件大小
	 */
	private Long fileSize;
	/**
	 * 文件类型
	 */
	private String fileType;
	/**
	 * 0-课件，1-预览图
	 */
	private int fileKind;
	 /**
	  *  上传人姓名
	  */
	private String userName;
	 /**
	  *  上传人
	  */
	private long staffId;

	private  String userId;
	/**
	 * 上传时间
	 */
	private Date fileTime;
	/**
	 * 所属分组
	 */
	private String groupCode;
	/**
	 * 关联主键
	 */
	private long relationId;
	/**
	 * 下载次数
	 */
	private long downloadTimes;
	/**
	 *  是否删除
	 */
	private int isDel;
	/**
	 * 分库字段
	 */
	private int shardingId;
	/**
	 * 文件用途
	 */
	private int fileUse;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public long getFileId() {
		return fileId;
	}

	public void setFileId(long fileId) {
		this.fileId = fileId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getAbsolutepath() {
		return absolutepath;
	}

	public void setAbsolutepath(String absolutepath) {
		this.absolutepath = absolutepath;
	}

	public String getKfabsolutepath() {
		return kfabsolutepath;
	}

	public void setKfabsolutepath(String kfabsolutepath) {
		this.kfabsolutepath = kfabsolutepath;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public long getStaffId() {
		return staffId;
	}

	public void setStaffId(long staffId) {
		this.staffId = staffId;
	}

	public Date getFileTime() {
		return fileTime;
	}

	public void setFileTime(Date fileTime) {
		this.fileTime = fileTime;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public long getRelationId() {
		return relationId;
	}

	public void setRelationId(long relationId) {
		this.relationId = relationId;
	}

	public long getDownloadTimes() {
		return downloadTimes;
	}

	public void setDownloadTimes(long downloadTimes) {
		this.downloadTimes = downloadTimes;
	}

	public int getIsDel() {
		return isDel;
	}

	public void setIsDel(int isDel) {
		this.isDel = isDel;
	}

	public int getShardingId() {
		return shardingId;
	}

	public void setShardingId(int shardingId) {
		this.shardingId = shardingId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getFileUse() {
		return fileUse;
	}

	public void setFileUse(int fileUse) {
		this.fileUse = fileUse;
	}


	public String getyFileName() {
		return yFileName;
	}

	public void setyFileName(String yFileName) {
		this.yFileName = yFileName;
	}


	public int getFileKind() {
		return fileKind;
	}

	public void setFileKind(int fileKind) {
		this.fileKind = fileKind;
	}
}
