package com.know.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.concurrent.ListenableFuture;

import javax.annotation.Resource;

/**
 * @Author: Facecat
 * @Date: 2020/2/29 19:39
 */

@Configuration
@EnableScheduling
public class KafkaConfig {
    private static final Logger LOG = LoggerFactory.getLogger(KafkaConfig.class);

    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${spring.kafka.topic}")
    private String topic;

    //发送消息方法

    public void send(String message) {
        for (int i=0;i<3;i++){
            ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic,i,"key",message+i);
            future.addCallback(success -> LOG.info("发送消息成功:"+message),
                    fail -> LOG.error("KafkaMessageProducer 发送消息失败！")
            );
        }
    }



}
