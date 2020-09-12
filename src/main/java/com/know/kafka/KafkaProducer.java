
package com.know.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.concurrent.ListenableFuture;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @Author: Facecat
 * @Date: 2020/2/29 19:39
 */

@Configuration
@Slf4j
@EnableScheduling
public class KafkaProducer {

    @Resource
    private KafkaTemplate<Integer, String> kafkaTemplate;

    @Value("${spring.kafka.topic}")
    private String topic;

    //发送消息方法
    @Scheduled(cron = "0/5 * * * * ?")
    public void send() {
             String message = UUID.randomUUID().toString();
        ListenableFuture<SendResult<Integer, String>> future= kafkaTemplate.send("api",1,message);
        future.addCallback(success -> log.info("发送消息成功:" + message),
                fail -> log.error("KafkaMessageProducer 发送消息失败！"));

//            );
//            ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send("test",message);
//            future.addCallback(success -> log.info("发送消息成功:"+message),
//                    fail -> log.error("KafkaMessageProducer 发送消息失败！")
//            );

    }



}

