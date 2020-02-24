package com.know.info.dto;

/**
 * @Author: Facecat
 * @Date: 2020/2/23 9:27
 */
public class OrgDto {

    private long orgId;
    private String orgCode;
    private String createTime;

    public long getOrgId() {
        return orgId;
    }

    public void setOrgId(long orgId) {
        this.orgId = orgId;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
