package com.know.info.mapper;

import com.know.info.dto.UserFamilyDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: Facecat
 * @Date: 2020/5/3 9:53
 */
@Mapper
public interface OrderMapper {
//    获取订单列表
    List<UserFamilyDto> queryListMapper();
}
