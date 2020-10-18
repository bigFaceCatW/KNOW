package com.know.redis;

import com.know.thread.ThreadRunnable;
import com.know.util.ContentUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: FaceCat
 * @Date: 2020/10/18 9:38
 */
@RequestMapping("/redisTest")
@RestController
public class Test {
    @Resource
    private JedisCluster jedisCluster;

    @PostMapping("/multiThreading")
    public void redisTest() {
        String lockedValue = UUID.randomUUID().toString();
        int expirTime=3*60*1000;
        ThreadPoolExecutor pool = new ThreadPoolExecutor(100, 2000, 2000,
                TimeUnit.MICROSECONDS,
                new LinkedBlockingDeque<>());
        try {
//       开始获取锁
        boolean redisFlag=RedisTool.redisAddLock(jedisCluster, ContentUtil.REDIS_LOCK, lockedValue, expirTime);
        if(redisFlag){

            Future future = pool.submit(new ThreadRunnable(jedisCluster,lockedValue));
            pool.shutdown();//关闭线程
        }else {
            boolean redisWaitFlag=RedisTool.redisWaitLock(jedisCluster, ContentUtil.REDIS_LOCK, lockedValue, expirTime,3*60*1000);
        if(redisWaitFlag){
            Future future = pool.submit(new ThreadRunnable(jedisCluster,lockedValue));
            }
        }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            pool.shutdown();
        }
    }


}