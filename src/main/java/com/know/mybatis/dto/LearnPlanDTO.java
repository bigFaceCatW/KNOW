package com.know.mybatis.dto;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.io.Serializable;

/**
 * @author: FaceCat
 * @date: 2020/8/9 10:51
 */
public class LearnPlanDTO implements Serializable {
    private static final long serialVersionUID = 1723082229918632082L;

    private long planId;
    private String planName;
    private String planDesc;
    private int planType;
    private int cutNum;
    private int isPush;
    private int isOrder;
    private String code;
    private String sTime;
    private String eTime;
    private String cTime;
    private String userId;
    private String userName;
    private int isDel;
    private String area;  //地区名称序列
    private String areaId; //地区id序列
    private String strAreaId;
    private int questionNum; //题库次数
    private String role; //角色名称
    private String channelName;
    private String channelId;
    private String questionStr;
    private int flag;
    private int importQuestionNum;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long groupId;
    private String oldArea;
    private String oldChannel;
    private int oldQuestionNum;
    private long recordId;
    private String manageType;
    private String manageAreaCode;
    private String state;//考试状态
    private Long examTime;
    private double totalScore;
    private String areaName;

    public int getOldQuestionNum() {
        return oldQuestionNum;
    }

    public void setOldQuestionNum(int oldQuestionNum) {
        this.oldQuestionNum = oldQuestionNum;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(double totalScore) {
        this.totalScore = totalScore;
    }

    public Long getExamTime() {
        return examTime;
    }

    public void setExamTime(Long examTime) {
        this.examTime = examTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getManageType() {
        return manageType;
    }

    public void setManageType(String manageType) {
        this.manageType = manageType;
    }

    public String getManageAreaCode() {
        return manageAreaCode;
    }

    public void setManageAreaCode(String manageAreaCode) {
        this.manageAreaCode = manageAreaCode;
    }

    public long getRecordId() {
        return recordId;
    }

    public void setRecordId(long recordId) {
        this.recordId = recordId;
    }

    public String getOldArea() {
        return oldArea;
    }

    public void setOldArea(String oldArea) {
        this.oldArea = oldArea;
    }

    public String getOldChannel() {
        return oldChannel;
    }

    public void setOldChannel(String oldChannel) {
        this.oldChannel = oldChannel;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }


    public String getStrAreaId() {
        return strAreaId;
    }

    public void setStrAreaId(String strAreaId) {
        this.strAreaId = strAreaId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getImportQuestionNum() {
        return importQuestionNum;
    }

    public void setImportQuestionNum(int importQuestionNum) {
        this.importQuestionNum = importQuestionNum;
    }

    public String getQuestionStr() {
        return questionStr;
    }

    public void setQuestionStr(String questionStr) {
        this.questionStr = questionStr;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public long getPlanId() {
        return planId;
    }

    public void setPlanId(long planId) {
        this.planId = planId;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getPlanDesc() {
        return planDesc;
    }

    public void setPlanDesc(String planDesc) {
        this.planDesc = planDesc;
    }

    public int getPlanType() {
        return planType;
    }

    public void setPlanType(int planType) {
        this.planType = planType;
    }

    public int getCutNum() {
        return cutNum;
    }

    public void setCutNum(int cutNum) {
        this.cutNum = cutNum;
    }

    public int getIsPush() {
        return isPush;
    }

    public void setIsPush(int isPush) {
        this.isPush = isPush;
    }

    public int getIsOrder() {
        return isOrder;
    }

    public void setIsOrder(int isOrder) {
        this.isOrder = isOrder;
    }



    public String getsTime() {
        return sTime;
    }

    public void setsTime(String sTime) {
        this.sTime = sTime;
    }

    public String geteTime() {
        return eTime;
    }

    public void seteTime(String eTime) {
        this.eTime = eTime;
    }

    public String getcTime() {
        return cTime;
    }

    public void setcTime(String cTime) {
        this.cTime = cTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getIsDel() {
        return isDel;
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public int getQuestionNum() {
        return questionNum;
    }

    public void setQuestionNum(int questionNum) {
        this.questionNum = questionNum;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


}


