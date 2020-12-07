package com.know.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

/**
 * @Author: Facecat
 * @Date: 2020/2/3 21:28
 */

public class ZkCurator {




    /*@Bean
    public  CuratorFramework curatorConfig(){
        CuratorFramework zkClient  = CuratorFrameworkFactory.builder()
                .connectString(host)//集群直接，分割增加
                .sessionTimeoutMs(5000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))//容错重试每睡1秒重试一次，总共3次，递增增加时间
                .build();

        zkClient.start();
        return zkClient;
    }*/
    //45分钟crud 49分钟ACL权限  64分钟watcher  83分钟API使用


    private static void createDate(CuratorFramework curatorFramework) throws Exception {
        curatorFramework.create().creatingParentsIfNeeded()
                .withMode(CreateMode.PERSISTENT)
                .forPath("/curator/data","curator".getBytes());
    }

    private  static  void updateDate(CuratorFramework curatorFramework) throws Exception {
        curatorFramework.setData().forPath("/curator/data", "update".getBytes());
    }

    private  static  void deleteDate(CuratorFramework curatorFramework) throws Exception {
        Stat stat = new Stat();
        String value = new String(curatorFramework.getData().storingStatIn(stat).forPath("/curator/data"));
        //获取版本号,同时返回节点信息
        curatorFramework.delete().withVersion(stat.getVersion()).forPath("/curator/data");
    }

    private  static  void queryDate(CuratorFramework curatorFramework) throws Exception {
        Stat stat = new Stat();
        String value = new String(curatorFramework.getData().storingStatIn(stat).forPath("/curator/data"));
        System.out.println(value); //获取对应的值
    }


    public static void main(String[] args) throws Exception {
        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder()
                .connectString("127.0.0.1:2181")//集群直接，分割增加
                .sessionTimeoutMs(5000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))//容错重试每睡1秒重试一次，总共3次，递增增加时间
                .build();

        curatorFramework.start();

        updateDate(curatorFramework);
        //createDate(curatorFramework);

        /*List<ACL> list = new ArrayList<>();
        ACL acl = new ACL(ZooDefs.Perms.READ,new Id("digest","admin:password"));
        curatorFramework.create().withMode(CreateMode.EPHEMERAL).withACL().forPath("/auth");*/



        //watcher监听事件


    }
}