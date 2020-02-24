package com.know.zk;

import com.know.redis.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @Author: Facecat
 * @Date: 2020/2/22 15:47
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    @Resource
    private RedisUtil redisUtil;

    @Test
    public  void set(){
        redisUtil.set("key", "2020");
    }
    @Test
    public  void get(){
       Object str= redisUtil.get("key");
       System.out.println(str);
    }

}
