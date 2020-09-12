
package com.know.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @Author: Facecat
 * @Date: 2020/2/29 22:35
 */

@Component
@Slf4j
public class KafkaConsumer {
    @KafkaListener(topics = {"api"})
    public void consumerThree( ConsumerRecord<Integer, String> record) {
        Optional msg = Optional.ofNullable(record.value());
        if (msg.isPresent()) {
            log.info("consumer2-1接收到消息：" + record.value());
        }
    }
//    @Value("${spring.kafka.topic}")
//    private String topic;
//没有指定所有消息都会接受
//  @KafkaListener(topics={"topicChannle"})
//    public void receive1(@Payload String message, @Headers MessageHeaders headers){
//        log.info("接收到消息1："+message);
//    }
//

//   @KafkaListener(id = "consumer1-1",groupId = "group2",
//            topicPartitions = {@TopicPartition(topic = "javaKafka", partitions = {"1"})
//            })
//   public void consumerTwo(ConsumerRecord record) {
//       Optional msg = Optional.ofNullable(record.value());
//       if (msg.isPresent()) {
//           log.info("consumer1-1接收到消息：" + record.value());
//       }
//   }

    @KafkaListener(id = "consumer1-2",groupId = "group2",
            topicPartitions = {@TopicPartition(topic = "java1", partitions = {"1"})
            })
    public void consumerTwo1(ConsumerRecord record) {
        Optional msg = Optional.ofNullable(record.value());
        if (msg.isPresent()) {
            log.info("consumer1-2接收到消息：" + record.value());
        }
    }









}

