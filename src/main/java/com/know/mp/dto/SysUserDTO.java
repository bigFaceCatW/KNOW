package com.know.mp.dto;

import lombok.Data;

/**
 * @Author: FaceCat
 * @Date: 2021/2/22 11:42
 */
@Data
public class SysUserDTO {
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

