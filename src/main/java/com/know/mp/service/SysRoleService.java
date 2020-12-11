package com.know.mp.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.know.mp.dto.SysRole;
import com.know.mp.mapper.SysRoleMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色信息表 服务类
 * </p>
 *
 * @author FaceCat
 * @since 2020-12-08
 */
@Service
public class SysRoleService extends ServiceImpl<SysRoleMapper, SysRole>  {
    //    public List<SysUserDto> questList(int page,int pageNum){
//        //分页查询
//        PageHelper.startPage(page,pageNum);
//        SysUserDto userDto = new SysUserDto();
//        List<SysUserDto> userList = userMapper.questSysUserList(userDto);
//        //得到分页的结果对象
//        PageInfo<SysUserDto> userPageInfo = new PageInfo<>(userList);
//        //得到分页中的UserDto条目对象
//        List<SysUserDto> pageList = userPageInfo.getList();
//        //将结果存入map进行传送
//       return pageList;
//    }

}
