package com.know.info.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.know.info.mapper.UserMapper;
import com.know.info.dto.UserDto;
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
        //分页查询
        PageHelper.startPage(page,pageNum);
        UserDto userDto = new UserDto();
        List<UserDto> userList = userMapper.questUserList(userDto);
        //得到分页的结果对象
        PageInfo<UserDto> userPageInfo = new PageInfo<>(userList);
        //得到分页中的UserDto条目对象
        List<UserDto> pageList = userPageInfo.getList();
        //将结果存入map进行传送
       return pageList;
    }

    public List<UserDto> queryIdList(long id) {

        List<UserDto> list = userMapper.queryIdUserList(id);
       return list;
    }

    public long post(UserDto userDto) {
        userMapper.postUser(userDto);
        System.out.println(userDto.getId());
       return  userDto.getId();
    }

    public long addBatchUser(List<UserDto> userDtos) {
        return userMapper.addBatchUser(userDtos);

    }

    public List<UserDto> queryDtoList(UserDto userDto) {
        List<UserDto> list = userMapper.questUserList(userDto);
        return list;
    }
}
