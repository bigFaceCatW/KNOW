package com.know.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisCommands;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

/**
 * @Author: FaceCat
 * @Date: 2020/10/18 9:01
 */
public class RedisTool{

    public static final Logger logger = LoggerFactory.getLogger(RedisTool.class);
    private StringRedisTemplate stringRedisTemplate;
    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";
    private static final Long RELEASE_SUCCESS = 1L;
    public RedisTool(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }
//======================================================1.使用jedisCluster方式
        //    加锁 (expirtTime单位毫秒)
        public static boolean redisAddLock( JedisCluster jedisCluster, String lockKey, String lockValue, long expirtTime){
            String result = jedisCluster.set(lockKey, lockValue, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expirtTime);
            if (LOCK_SUCCESS.equals(result+"")) {
                return true;
            }else{
                return false;
            }
        }

        //如果未获得锁,自动尝试多次，直到花费的时间超过tryTimeOut时间((tryTimeOut单位毫秒))
        public static boolean redisWaitLock(JedisCluster jedisCluster,String lockKey, String lockValue,long expirtTime, long tryTimeOut) {        //单位都是毫秒
            long startTime = System.currentTimeMillis() ;
            int s = 0;
            while ((System.currentTimeMillis() - startTime) <= tryTimeOut){

                System.out.println(System.currentTimeMillis() - startTime);
                System.out.println(+s++);
                if( redisAddLock(jedisCluster,lockKey,lockValue,expirtTime) ) {
                    return true;
                }else{
                    try {
                        Thread.sleep(50, new Random().nextInt(100));
                    } catch (InterruptedException e) {
                        logger.error( "分布式redis锁异常");
                    }
                }

            }
            return false;
        }


        //   释放锁
        public static boolean redisDelLock(JedisCluster jedisCluster, String lockKey, String lockedValue) {

            String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
            Object result = jedisCluster.eval(script, Collections.singletonList(lockKey), Collections.singletonList(lockedValue));
            if (RELEASE_SUCCESS.equals(result)) {
                return true;
            }else{
                return false;
            }
        }

//        ==============================================2.使用StringTemplate方式(redis单例)

//    获取锁
    public boolean getLock(String lockedKey,  long expire,String lockedValue) {
        String exeResult = stringRedisTemplate.execute((RedisCallback<String>) connection -> {
            JedisCommands commands = (JedisCommands) connection.getNativeConnection();            /**
             * NX： 表示只有当锁定资源不存在的时候才能 SET 成功。利用 Redis 的原子性，
             *      保证了只有第一个请求的线程才能获得锁，而之后的所有线程在锁定资源被释放之前都不能获得锁。
             *
             * PX： expire 表示锁定的资源的自动过期时间，单位是毫秒。具体过期时间根据实际场景而定
             */
            return commands.set(lockedKey, lockedValue, "NX", "PX", expire);
        });        //是否获取到锁
        boolean result = LOCK_SUCCESS.equals(exeResult);
        return result;
    }

//    多次自动获取锁
    public boolean getManyLock(String lockedKey,String lockedValue, long expire, long tryTimeOut) {        //单位都是毫秒
        long startTime = System.currentTimeMillis() ;
        Random random = new Random();
        while ((System.currentTimeMillis() - startTime) <= tryTimeOut){
            if( getLock(lockedKey,expire,lockedValue) ){
                return true ;
        }try {
            Thread.sleep(50, random.nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        }
        return false ;
    }


    public boolean delLock(String lockedKey,String lockedValue) {
        if (lockedValue == null || lockedValue.length() == 0){
            return  false ;
    }        // 使用Lua脚本删除Redis中匹配value的key，可以避免由于方法执行时间过长而redis锁自动过期失效的时候误删其他线程的锁
        // 删除前要通过value来判断是否为自己的锁
        String script = new StringBuffer()
                .append("if redis.call('get', KEYS[1]) == ARGV[1] then ")
                .append("   return redis.call('del', KEYS[1]) ")
                .append("else ")
                .append("   return 0 ")
                .append("end ")
                .toString();

        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<Long>();
        redisScript.setResultType(Long.class);
        redisScript.setScriptText(script);        //执行脚本
        Long exeResult = stringRedisTemplate.execute(redisScript, Arrays.asList(lockedKey), lockedValue);
        if(RELEASE_SUCCESS==exeResult){
            return true;
        }else {
            return false;
        }

    }




}
