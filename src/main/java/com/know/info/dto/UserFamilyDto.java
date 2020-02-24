package com.know.info.dto;

/**
 * @Author: Facecat
 * @Date: 2020/2/23 9:16
 */
public class UserFamilyDto {
    private String address;
    private String createTime;
    private long userId;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
