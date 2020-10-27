package com.know.info.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @author: ning
 */
public class CourseType implements Serializable {

    /**
     * 课程类型ID
     */
    private long courseTypeId;
    /**
     * 课程类型名称
     */
    private String courseTypeName;
    /**
     * 课程类型描述
     */
    private String courseTypeDesc;
    /**
     * 0否 1是（默认0）
     */
    private int ifDel;
    /**
     * 0可用 1不可用（默认0）
     */
    private int ifUsable;
    /**
     * 树备用 是否叶子节点 0否 1是
     */
    private int is_left;



    /**
     * 树备用 所属1级类型 无便是0/null
     */
    private long parentIdFirst;

    public String getParentFirstName() {
        return parentFirstName;
    }

    public void setParentFirstName(String parentFirstName) {
        this.parentFirstName = parentFirstName;
    }

    private String parentFirstName;
    /**
     * 所属2级类型
     */
    private long parentIdSecond;

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    /**
     * 存入userId
     */
    private String creatorId;


    private String createDate;
    /**
     * 层级链路
     */
    private String courseTypeSeq;



    /**
     * 课程类型层级 1，2,3
     */
    private int typeLevel;
    /**
     * 排序
     */
    private int order;
    public int getIfFirst() {
        return ifFirst;
    }

    public void setIfFirst(int ifFirst) {
        this.ifFirst = ifFirst;
    }

    /**
     * 是否是1级分类
     */
   private  int ifFirst;

    public long getChannelId() {
        return channelId;
    }

    public void setChannelId(long channelId) {
        this.channelId = channelId;
    }

    /**
     * 渠道ID
     */
    private long channelId;
    private int shardingId;

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    private String channelName;

    public String getsDate() {
        return sDate;
    }

    public void setsDate(String sDate) {
        this.sDate = sDate;
    }

    public String geteDate() {
        return eDate;
    }

    public void seteDate(String eDate) {
        this.eDate = eDate;
    }

    private String sDate;
    private String eDate;

    /**
     * 下拉框使用id
     */
    private long id;

    /**
     * 下拉框使用name
     */
    private String name;

    /**
     * 下拉框children
     * 子类型
     */
    private List<CourseType> children;

    public long getParentIdFirst() {
        return parentIdFirst;
    }

    public void setParentIdFirst(long parentIdFirst) {
        this.parentIdFirst = parentIdFirst;
    }

    public long getParentIdSecond() {
        return parentIdSecond;
    }

    public void setParentIdSecond(long parentIdSecond) {
        this.parentIdSecond = parentIdSecond;
    }
    public int getTypeLevel() {
        return typeLevel;
    }

    public void setTypeLevel(int typeLevel) {
        this.typeLevel = typeLevel;
    }
    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }


    public long getCourseTypeId() {
        return courseTypeId;
    }

    public void setCourseTypeId(long courseTypeId) {
        this.courseTypeId = courseTypeId;
    }

    public String getCourseTypeName() {
        return courseTypeName;
    }

    public void setCourseTypeName(String courseTypeName) {
        this.courseTypeName = courseTypeName;
    }

    public String getCourseTypeDesc() {
        return courseTypeDesc;
    }

    public void setCourseTypeDesc(String courseTypeDesc) {
        this.courseTypeDesc = courseTypeDesc;
    }

    public int getIfDel() {
        return ifDel;
    }

    public void setIfDel(int ifDel) {
        this.ifDel = ifDel;
    }

    public int getIfUsable() {
        return ifUsable;
    }

    public void setIfUsable(int ifUsable) {
        this.ifUsable = ifUsable;
    }

    public int getIs_left() {
        return is_left;
    }

    public void setIs_left(int is_left) {
        this.is_left = is_left;
    }


    public List<CourseType> getChildren() {
        return children;
    }

    public void setChildren(List<CourseType> children) {
        this.children = children;
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

    public String getCourseTypeSeq() {
        return courseTypeSeq;
    }

    public void setCourseTypeSeq(String courseTypeSeq) {
        this.courseTypeSeq = courseTypeSeq;
    }



    public int getShardingId() {
        return shardingId;
    }

    public void setShardingId(int shardingId) {
        this.shardingId = shardingId;
    }


}
