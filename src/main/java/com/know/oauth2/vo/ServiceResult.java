package com.know.oauth2.vo;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.util.StringUtils;

/**
 * @Author: FaceCat
 * @Date: 2020/12/23 16:12
 */
@ApiModel("接口返回对象")
public class ServiceResult {
    @ApiModelProperty(
            value = "服务请求结果编码",
            name = "STATUS",
            required = true,
            example = "0000"
    )
    @JSONField(
            name = "STATUS"
    )
    private String STATUS;
    @ApiModelProperty(
            value = "服务请求结果描述",
            name = "MSG",
            required = true
    )
    @JSONField(
            name = "MSG"
    )
    private String MSG;
    @ApiModelProperty(
            value = "服务流水号",
            name = "TXID"
    )
    @JSONField(
            name = "TXID"
    )
    private String TXID;
    @JSONField(
            name = "RSP"
    )
    private RspResult RSP;

    public ServiceResult() {
        this.STATUS = StatusCode.DEFAULT.getKey();
        this.MSG = MsgCode.DEFAULT.getKey();
    }

    public String getSTATUS() {
        return this.STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getMSG() {
        return this.MSG;
    }

    public void setMSG(String MSG) {
        this.MSG = MSG;
    }

    public String getTXID() {
        return this.TXID;
    }

    public void setTXID(String TXID) {
        this.TXID = TXID;
    }

    public RspResult getRSP() {
        return this.RSP;
    }

    public ServiceResult setRSP(RspResult RSP) {
        this.RSP = RSP;
        return this;
    }

    public ServiceResult setRSP(String RSP_CODE, String RSP_DESC, Object DATA) {
        RspResult rr = new RspResult();
        rr.setRSP_CODE(StringUtils.isEmpty(RSP_CODE) ? StatusCode.DEFAULT.getKey() : RSP_CODE);
        rr.setRSP_DESC(StringUtils.isEmpty(RSP_DESC) ? MsgCode.DEFAULT.getKey() : RSP_DESC);
        rr.setDATA(DATA);
        this.RSP = rr;
        return this;
    }

    public ServiceResult setRSP() {
        return this.setRSP((String)null, (String)null, (Object)null);
    }

    public void aa() {
        Object data = null;
        this.setRSP((String)null, (String)null, data);
    }
}
