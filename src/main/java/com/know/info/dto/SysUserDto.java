package com.know.info.dto;

import lombok.Data;

/**
 * @Author: FaceCat
 * @Date: 2020/11/21 14:52
 */
@Data
public class SysUserDto {
    private Long userId;
    private Long deptId;
    private String nickName;
    private String userName;
    private String userType;
    private String email;
    private String phonenumber;
    private int s;
    private Integer sex;
    private Integer status;
    private Integer delflag;
    private String loginIp;
    private String loginTime;
    private String createBy;
    private String createTime;
    private String remark;




}
