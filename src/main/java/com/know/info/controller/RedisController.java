package com.know.info.controller;


import com.know.info.dto.UserDto;
import com.know.info.service.ConsumersService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: BigFacecat
 * @Date: 2020/1/9 9:23
 */
@RestController
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
    @GetMapping("/redis")
    public List<UserDto> consumersTest(){
        UserDto user = new UserDto();
        user.setAge(13);
        user.setName("测试");
       boolean result= redisUtil.set("users",user);
       System.out.println(result);
        UserDto userDto=null;
        try{
            userDto = (UserDto)redisUtil.get("users");
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("我的名字叫"+userDto.getName()+",我今年"+userDto.getAge()+"岁了！");

        return consumersService.questList(userDto.getPage(),userDto.getPageNum());
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
    @PostMapping("/redis")
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
}
