
package com.know.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Arrays;
import java.util.Properties;


/**
 * @Author: Facecat
 * @Date: 2020/3/3 15:09
 */



public class KafkaClientConsumer extends Thread {

    private final String topic;



    private  final KafkaConsumer kafkaConsumer;

    public  KafkaClientConsumer (String topic){
        this.topic=topic;
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "10.190.3.172:5889,10.190.5.208:5889,10.190.5.209:5889");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "KafkaApacheConsumer");  //分组
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");    //自动提交
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");//自动提交间隔时间（批量确认）
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");//一个新的消费者去消费所有未被消息的
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        kafkaConsumer = new KafkaConsumer(properties);
//        kafkaConsumer.subscribe(Collections.singletonList(topic));
        TopicPartition topicPartition = new TopicPartition(topic,0); //接收指定的分区消息
        kafkaConsumer.assign(Arrays.asList(topicPartition));

    }

    @Override
    public void  run(){
        while (true){
//            ConsumerRecords<Integer,String> consumerRecord = kafkaConsumer.poll(Duration.ofMillis(1000));
            ConsumerRecords<Integer,String> consumerRecord = kafkaConsumer.poll(1000);
            for(ConsumerRecord record:consumerRecord){
                System.out.println(record.partition()+"->message 接收:"+record.value());
            }

        }
    }


    public static void main(String[] args) {
        new KafkaClientConsumer("javaShow").start();
    }


}

