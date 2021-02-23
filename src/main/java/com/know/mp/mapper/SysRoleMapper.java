package com.know.mp.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.know.mp.dto.SysRole;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色信息表 Mapper 接口
 * </p>
 *
 * @author FaceCat
 * @since 2020-12-08
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {



    List<SysRole> queryMapList(Map<String, Object> map);

    long addBatchUser(List<SysRole> sysRoles);

    Map<String,Object> queryMap(Map<String, Object> map);

    @Select("SELECT * FROM sys_role ${ew.customSqlSegment} ")
    IPage<SysRole> pageQueryMapper(Page<SysRole> searchPage, @Param(Constants.WRAPPER)LambdaQueryWrapper<SysRole> queryWrapper);
}
