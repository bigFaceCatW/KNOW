package com.know.info.service;

import com.know.info.dto.UserFamilyDto;
import com.know.info.mapper.OrderMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Facecat
 * @Date: 2020/5/3 9:45
 */
@Service
public class ResponseService {
    @Resource
    private OrderMapper orderMapper;

    public List<UserFamilyDto> queryListService(){

        return orderMapper.queryListMapper();
    }
}
