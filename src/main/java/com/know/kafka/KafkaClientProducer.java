
package com.know.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;

import java.util.Properties;

/**
 * @Author: Facecat
 * @Date: 2020/3/3 14:37
 */


public class KafkaClientProducer extends Thread {

    private  final KafkaProducer<Integer,String> producer;

    private final String topic;

    @Value("${spring.kafka.bootstrap-servers}")
    private String servers;

    public  KafkaClientProducer(String topic){
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"10.190.3.172:5889,10.190.5.208:5889,10.190.5.209:5889");
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, "producer-group"); //分组
        properties.put(ProducerConfig.ACKS_CONFIG, "-1");//0：不需要确认|1：获得leader确认即可返回|-1：需要所有节点确认
//        properties.put(ProducerConfig.BATCH_SIZE_CONFIG,123); //设置达到多少就批量发送
//        properties.put(ProducerConfig.LINGER_MS_CONFIG,234324); //间隔发送次数
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName()); //配置key的序列化
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, "com.know.kafka.Partition"); //自定义分区策略
        producer = new KafkaProducer<Integer,String>(properties);
        //batch.size:同一个分区会缓存，再批量发送
        this.topic=topic;
    }


    @Override
    public void run() {
        int num =0;
        while (num<50){
            String message = "message"+num;
            System.out.println("producer开始发送：---"+message);
            producer.send(new ProducerRecord<Integer, String>(topic,message)); //后面加.get()同步阻塞可以获取发送信息
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            num++;
        }
    }

    public static void main(String[] args) {
        new KafkaClientProducer("topicChannle").start();
    }

}

