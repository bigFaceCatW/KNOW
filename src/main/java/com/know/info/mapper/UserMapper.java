package com.know.info.mapper;

import com.know.info.dto.SysUserDto;
import com.know.info.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @Author: BigFacecat
 * @Date: 2020/1/9 9:23
 */
@Mapper
public interface UserMapper {

    /**
     * 查询人员列表
     */
    List<SysUserDto> questSysUserList(SysUserDto userDto);

    List<UserDto> questUserList(UserDto userDto);

    List<UserDto> queryIdUserList(long id);

    long postUser(UserDto userDto);

    long addBatchUser(List<UserDto> userDtos);

    List<SysUserDto> questSysUserListMap(Map<String, Object> map);
}

