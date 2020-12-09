package com.know.mybatis.dto;


import com.know.util.ExcelResources;

import java.io.Serializable;

/**
 * @author: FaceCat
 * @date: 2020/8/17 11:25
 */
public class UnUserDTO implements Serializable {
    private static final long serialVersionUID = 5717321807237340087L;
    private long id;
   @ExcelResources(title="账号",order=1,width=100)
   private String userId;
    @ExcelResources(title="姓名",order=2,width=100)
   private String userName;
    @ExcelResources(title="地域",order=3,width=100)
    private String areaSeq; //地域序列名称
    @ExcelResources(title="手机",order=4,width=100)
    private String mobile;//号码

    @ExcelResources(title="邮箱",order=5,width=100)
    private String email;
    @ExcelResources(title="渠道",order=6,width=100)
   private String channel;
   private int examType; //0必考，1非必考
    @ExcelResources(title="组织机构",order=7,width=100)
    private String orgName;
    private long orgId;
   private int areaId; //地域id
    @ExcelResources(title="用户隶属性质",order=8,width=100)
   private String userNature; //用工隶属性质
    @ExcelResources(title="考试类别",order=9,width=100)
    private String examTypeName; //必考，非必考

    @ExcelResources(title="考试开始时间",order=10,width=100)
    private String stime; //必考，非必考
    @ExcelResources(title="考试结束时间",order=11,width=100)
    private String etime; //必考，非必考

    @ExcelResources(title="管理员",order=12,width=100)
    private String manageUserId; //管理员id

    public String getStime() {
        return stime;
    }

    public void setStime(String stime) {
        this.stime = stime;
    }

    public String getEtime() {
        return etime;
    }

    public void setEtime(String etime) {
        this.etime = etime;
    }

    public String getManageUserId() {
        return manageUserId;
    }

    public void setManageUserId(String manageUserId) {
        this.manageUserId = manageUserId;
    }

    public String getAreaSeq() {
        return areaSeq;
    }

    public void setAreaSeq(String areaSeq) {
        this.areaSeq = areaSeq;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getExamTypeName() {
        return examTypeName;
    }

    public void setExamTypeName(String examTypeName) {
        this.examTypeName = examTypeName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public long getOrgId() {
        return orgId;
    }

    public void setOrgId(long orgId) {
        this.orgId = orgId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public int getExamType() {
        return examType;
    }

    public void setExamType(int examType) {
        this.examType = examType;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public String getUserNature() {
        return userNature;
    }

    public void setUserNature(String userNature) {
        this.userNature = userNature;
    }
}
