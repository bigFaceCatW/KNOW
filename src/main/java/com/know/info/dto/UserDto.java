package com.know.info.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: BigFacecat
 * @Date: 2020/1/9 9:23
 */
public class UserDto implements Serializable {
    private static final long serialVersionUID=1L;

    private long orgId;
    private String userCode;
    private String userName;
    private String mphone;
    private String email;
    private long id;
    private String name;
    private int age;
    private int page;
    private int pageNum;
    private OrgDto orgDto;
    private List<UserFamilyDto> userFamilyDtos;

    public OrgDto getOrgDto() {
        return orgDto;
    }

    public void setOrgDto(OrgDto orgDto) {
        this.orgDto = orgDto;
    }

    public List<UserFamilyDto> getUserFamilyDtos() {
        return userFamilyDtos;
    }

    public void setUserFamilyDtos(List<UserFamilyDto> userFamilyDtos) {
        this.userFamilyDtos = userFamilyDtos;
    }

    public UserDto() {
    }

    public long getOrgId() {
        return orgId;
    }

    public void setOrgId(long orgId) {
        this.orgId = orgId;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMphone() {
        return mphone;
    }

    public void setMphone(String mphone) {
        this.mphone = mphone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "orgId=" + orgId +
                ", userCode='" + userCode + '\'' +
                ", userName='" + userName + '\'' +
                ", mphone='" + mphone + '\'' +
                ", email='" + email + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", page=" + page +
                ", pageNum=" + pageNum +
                '}';
    }
}
