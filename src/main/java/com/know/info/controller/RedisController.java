package com.know.info.controller;


import com.know.info.dto.UserDto;
import com.know.info.service.ConsumersService;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: BigFacecat
 * @Date: 2020/1/9 9:23
 */
@RestController
@RequestMapping("/redis")
public class RedisController {

    @Resource
    private ConsumersService consumersService;
   @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private com.know.redis.RedisUtil redisUtil;

    /**
     * redis获取人员
     *
     */
    @GetMapping("/stringAdd")
    @ApiOperation("添加用户的接口")
    public List<UserDto> consumersTest(){
        List<UserDto> list = new ArrayList<>();
       UserDto  user = new UserDto();
        user.setAge(16);
        user.setName("测试16");
       boolean result= redisUtil.set("users",user);

        UserDto userDto=null;
        try{
            userDto = (UserDto)redisUtil.get("users");

        }catch (Exception e){
            e.printStackTrace();
        }
        list.add(user);
        UserDto  user1 = new UserDto();
        user1.setAge(17);
        user1.setName("测试17");
        list.add(user1);
//        long  lenght =redisUtil.listSingleSet("list", list);
       long  lenght =redisUtil.listSingleLeftSet("list", list);

        System.out.println(lenght);
        List<UserDto> list1 = (List<UserDto>) redisUtil.lGetIndex("list",0);
        System.out.println( "下标0=====》"+list1.toString());
        List<Object> list2 =redisUtil.lGet("list",0,-1);
        System.out.println( "全部=====》"+list2.toString());
        return list1;
    }





    /**
     * 根据id查询数据
     *
     */
    @GetMapping("/{id}")
    public List<UserDto> consumersTest(@PathVariable long id){
                  return consumersService.queryIdList(id);

    }

    /**
     * 插入返回主键
     */
    @PostMapping("/primary")
    public long addUser(UserDto userDto){
        return  consumersService.post(userDto);

    }

    /**
     * 批量插入
     *
     */
     @PostMapping("/redisBatch")
    public  long  addBatchUser(List<UserDto> userDtos){
         return consumersService.addBatchUser(userDtos);
     }





    public static void main(String[] args) {


    }



}
