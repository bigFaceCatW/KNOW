package com.know.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @Author: Facecat
 * @Date: 2020/2/20 22:50
 */
public class zkWatcher {
    /**
     * PathChildCache --针对子节点的创建，删除，更新触发事件
     * NodeCache  针对当前节点的变化触发事件
     * TreeCache  综合事件
     */

//节点的创建，修改，删除进行监听
    private static void addListenWithNode(CuratorFramework curatorFramework) throws Exception {
        NodeCache nodeCache = new NodeCache(curatorFramework,"/watch",false);//针对某个节点监听，false是否缓存
        NodeCacheListener nodeCacheListener =() -> {
                  System.out.println("receive Node Changed");
                  System.out.println(nodeCache.getCurrentData().getPath()+"/"+new String(nodeCache.getCurrentData().getData()));

        };
        nodeCache.getListenable().addListener(nodeCacheListener);
        nodeCache.start();

        System.in.read(); //让main线程一直启动
    }

    //对子节点变化监听,数据变化后节点变化都可以监听
    private static void addListenWithChild(CuratorFramework curatorFramework) throws Exception {
        PathChildrenCache nodeCache = new PathChildrenCache(curatorFramework,"/watch",true);//针对某个节点监听
        PathChildrenCacheListener nodeListener = (curatorFrame, pathChildrenCacheEvent) -> {
            System.out.println(pathChildrenCacheEvent.getType() //获取事件类型
                    +"->"+new String(pathChildrenCacheEvent.getData().getData()));//获取触发事件变化的数据
        };
        nodeCache.getListenable().addListener(nodeListener);
        nodeCache.start(PathChildrenCache.StartMode.NORMAL); //三种模式
        System.in.read(); //让main线程一直启动
    }

    public static void main(String[] args) throws Exception {
        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder()
                .connectString("10.190.3.172:5888")//集群直接，分割增加
                .sessionTimeoutMs(16000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))//容错重试每睡1秒重试一次，总共3次，递增增加时间
                .build();

        curatorFramework.start();
//        addListenWithNode(curatorFramework);
        addListenWithChild(curatorFramework);


    }
}
