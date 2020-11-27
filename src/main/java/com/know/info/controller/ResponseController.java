package com.know.info.controller;

import com.know.info.dto.SysUserDto;
import com.know.info.service.ConsumersService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Facecat
 * @Date: 2020/5/3 9:34
 */
@RestController
@RequestMapping("/request")
public class ResponseController {

@Resource
private ConsumersService consumersService;

    @PostMapping("/getDto")
    public Map<String,Object> getDto(SysUserDto userDtoParam){
        Map<String, Object> map = new HashMap<String, Object>();
        SysUserDto userDto = userDtoParam;
        Map<String, Object> mapParam = new HashMap<>();
        mapParam.put("userName", "ad");
//        List<SysUserDto> sysUserDtoList=consumersService.queryDtoList(userDto);
        List<SysUserDto> sysUserDtoList=consumersService.queryDtoListMap(mapParam);
        for(SysUserDto dto:sysUserDtoList){
           System.out.println("实体类出参>>>"+dto.toString());
        }

        return map;

    }
    @PostMapping("/getMap")
    public Map<String,Object> getMap(Map<String,Object> userDtoParam){
        Map<String, Object> map = userDtoParam;
        return map;

    }

    @PostMapping("/getDtoOrString")
    public Map<String,Object> getDtoOrString(SysUserDto userDtoParam,String id){
        Map<String, Object> map = new HashMap<String, Object>();
        return map;

    }

}
