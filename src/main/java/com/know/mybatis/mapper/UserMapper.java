//package com.know.mybatis.mapper;
//
//import com.know.mybatis.dto.SysUserDto;
//import com.know.mybatis.dto.UserDto;
//import org.apache.ibatis.annotations.Mapper;
//
//import java.util.List;
//import java.util.Map;
//
///**
// * @Author: BigFacecat
// * @Date: 2020/1/9 9:23
// */
//@Mapper
//public interface UserMapper {
//
//    /**
//     * 查询人员列表
//     */
//    List<SysUserDto> questSysUserList(SysUserDto userDto);
//
//    List<UserDto> questUserList(UserDto userDto);
//
//    List<UserDto> queryIdUserList(long id);
//
//    List<SysUserDto> queryMapList(Map<String, Object> map);
//
//    long postUser(UserDto userDto);
//
//    long addBatchUser(List<UserDto> userDtos);
//
//    Map<String,Object> queryMap(Map<String, Object> map);
//
//    List<SysUserDto> questSysUserListMap(Map<String, Object> map);
//}
//
