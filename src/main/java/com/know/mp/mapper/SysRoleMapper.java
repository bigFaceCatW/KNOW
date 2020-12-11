package com.know.mp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.know.mp.dto.SysRole;

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


}
