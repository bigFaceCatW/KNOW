package com.know.mybatis.dto;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @Author: Facecat
 * @Date: 2020/2/23 9:27
 */
public class OrgDto implements Serializable {

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


    public static void main(String[] args) {
        List<OrgDto> list = new ArrayList<>();
        OrgDto orgDto = new OrgDto();
        orgDto.setCreateTime("2020年3月16日16:51:47");
        OrgDto orgDto1 = new OrgDto();
        orgDto1.setOrgCode("2021");
        orgDto1.setCreateTime("2020年3月16日16:51:48");
        list.add(orgDto);
        list.add(orgDto1);

        Optional<OrgDto> msg = Optional.ofNullable(orgDto);
        Object object = msg.map(p -> p.getOrgCode())
                .map(name -> name.toUpperCase())
                .orElse(null);
        
        System.out.println(JSON.toJSONString(object));
    }
}
