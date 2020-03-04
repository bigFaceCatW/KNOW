
package com.know.kafka;


/**
 * @Author: Facecat
 * @Date: 2020/3/3 22:24
 */

import org.apache.commons.lang.math.RandomUtils;
import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;

import java.util.List;
import java.util.Map;


public class Partition implements Partitioner {
    @Override
    public int partition(String topic, Object key, byte[] bytes, Object value, byte[] bytes1, Cluster cluster) {
        List<PartitionInfo> partitionInfos = cluster.partitionsForTopic(topic);//获取分区列表
        int partitionNum=0;
        if (key==null){
            partitionNum = RandomUtils.nextInt(partitionInfos.size());//随机分区
        }else {
            partitionNum = Math.abs((key.hashCode()) % partitionInfos.size());//根据key哈希算法分区
        }
    System.out.println("key ->"+key+"->value->"+value+"->"+partitionNum);
        return partitionNum;//分区的值
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}


