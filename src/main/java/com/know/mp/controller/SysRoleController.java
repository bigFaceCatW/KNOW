package com.know.mp.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.know.mp.dto.SysRole;
import com.know.mp.service.SysRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 角色信息表 前端控制器
 * </p>
 *
 * @author FaceCat
 * @since 2020-12-08
 */
@RestController
@RequestMapping("/sys-role")
public class SysRoleController {
    private static final Logger log = LoggerFactory.getLogger(SysRoleController.class);
    @Resource
    private SysRoleService sysRoleService;

    @PostMapping("/add")
    public void mpAdd(){
        SysRole sysRole = new SysRole();
        sysRole.setRoleName("普通角色1");
        sysRole.setRemark("普通角色1");
        sysRole.setRoleKey("admin1");
//        新增
        sysRole.insertOrUpdate(); //sysRoleService.save(mpUser);
//        删除
        sysRole.deleteById(3);
//        修改
        sysRole.setRoleName("普通角色2");
        sysRole.setRoleId(4L);
        sysRole.insertOrUpdate();

        SysRole sysRole1 =sysRole.selectById(4);
        log.error(sysRole1.toString());

    }

    @PostMapping("/pageQuery")
    public List<SysRole> pageQuery(int pageNum,int pageSize){
        IPage<SysRole> sysRoleIPage = sysRoleService.page(new Page<>(pageNum, pageSize), new QueryWrapper<SysRole>().eq("role_name","普通角色"));
        List<SysRole> sysRoles = sysRoleIPage.getRecords();
//        总页数
        long allPageNum = sysRoleIPage.getPages();
        log.error("总页数>>>"+allPageNum);
        log.error("pageQuery>>>"+sysRoles.toString());
        return sysRoles;

}
    @PostMapping("termQuery")
    public List<?>termQuery(){
        SysRole  sysRole = sysRoleService.getOne(new QueryWrapper<SysRole>().eq("role_name", "普通角色").eq("role_key", "common"));

        log.error("termQuery>>>"+sysRole.toString());
        // 批量查询
        List<SysRole> mpUserList = sysRoleService.list();
        log.error("termQuery1>>>"+mpUserList.toString());
        return mpUserList;
    }



}
