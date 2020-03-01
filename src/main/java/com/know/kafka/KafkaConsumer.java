package com.know.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;

/**
 * @Author: Facecat
 * @Date: 2020/2/29 22:35
 */

@Configuration
public class KafkaConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaConsumer.class);
//没有指定所有消息都会接受
//  @KafkaListener(topics={"${spring.kafka.topic}"})
//    public void receive1(@Payload String message, @Headers MessageHeaders headers){
//        LOG.info("接收到消息1："+message);
//    }


   @KafkaListener(id = "consumer1-1",groupId = "group1",
            topicPartitions = {@TopicPartition(topic = "${spring.kafka.topic}", partitions = {"0"})
            })
    public void consumerOne(@Payload String message, @Headers MessageHeaders headers){
        LOG.info("consumer1-1接收到消息："+message);
    }


    @KafkaListener(id = "consumer1-2",groupId = "group1",
            topicPartitions = {@TopicPartition(topic = "${spring.kafka.topic}", partitions = {"1"})
            })
    public void consumerTwo(@Payload String message, @Headers MessageHeaders headers){
        LOG.info("consumer1-2接收到消息："+message);
    }

    @KafkaListener(id = "consumer1-3",groupId = "group1",
            topicPartitions = {@TopicPartition(topic = "${spring.kafka.topic}", partitions = {"1"})
            })
    public void consumerTwo1(@Payload String message, @Headers MessageHeaders headers){
        LOG.info("consumer1-3接收到消息："+message);
    }

    @KafkaListener(id = "consumer2-1", groupId = "group2",
            topicPartitions = {@TopicPartition(topic = "${spring.kafka.topic}", partitions = {"2"})
            })
    public void consumerThree(ConsumerRecord<String, Object> record){
        LOG.info("consumer2-1接收到消息："+record.value());
    }



}
