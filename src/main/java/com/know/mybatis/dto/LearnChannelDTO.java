package com.know.mybatis.dto;

import java.io.Serializable;

/**
 * @author: FaceCat
 * @date: 2020/8/14 11:36
 */
public class LearnChannelDTO implements Serializable {
    private static final long serialVersionUID = 610610123527123982L;
    private int value;
    private String name;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
