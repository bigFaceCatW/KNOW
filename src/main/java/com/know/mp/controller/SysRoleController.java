package com.know.mp.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.know.config.annotation.optLog;
import com.know.config.annotation.optLogConst;
import com.know.mp.dto.SysRole;
import com.know.mp.mapper.SysRoleMapper;
import com.know.mp.service.SysRoleService;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
public class SysRoleController{
    private static final Logger logger = LoggerFactory.getLogger(SysRoleController.class);

   @Autowired
   private SysRoleService sysRoleService;

    @Resource
    private SysRoleMapper sysRoleMapper;

    @PostMapping("/add")
    @optLog(optModul="mybatisPlus",optType= optLogConst.SEARCH,optDesc="查询增加")
    public void mpAdd(@RequestParam Map map){
        SysRole sysRole = new SysRole();
        sysRole.setRoleName("普通角色1");
        sysRole.setRemark("普通角色1");
        sysRole.setRoleKey("admin1");
//        新增

        sysRole.insertOrUpdate(); //sysRoleService.save(mpUser);
//        删除
        sysRole.deleteById(3);


    }

    @PostMapping("/insertOrUpdate")
    public void insertOrUpdate(){
        SysRole sysRole = new SysRole();
        sysRole.setRoleId((long) 6);
        sysRole.setRoleName("普通角色s1");
        sysRole.setRemark("普通角色1");
        sysRole.setRoleKey("admins");
        sysRole.insertOrUpdate(); //根据id修改
        sysRole.update(new QueryWrapper<SysRole>().eq("role_name", "普通角色s").eq("role_key", "admins"));


    }


//    批量增
    @PostMapping("/batchAdd")
    public void batch(){
        List<SysRole> sysRoles = new ArrayList<>();
        for (int i=3;i<8;i++){
            SysRole sysRole = new SysRole();
            sysRole.setRoleName("普通角色"+i);
            sysRole.setRemark("普通角色"+i);
            sysRole.setRoleKey("admin"+i);
            sysRoles.add(sysRole);
        }
        sysRoleService.saveBatch(sysRoles);//批量增加

    }
    //    批量修
    @PostMapping("/batchEdit")
    public void batchEdit(){
        List<SysRole> sysRoles = new ArrayList<>();
        for (int i=6;i<11;i++){
            SysRole sysRole = new SysRole();
            sysRole.setRoleId((long) i);
            sysRole.setRoleName("普通角色修改"+i);
            sysRole.setRemark("普通角色修改"+i);
            sysRole.setRoleKey("adminEdit"+i);
            sysRoles.add(sysRole);
        }
        sysRoleService.updateBatchById(sysRoles);//批量增加或者修改


    }
    //    批量删
    @PostMapping("/batchRemove")
    public void batchRemove(){
        List<Long> roleIds = new ArrayList<>();
        roleIds.add(new Long(7));
        roleIds.add(new Long(8));
        roleIds.add(new Long(9));
        sysRoleService.removeByIds(roleIds);//批量增加

    }

    @PostMapping("/pageQuery")
    public List<SysRole> pageQuery(int pageNum,int pageSize){
        IPage<SysRole> sysRoleIPage = sysRoleService.page(new Page<>(pageNum, pageSize), new QueryWrapper<SysRole>().eq("role_name","普通角色"));
        List<SysRole> sysRoles = sysRoleIPage.getRecords();
//        总页数
        long allPageNum = sysRoleIPage.getPages();
        logger.error("总页数>>>"+allPageNum);
        logger.error("pageQuery>>>"+sysRoles.toString());
        return sysRoles;

}
    @PostMapping("termQuery")
    public List<?>termQuery(@RequestBody String param){
        SysRole  sysRole = sysRoleService.getOne
                (new QueryWrapper<SysRole>().eq("role_name", "普通角色").eq("role_key", "common"));
       List<SysRole>  sysRoleList=sysRoleService.list(new QueryWrapper<SysRole>().like("role_name","角色"));
        logger.error("termQuery>>>"+sysRoleList.toString());
        logger.error("termQuery1>>>"+sysRole.toString());
        // 批量查询所有
        List<SysRole> mpUserList = sysRoleService.list();
        logger.error("termQuery1>>>"+mpUserList.toString());
        return mpUserList;
    }


    @PostMapping("/getMap")
    public Map<String,Object> getMap(@RequestParam Map<String,Object> userDtoParam){
        Map<String, Object> map = userDtoParam;
       String str= MapUtils.getString(map, "roleName");
       logger.error(str);

        List<SysRole> sysRoles = sysRoleMapper.queryMapList(map);
        for(SysRole dto:sysRoles){
            logger.error("/getMap>>>"+dto.toString());
        }

        Map<String, Object> returnParam= sysRoleMapper.queryMap(map);
        logger.error("返回map>>>"+returnParam.toString());
        return map;

    }


}
