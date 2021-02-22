package com.know.thread;

import com.know.redis.RedisTool;
import com.know.util.ContentUtil;
import redis.clients.jedis.JedisCluster;

/**
 * @Author: Facecat
 * @Date: 2020/3/16 15:49
 */
public class ThreadRunnable implements Runnable {

    private JedisCluster jedisCluster;
    private String lockedValue;

    public  ThreadRunnable(JedisCluster jedisCluster, String lockedValue){
        this.jedisCluster=jedisCluster;
        this.lockedValue=lockedValue;
    }

    @Override
    public void run() {
        int s =0;
        for(int i=0; i<20; i++){
            System.out.println(Thread.currentThread().getName()+":"+s);
            s++;
//            try {
//                Thread.sleep(10);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }

        System.out.println(Thread.currentThread().getName()+"总和>>>"+s);
        RedisTool.redisDelLock(jedisCluster,ContentUtil.REDIS_LOCK,lockedValue);
    }

//    public static void main(String[] args) {
//        ThreadRunnable thread = new ThreadRunnable();
//        ThreadPoolExecutor pool = new ThreadPoolExecutor(100,2000,2000,
//                TimeUnit.MICROSECONDS,
//                new LinkedBlockingDeque<>());
//        for(int i=0;i<5;i++){
//            Future future =  pool.submit(thread);
//            try{
//                Object object = future.get();
//                System.out.println("futrue返回>>>"+ JSON.toJSONString(object));
//            }catch(Exception e){
//                e.printStackTrace();
//            }
//        }
//        pool.shutdown();//关闭线程
//
//    }

}