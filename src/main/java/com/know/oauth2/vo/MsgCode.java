package com.know.oauth2.vo;

/**
 * @Author: FaceCat
 * @Date: 2020/12/23 16:12
 */
public enum MsgCode {
    DEFAULT("SUCCESS", "处理成功"),
    FAILURE("FAILURE", "处理失败");

    private final String key;
    private final String desc;

    public String getKey() {
        return this.key;
    }

    public String getDesc() {
        return this.desc;
    }

    private MsgCode(String key, String desc) {
        this.key = key;
        this.desc = desc;
    }
}
