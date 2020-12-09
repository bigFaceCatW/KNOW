package com.know.mybatis.dto;

import java.io.Serializable;

/** 
 * ClassName: SysFile
 * 附件实体
 * @author fs 
 * date: 2018年11月10日 下午3:38:29
 */
public class SysFile implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4745986742290744792L;
	
	/**
	 * 附件主键
	 */
	private long fileId;
	/**
	 * 文件名
	 */
	private String  fileName;
	/**
	 * 文件路径
	 */
	private String  absolutePath;
	/**
	 * 文件类型
	 */
	private String fileType;
	/**
	 * 上传人
	 */
	private int  staffId;
	/**
	 * 所属分组
	 */
	private String  groupCode;
	/**
	 * 关联主键
	 */
	private int  relationId;

	private String  tenantId; 
	private int  shardingId;
	private long  fileSize;
	
	
	
	

	public long getFileSize() {
		return fileSize;
	}
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
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
	public String getAbsolutePath() {
		return absolutePath;
	}
	public void setAbsolutePath(String absolutePath) {
		this.absolutePath = absolutePath;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public int getStaffId() {
		return staffId;
	}
	public void setStaffId(int staffId) {
		this.staffId = staffId;
	}
	public String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	public int getRelationId() {
		return relationId;
	}
	public void setRelationId(int relationId) {
		this.relationId = relationId;
	}
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public int getShardingId() {
		return shardingId;
	}
	public void setShardingId(int shardingId) {
		this.shardingId = shardingId;
	} 
	
	
}
