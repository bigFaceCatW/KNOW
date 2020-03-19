package com.know.redis;

import com.alibaba.fastjson.JSON;
import com.know.config.dto.PageObj;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Author: Facecat
 * @Date: 2020/2/22 15:47
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    @Resource
    private RedisUtil redisUtil;
    @Resource
    private PageObj pageObj;

    //    ================================================String=================================================
    @Test
    public void set() {
        redisUtil.set("key", "2020");
    }

    @Test
    public void get() {
        Object str = redisUtil.get("key");
        System.out.println(str);
    }

    @Test
    public void expire() {
        redisUtil.expire("key", 10);
    }

    //    ====================================================Map========================================
    @Test
    public void hashMapSet() {
        //-----添加数据----------
        Map<String, Object> map = new HashMap<>();
        map.put("name", "测试");
        map.put("age", 22);
        map.put("time", "2020-02-29");
        redisUtil.hmset("map", map);
    }

    @Test
    public void hashMapGet() {
        //-----获取数据----------
        Map<Object, Object> map = redisUtil.hmget("map");
        String str = JSON.toJSONString(map);
        System.out.println(str);
    }

    @Test
    public void hashSet() {
        //-----修改数据----------
        Map<String, Object> mapTow = new HashMap<>();
      mapTow.put("address", "合肥");
        boolean bool= redisUtil.hset("map","address",1);
        System.out.println(bool);

    }
    @Test
    public void hashGet() {
        //-----添加数据----------
       Object map =  redisUtil.hget("map","age");
        String str = JSON.toJSONString(map);
        System.out.println(str);
    }

   /* @Test
    public void hincr() {
        //-----添加数据----------
        redisUtil.hincr("map","address",3);
    }*/


    @Test
    public void hashDel() {
        redisUtil.hdel("map","name");
    }
//    ====================================================set======================================

    @Test
    public void setSet() {
        redisUtil.sSet("test", 1,"ceshi",2,3,4,5,6,7,8,9);
    }

    @Test
    public void getSet() {
        Set<Object> set = redisUtil.sGet("test");
        for(Object obj:set){
            System.out.println(obj.toString());
        }
    }

    @Test
    public void sGetSetSize() {
        long setSize = redisUtil.sGetSetSize("test");
        System.out.println(setSize);
    }

    @Test
    public void setRemove() {
        long setSize = redisUtil.setRemove("set",1);
        System.out.println(setSize);
    }

//    ====================================================list======================================

    @Test
    public void lSet() {
         redisUtil.allSet("list",1,2,3,4,5);
    }

    @Test
    public void allSet() {

        List<Object> list = new ArrayList<>();
        pageObj.setPage(1);
        pageObj.setRows(2);
        list.add(6);
        list.add("7");
        list.add(pageObj);
        boolean bool = redisUtil.lSet("list",list);
        System.out.println(bool);
    }

    @Test
    public void lGetListSize() {
       long bool= redisUtil.lGetListSize("list");
       System.out.println(bool);
    }

    @Test
    public void lGet() {
       List<Object> list = redisUtil.lGet("list",0,-1);
       for(Object obj :list){
           System.out.println(obj.toString());
           System.out.println(JSON.toJSONString(obj));
       }
       }
    @Test
    public void lGetIndex() {
        redisUtil.lGetIndex("list",1);
    }

    @Test
    public void lUpdateIndex() {
        boolean bool = redisUtil.lUpdateIndex("list",1,9);
        System.out.println(bool);
    }


    @Test
    public void lRemove() {
        long bool = redisUtil.lRemove("list",2,9);
        System.out.println(bool);
    }



















}