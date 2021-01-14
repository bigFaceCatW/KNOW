package com.know.oauth2.vo;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: FaceCat
 * @Date: 2020/12/23 16:12
 */
public class RspResult {
    @ApiModelProperty(
            value = "业务状态码",
            name = "RSP_CODE",
            required = true
    )
    @JSONField(
            name = "RSP_CODE"
    )
    private String RSP_CODE;
    @ApiModelProperty(
            value = "返回业务结果描述",
            name = "RSP_DESC"
    )
    @JSONField(
            name = "RSP_DESC"
    )
    private String RSP_DESC;
    @ApiModelProperty(
            value = "结果的内容",
            name = "DATA",
            required = true
    )
    @JSONField(
            name = "DATA"
    )
    private Object DATA;
    @ApiModelProperty(
            value = "扩展结构",
            name = "ATTACH"
    )
    @JSONField(
            name = "ATTACH"
    )
    private Object ATTACH;

    public RspResult() {
        this.RSP_CODE = StatusCode.DEFAULT.getKey();
        this.RSP_DESC = MsgCode.DEFAULT.getKey();
    }

    public String getRSP_CODE() {
        return this.RSP_CODE;
    }

    public void setRSP_CODE(String RSP_CODE) {
        this.RSP_CODE = RSP_CODE;
    }

    public String getRSP_DESC() {
        return this.RSP_DESC;
    }

    public void setRSP_DESC(String RSP_DESC) {
        this.RSP_DESC = RSP_DESC;
    }

    public Object getDATA() {
        return this.DATA;
    }

    public void setDATA(Object DATA) {
        this.DATA = DATA;
    }

    public Object getATTACH() {
        return this.ATTACH;
    }

    public void setATTACH(Object ATTACH) {
        this.ATTACH = ATTACH;
    }
}
