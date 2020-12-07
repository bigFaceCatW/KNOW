package com.know.info.controller;

import com.know.info.dto.SysUserDto;
import com.know.info.mapper.UserMapper;
import com.know.info.service.ConsumersService;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    private static final Logger logger = LoggerFactory.getLogger(ResponseController.class);

@Resource
private ConsumersService consumersService;

@Resource
private UserMapper userMapper;

    @PostMapping("/getDto测试")
    public Map<String,Object> getDto(SysUserDto userDtoParam){
        Map<String, Object> map = new HashMap<String, Object>();
//测试
        SysUserDto userDto = userDtoParam;
        List<SysUserDto> sysUserDtoList=userMapper.questSysUserList(userDto);
        for(SysUserDto dto:sysUserDtoList){
            logger.error("/getDto>>>"+dto.toString());
        }
        return map;

    }
    @PostMapping("/getMap")
    public Map<String,Object> getMap(@RequestParam Map<String,Object> userDtoParam){
        Map<String, Object> map = userDtoParam;
       String str= MapUtils.getString(map, "userId");
       logger.error(str);

        List<SysUserDto> sysUserDtoList = userMapper.queryMapList(map);
        for(SysUserDto dto:sysUserDtoList){
            logger.error("/getMap>>>"+dto.toString());
        }

        Map<String, Object> returnParam= userMapper.queryMap(map);
        logger.error("返回map>>>"+returnParam.toString());
        return map;

    }

    @PostMapping("/getDtoOrString")
    public Map<String,Object> getDtoOrString(SysUserDto userDtoParam,String id){
        Map<String, Object> map = new HashMap<String, Object>();
        return map;

    }

}
