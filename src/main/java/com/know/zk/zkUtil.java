package com.know.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;

import javax.annotation.Resource;

/**
 * @Author: Facecat
 * @Date: 2020/2/4 10:28
 */
public class zkUtil {

   @Resource
    private CuratorFramework curatorFramework;


   public  void  createData(CuratorFramework curatorFramework) throws Exception {

        curatorFramework.create().creatingParentContainersIfNeeded()//实现递归创建节点
                .withMode(CreateMode.PERSISTENT).forPath("/data/curator1", "test".getBytes());

    }

    public static void main(String[] args) {
        zkUtil zk = new zkUtil();


    }
}
