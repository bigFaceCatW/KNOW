//package com.know.kafka;
//
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.kafka.common.serialization.StringDeserializer;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.annotation.EnableKafka;
//import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
//import org.springframework.kafka.config.KafkaListenerContainerFactory;
//import org.springframework.kafka.core.ConsumerFactory;
//import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
//import org.springframework.kafka.listener.AbstractMessageListenerContainer;
//import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * Created by LIBIN on 2019/1/11 15:56
// * Description:
// */
//@Configuration
//@EnableKafka
//public class KafkaConsumerConfig {
//    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerConfig.class);
//    @Value("${kafka.consumer.servers}")
//    private String servers;
//
//    @Value("${kafka.consumer.enable.auto.commit}")
//    private boolean enableAutoCommit;
//
//    @Value("${kafka.consumer.session.timeout}")
//    private String sessionTimeout;
//
//    @Value("${kafka.consumer.auto.commit.interval}")
//    private String autoCommitInterval;
//
//    @Value("${kafka.consumer.group.id}")
//    private String groupId;
//
//    @Value("${kafka.consumer.auto.offset.reset}")
//    private String autoOffsetReset;
//
//    @Value("${kafka.consumer.concurrency}")
//    private int concurrency;
//
//    @Value("${kafka.consumer.userName}")
//    private String userName;
//
//    @Value("${kafka.consumer.password}")
//    private String password;
//
//
//
//    @Bean
//    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory() {
//        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactory());
//        factory.setConcurrency(concurrency);
//        factory.setBatchListener(true);
//        factory.getContainerProperties().setPollTimeout(1500);
//        // 手动提交偏移量
//        factory.getContainerProperties().setAckMode(AbstractMessageListenerContainer.AckMode.MANUAL_IMMEDIATE);
//        return factory;
//    }
//
//    public ConsumerFactory<String, String> consumerFactory() {
//        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
//    }
//
//    public Map<String, Object> consumerConfigs() {
//        Map<String, Object> propsMap = new HashMap<>();
//        propsMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
//        propsMap.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, enableAutoCommit);
//        propsMap.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, autoCommitInterval);
//        propsMap.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, sessionTimeout);
//        propsMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        propsMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        propsMap.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
//        propsMap.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
//        propsMap.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
//        propsMap.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
//        if (!StringUtil.isBlank(userName)) {
//            //用户名密码方式 begin
//            try {
//                logger.info("Kafka认证配置开始。。。。");
//                propsMap.put("security.protocol", "SASL_PLAINTEXT");
//                propsMap.put("sasl.mechanism", "SCRAM-SHA-256");//PLAIN
//                propsMap.put("sasl.jaas.config",
//                        "org.apache.kafka.common.security.scram.ScramLoginModule required username=\"" + userName + "\" password=\"" + password + "\";");
//                //用Listener户名密码方式 end
//                logger.info("Kafka认证配置结束。。。。");
//            } catch (Exception e) {
//                logger.info(e.getMessage(), e);
//            }
//        }
//        return propsMap;
//    }
//
//    @Bean
//    public Listener listener() {
//        return new Listener();
//    }
//}
