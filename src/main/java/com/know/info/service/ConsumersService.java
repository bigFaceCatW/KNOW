package com.know.info.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.know.info.dto.UserDto;
import com.know.info.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: BigFacecat
 * @Date: 2020/1/9 9:23
 */
@Service
public class ConsumersService {

    @Resource
    private UserMapper userMapper;

    public List<UserDto> questList(int page,int pageNum){

        PageHelper.startPage(page,pageNum);
        List<UserDto> userList = userMapper.questUserList();
        //得到分页的结果对象
        PageInfo<UserDto> userPageInfo = new PageInfo<>(userList);
        //得到分页中的UserDto条目对象
        List<UserDto> pageList = userPageInfo.getList();
        //将结果存入map进行传送
       return pageList;
    }

}
