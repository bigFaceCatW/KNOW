//package com.know.redis;
//
//import com.alibaba.fastjson.JSON;
//import com.know.config.dto.PageObj;
//import com.know.info.dto.OrgDto;
//import org.apache.commons.collections.map.HashedMap;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import javax.annotation.Resource;
//import java.util.*;
//
///**
// * @Author: Facecat
// * @Date: 2020/2/22 15:47
// */
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class RedisTest {
//
//    @Resource
//    private RedisUtil redisUtil;
//    @Resource
//    private PageObj pageObj;
//    @Resource
//    private RedisTemplate<String, Object> redisTemplate;
//
//    //    ================================================String=================================================
//    @Test
//    public void set() {
////        存一个值
//        redisTemplate.opsForValue().set("key", "StringValue");
//    }
//    @Test
//    public void mset() {
////        存多个值
//        Map<String, Object> map = new HashedMap();
//        map.put("time", "2020-8-2 14:04:33");
//        map.put("age", "28");
//        map.put("one", "18岁的爱情很简单,我爱你就够了;28岁的爱情也很简单,没有就算了");
//        redisTemplate.opsForValue().multiSet(map);
//    }
//    @Test
//    public void get() {
////        return key == null ? null : redisTemplate.opsForValue().get(key);
//        Object str = redisUtil.get("key");
//        System.out.println(str);
//    }
//    @Test
//    public void mget() {
////        获取多个值
//        List<String> list = new ArrayList<>();
//        list.add("time");
//        list.add("age");
//        list.add("one");
//        List<Object> returnList= redisTemplate.opsForValue().multiGet(list);
//        System.out.println(returnList.toString());
//    }
//
//    @Test
//    public void expire() {
//        redisUtil.expire("key", 10);
//    }
//
//    @Test
//    public void getSetStr() {
//        Object param=redisTemplate.opsForValue().getAndSet("time", "2020-8-2 14:21:51");
//        System.out.println(param.toString());
//    }
//
//    @Test
//    public void delKey() {
////        删除key
//        redisTemplate.delete("listStr");
//
//    }
//
//    @Test
//    public void hashKey() {
////        删除key
//       boolean iskey= redisTemplate.hasKey("listStr");
//       System.out.println(iskey);
//    }
//
//
//    //    ====================================================Map========================================
//    @Test
//    public void hashMapSet() {
//        //-----添加多个数据----------
//        Map<String, Object> map = new HashMap<>();
//        map.put("name", "测试");
//        map.put("age", 22);
//        map.put("time", "2020-02-29");
//        redisUtil.hmset("map", map);
//    }
//
//    @Test
//    public void hashSet() {
//        //-----添加单个数据----------
//        redisTemplate.opsForHash().put("map","address",1);
//        redisTemplate.opsForHash().put("map","time","2020-7-30 07:05:27");
//    }
//    @Test
//    public void hashSetTnx() {
//        //-----key不存在添加数据----------
//        OrgDto dto = new OrgDto();
//        dto.setOrgId(100);
//        dto.setOrgCode("1000");
//        List<Object> list = new ArrayList<>();
//        list.add(1);
//        list.add("liuchuanfen");
//        list.add(dto);
//        redisTemplate.opsForHash().putIfAbsent("map","name","wenlei");
//        redisTemplate.opsForHash().putIfAbsent("map","anything",list);
//    }
//
//    @Test
//    public void delHashMap() {
//        //-----添加数据----------
//        redisTemplate.opsForHash().delete("map","name");
//    }
//    @Test
//    public void hashMapKey() {
//        //-----获取key集合----------
//        Set<?> map = redisTemplate.opsForHash().keys("map");
//        String str = JSON.toJSONString(map);
//        System.out.println(str);
//    }
//
//    @Test
//    public void hashMapLen() {
//        //-----获取key集合----------
//        long mapLen = redisTemplate.opsForHash().size("map");
//        System.out.println(mapLen);
//    }
//
//    @Test
//    public void hashMapGet() {
//        //-----获取全部数据----------
//        Map<Object, Object> map = redisTemplate.opsForHash().entries("map");
//        String str = JSON.toJSONString(map);
//        System.out.println(str);
//    }
//
//    @Test
//    public void hashGetMulit() {
//        //-----获取单个数据----------
//        List<Object> keys = new ArrayList<>();
//        keys.add("address");
//        keys.add("time");
//        List<?> mulitValue =  redisTemplate.opsForHash().multiGet("map",keys);
//        String str = JSON.toJSONString(mulitValue);
//        System.out.println(str);
//    }
//
//    @Test
//    public void hashGet() {
//        //-----获取单个数据----------
//        Object map =  redisTemplate.opsForHash().get("map","age");
//        String str = JSON.toJSONString(map);
//        System.out.println(str);
//    }
//   /* @Test
//    public void hincr() {
//        //-----添加数据----------
//        redisUtil.hincr("map","address",3);
//    }*/
//
////    ====================================================list======================================
////左边插入，返回list长度
//   @Test
//   public void lSet() {
//       long size = redisTemplate.opsForList().leftPush("listStr",1);
//   }
//
//    @Test
//    public void rSet() {
//        redisTemplate.opsForList().rightPush("listStr",2);
//    }
//
//    //批量加入队列
//    @Test
//    public void rightAllSet() {
//        redisTemplate.opsForList().rightPushAll("listStr",3,4,5,6);
//        List<Object> list = new ArrayList<>();
//        pageObj.setPage(1);
//        pageObj.setRows(2);
//        list.add(6);
//        list.add("7");
//        list.add(pageObj);
//        redisTemplate.opsForList().rightPushAll("listStr",list);
//    }
//
//    //    弹出第一个元素
//    @Test
//    public void lpop() {
//        redisTemplate.opsForList().leftPop("listStr");
//
//    }
//
//    //通过下标设置值
//    @Test
//    public void listSet() {
//        redisTemplate.opsForList().set("listStr",0,2);
//    }
//
//    //    通过下标获取
//    @Test
//    public void ListIndex() {
////    索引 index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
//        Object returnIndex= redisTemplate.opsForList().index("listStr",1);
//        System.out.println(returnIndex.toString());
//
//        List<Object> redisList =redisTemplate.opsForList().range("listStr",2,5);
//        System.out.println(redisList.toString());
//    }
//
//    //    获取list的长度
//    @Test
//    public void lGetListSize() {
//        long bool= redisTemplate.opsForList().size("list");
//        System.out.println(bool);
//    }
//
//
//    //根据index修改list中的某条数据
//    @Test
//    public void lUpdateIndex() {
//        boolean bool = redisUtil.lUpdateIndex("list",1,9);
//        System.out.println(bool);
//    }
//    //    将9放到3前面,失败返回：-1|成功返回list长度;rightLinsert 则将9放到3后面
//    @Test
//    public void leftLinsert() {
//        long bool = redisTemplate.opsForList().leftPush("listStr",3,9);
//        System.out.println(bool);
//    }
//
//    // /** 移除N个值为value
////     * @param key 键
////     * @param count 移除多少个
////     * @param value 值
////     * @return 移除的个数
//    @Test
//    public void remove() {
//        long bool =  redisTemplate.opsForList().remove("listStr",2,6);
//        System.out.println(bool);
//    }
//
//    //将第一个集合中的元素弹出到第二个集合中,旧集合移除
//    @Test
//    public void rightPopAndLeftPush() {
//        redisTemplate.opsForList().rightPopAndLeftPush("listStr","listLeft");
//
//
//    }
//
////    ====================================================set======================================
//
//    @Test
//    public void setSet() {
//        redisTemplate.opsForSet().add("setList1", 4,"磊",2,8,9);
//    }
////获取
//    @Test
//    public void getSet() {
//        Set<Object> set = redisTemplate.opsForSet().members("setList");
//        for(Object obj:set){
//            System.out.println(obj.toString());
//        }
//    }
//
////    判断是否存在1
//    @Test
//    public void sisMember() {
//       boolean isMember = redisTemplate.opsForSet().isMember("setList",8);
//       System.out.println(isMember);
//    }
//
//
//    @Test
//    public void sGetSetSize() {
//        long setSize = redisTemplate.opsForSet().size("setList");
//        System.out.println(setSize);
//    }
//
////移除
//    @Test
//    public void setRemove() {
//        long setSize = redisUtil.setRemove("set",1);
//        System.out.println(setSize);
//    }
////    求setList1与setList差集
//    @Test
//    public void difference() {
//        Set<?>setDto=redisTemplate.opsForSet().difference("setList1", "setList");
//        System.out.println(setDto.toString());
//    }
////求setList1与setList交集
//    @Test
//    public void intersect() {
//        Set<?>setDto=redisTemplate.opsForSet().intersect("setList1", "setList");
//        System.out.println(setDto.toString());
//    }
//
//    //求setList1与setList1并集
//    // unionAndStore将并集存储到一个key中
//    @Test
//    public void sunion() {
//        Set<?>setDto=redisTemplate.opsForSet().union("setList1", "setList");
//        System.out.println(setDto.toString());
//    }
//
//
//    //求将setList1移除到setList中
//    @Test
//    public void move() {
//    boolean  isMove=redisTemplate.opsForSet().move("setList1","磊","setList");
//        System.out.println(isMove);
//    }
//
////count 存在返回随机数组，不存在返回随机元素
////redisTemplate.opsForSet().distinctRandomMembers();  返回随机不重复元素集合
////redisTemplate.opsForSet().randomMembers 返回随机集合List<Object>
//    @Test
//    public void randomMember() {
//        Object  obj=redisTemplate.opsForSet().randomMember("setList1");
//        System.out.println(obj);
//    }
//
//
//
//
////    ====================================================zset======================================
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//}