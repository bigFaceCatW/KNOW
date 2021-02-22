package com.know.config.annotation;

public enum optLogConst {
	
	DEFAULT(""),
	ADD("新增"),
	UPDATE("修改"),
	DELETE("删除"),
	SEARCH("搜索"),
	UPLOAD("上传"),
	DOWNLOAD("下载");
	
	private String desc;

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	private optLogConst( String desc ){
        this.desc = desc ;
    }
}
