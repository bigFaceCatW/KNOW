package com.know.mp.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.know.mp.dto.SysRole;
import com.know.mp.mapper.SysRoleMapper;
import com.know.util.BeanUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
    @Resource
    private SysRoleMapper sysRoleMapper;
    //    public List<SysUserDTO> questList(int page,int pageNum){
//        //分页查询
//        PageHelper.startPage(page,pageNum);
//        SysUserDTO userDto = new SysUserDTO();
//        List<SysUserDTO> userList = userMapper.questSysUserList(userDto);
//        //得到分页的结果对象
//        PageInfo<SysUserDTO> userPageInfo = new PageInfo<>(userList);
//        //得到分页中的UserDto条目对象
//        List<SysUserDTO> pageList = userPageInfo.getList();
//        //将结果存入map进行传送
//       return pageList;
//    }
//    自定义分页查询

    public Page<SysRole> pageQuerySer(int current,int size){
        // 创建条件构造器
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.ge(SysRole::getRoleId,1);
        Page<SysRole> searchPage = new Page<>(current,size);
        IPage<SysRole> data = sysRoleMapper.pageQueryMapper(searchPage,queryWrapper);
        return BeanUtil.copyPage(data);
    }


}
