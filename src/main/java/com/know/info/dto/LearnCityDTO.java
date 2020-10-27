package com.know.info.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @author: FaceCat
 * @date: 2020/8/13 16:33
 */
public class LearnCityDTO implements Serializable {
    private static final long serialVersionUID = 2352026422775026553L;

    private String cityId;
    private String cityPreId;
    private int cityLevel;
    private String cityName;
    private Boolean open;
    private Boolean isParent;
    private String cityIdSeq;
    private String cityNameSeq;
    private long id;
    private String name;
    private List<LearnCityDTO> children;

    public List<LearnCityDTO> getChildren() {
        return children;
    }

    public void setChildren(List<LearnCityDTO> children) {
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

    public Boolean getIsParent() {
        return isParent;
    }

    public void setIsParent(Boolean parent) {
        isParent = parent;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityPreId() {
        return cityPreId;
    }

    public void setCityPreId(String cityPreId) {
        this.cityPreId = cityPreId;
    }

    public int getCityLevel() {
        return cityLevel;
    }

    public void setCityLevel(int cityLevel) {
        this.cityLevel = cityLevel;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public Boolean getParent() {
        return isParent;
    }

    public void setParent(Boolean parent) {
        isParent = parent;
    }

    public String getCityIdSeq() {
        return cityIdSeq;
    }

    public void setCityIdSeq(String cityIdSeq) {
        this.cityIdSeq = cityIdSeq;
    }

    public String getCityNameSeq() {
        return cityNameSeq;
    }

    public void setCityNameSeq(String cityNameSeq) {
        this.cityNameSeq = cityNameSeq;
    }
}
