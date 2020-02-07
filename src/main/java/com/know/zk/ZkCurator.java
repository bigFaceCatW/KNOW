package com.know.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Facecat
 * @Date: 2020/2/3 21:28
 */
@Configuration
public class ZkCurator {

    @Value("${zk.host}")
    private String host;


    @Bean
    public  CuratorFramework curatorConfig(){
        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder()
                .connectString(host)//集群直接，分割增加
                .sessionTimeoutMs(5000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))//容错重试每睡1秒重试一次，总共3次，递增增加时间
                .build();

        curatorFramework.start();
        return curatorFramework;
    }
    //45分钟crud 49分钟ACL权限  64分钟watcher  83分钟API使用

    private static void addListenWithNode(CuratorFramework curatorFramework) throws Exception {
        NodeCache nodeCache = new NodeCache(curatorFramework,"/watch",false);
        NodeCacheListener nodeCacheListener =() -> {

        };
          nodeCache.getListenable().addListener(nodeCacheListener);
          nodeCache.start();
    }


    public static void main(String[] args) throws Exception {
        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder()
                .connectString("10.190.3.172:5888")//集群直接，分割增加
                .sessionTimeoutMs(5000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))//容错重试每睡1秒重试一次，总共3次，递增增加时间
                .build();

        curatorFramework.start();

        //ZkCurator.createData(curatorFramework);



        //watcher监听事件


    }
}