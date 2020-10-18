package com.know.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @Author: Facecat
 * @Date: 2020/2/21 17:33
 */
//分布式锁
public class ZkLock {

    public static void main(String[] args) {
        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder()
                .connectString("10.190.3.172:5888")//集群直接，分割增加
                .sessionTimeoutMs(15000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))//容错重试每睡1秒重试一次，总共3次，递增增加时间
                .build();

        curatorFramework.start();

        final InterProcessMutex lock = new InterProcessMutex(curatorFramework,"/locks");
        for (int i=0;i<10;i++){
           new Thread(()-> {
                System.out.println(Thread.currentThread().getName()+"->尝试竞争锁");
                try {
                    lock.acquire(); //阻塞竞争锁
                    System.out.println(Thread.currentThread().getName()+"->获得竞争锁");
                }catch(Exception e){
                    e.printStackTrace();
                }
               try {
                   Thread.sleep(15000);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }finally {
                   try {
                       lock.release();//释放锁
                   } catch (Exception e) {
                       e.printStackTrace();
                   }
               }
           },"Thread-"+i).start();



        }

    }
}
