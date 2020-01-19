package com.know.info.mapper;

import com.know.info.dto.UserDto;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: BigFacecat
 * @Date: 2020/1/9 9:23
 */
@Mapper
@CacheNamespace
public interface UserMapper {

    /**
     * 查询人员列表
     */
    List<UserDto> questUserList();

    List<UserDto> queryIdUserList(long id);

    long postUser(UserDto userDto);

    long addBatchUser(List<UserDto> userDtos);
}

