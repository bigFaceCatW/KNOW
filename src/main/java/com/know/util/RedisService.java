//package usi.dbdp.app.util;
//
// 
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import java.util.concurrent.TimeUnit;
//import java.util.regex.Pattern;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.connection.DataType;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Service;
//import org.springframework.util.CollectionUtils;
//
//@Service
//public class RedisService {
//
//	@Autowired
//	private RedisTemplate<String, Object> redisTemplate;
//	
//	public final RedisTemplate<String, Object> getRedisTemplate() {
//		return redisTemplate;
//	}
//	
//	
//	public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
//		this.redisTemplate = redisTemplate;
//	}
//
//
//	/**
//	 * 获取单个key的值
//	 * @param key
//	 * @return
//	 */
//	public Object get(String key) {
//        return redisTemplate.boundValueOps(key).get();
//    }
//	
//	public void set(String key, Object value) {
//        redisTemplate.boundValueOps(key).set(value);
//    }
//	
//	public void set(String key, Object value, Long expireTime) {
//        redisTemplate.boundValueOps(key).set(value, expireTime, TimeUnit.SECONDS);
//    }
//	
//	public boolean setExpireTime(String key, Long expireTime) {
//        return redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
//    }
//	
//	/**
//	 * 获取所有键的key
//	 * @return
//	 */
//	public Set<String> getAllKeys() {
//        return redisTemplate.keys("*");
//    }
//	
//	/**
//	 * 获取所有的普通key-value
//	 * @return
//	 */
//    public Map<String, Object> getAllString() {
//        Set<String> stringSet = getAllKeys();
//        Map<String, Object> map = new HashMap<String, Object>();
//        Iterator<String> iterator = stringSet.iterator();
//        while (iterator.hasNext()) {
//            String k = iterator.next();
//            if (redisTemplate.type(k) == DataType.STRING) {
//                map.put(k, get(k));
//            }
//        }
//        return map;
//    }
//	
//    /**
//     * 获取list集合中元素的个数
//     * @param key
//     * @return
//     */
//    public long getListSize(String key) {
//        return redisTemplate.boundListOps(key).size();
//    }
//    
//    /**
//     * 输出完整的list
//     * @param key
//     * @return
//     */
//    public List<Object> getList(String key) {
//        return redisTemplate.boundListOps(key).range(0, getListSize(key));
//    }
//    public long addList(String key, Object obj) {
//        return redisTemplate.boundListOps(key).rightPush(obj);
//    }
//    public void addList(String key, List<Object> objectList) {
//        for (Object obj : objectList) {
//            addList(key, obj);
//        }
//    }
//    public long addList(String key, Object... obj) {
//        return redisTemplate.boundListOps(key).rightPushAll(obj);
//    }
//    
//    /**
//     * 获取所有的List -key-value
//     * @return
//     */
//    public Map<String, List<Object>> getAllList() {
//        Set<String> stringSet = getAllKeys();
//        Map<String, List<Object>> map = new HashMap<String, List<Object>>();
//        Iterator<String> iterator = stringSet.iterator();
//        while (iterator.hasNext()) {
//            String k = iterator.next();
//            if (redisTemplate.type(k) == DataType.LIST) {
//                map.put(k, getList(k));
//            }
//        }
//        return map;
//    }
//    
//    public Map<Object, Object> getMap(String key) {
//        return  redisTemplate.boundHashOps(key).entries();
//    }
//    
//    public void addMap(String key, Map<String, Object> map) {
//        redisTemplate.boundHashOps(key).putAll(map);
//    }
//    
//    public void addMap(String key, String field, Object value) {
//        redisTemplate.boundHashOps(key).put(field, value);
//    }
//    
//    public Map<String, Map<Object, Object>> getAllMap() {
//        Set<String> stringSet = getAllKeys();
//        Map<String, Map<Object, Object>> map = new HashMap<String, Map<Object, Object>>();
//        Iterator<String> iterator = stringSet.iterator();
//        while (iterator.hasNext()) {
//            String k = iterator.next();
//            if (redisTemplate.type(k) == DataType.HASH) {
//                map.put(k, getMap(k));
//            }
//        }
//        return map;
//    }
//    
//    /**
//     * 删除缓存
//     * 根据key精确匹配删除
//     * @param key
//     */
//    public void remove(String key) {
//        if (exists(key)) {
//            redisTemplate.delete(key);
//        }
//    }
//    public void remove(String... key) {
//        if (key != null && key.length > 0) {
//            if (key.length == 1) {
//                remove(key[0]);
//            } else {
//                redisTemplate.delete(CollectionUtils.arrayToList(key));
//            }
//        }
//    }
//    
//	
//    /**
//     * 判断key是否存在
//     * @param key
//     * @return 存在true,不存在 false
//     */
//    public boolean exists(String key) {
//        return redisTemplate.hasKey(key);
//    }
//    
//    /**
//     * 获取所有满足正则表达式的key的value集合
//     * @param regKey
//     * @return
//     */
//    public List<Object> getByRegular(String regKey) {
//        Set<String> stringSet = getAllKeys();
//        List<Object> objectList = new ArrayList<Object>();
//        for (String s : stringSet) {
//            if (Pattern.compile(regKey).matcher(s).matches() && redisTemplate.type(s) == DataType.STRING) {
//                objectList.add(get(s));
//            }
//        }
//        return objectList;
//    }
//    
//    /**
//     * 处理事务时锁定key
//     * @param key
//     */
//    public void watch(String key) {
//        redisTemplate.watch(key);
//    }
// 
//}
