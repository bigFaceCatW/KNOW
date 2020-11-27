//package com.know.kafka;
//
//
//import com.know.util.StringUtil;
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.kafka.support.Acknowledgment;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
///**
// * Created by LIBIN on 2019/1/11 17:05
// * Description:
// */
//@Component("listener")
//public class Listener {
//
//    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
//
//
//
//    @KafkaListener(topics = {"${kafka.consumer.topicName}"})
//    public void listenerDts(List<ConsumerRecord<String, String>> records, Acknowledgment ack) {
//        long startTime = System.currentTimeMillis();
//        logger.info("监听到消息数量：" + records.size() + "，时间：" + startTime);
//        try {
//            System.out.println("监听: " + records.toString());
//            for (ConsumerRecord<String, String> record : records) {
//                logger.info("kafka的key: " + record.key());
//                logger.info("kafka的value: " + record.value().toString());
//                logger.info("kafka的offset: " + record.offset());
//                String key = (String) record.key();
//                System.out.println("key: " + key);
//                if(!StringUtil.isBlank(key)){
////                    业务逻辑
//                }
//            }
//        } catch (Exception e) {
//            logger.error("消费处理异常:"+e.getMessage(), e);
//        }finally {
//            ack.acknowledge();
//        }
//    }
//
//
//}
