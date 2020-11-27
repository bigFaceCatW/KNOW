package com.know.kafka;

import com.know.util.CommonUtil;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Kafka 初始化kafka，使用直接使用KafkaTemplate发送与接收-合力
 *
 */
//@Configuration
//@EnableKafka
public class KafkaProducerConfig {
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerConfig.class);
    @Value("${kafka.producer.servers}")
    private String servers;
    @Value("${kafka.producer.retries}")
    private int retries;
    @Value("${kafka.producer.batch.size}")
    private int batchSize;
    @Value("${kafka.producer.linger}")
    private int linger;
    @Value("${kafka.producer.buffer.memory}")
    private int bufferMemory;
    @Value("${kafka.producer.userName}")
    private String userName;
    @Value("${kafka.producer.password}")
    private String password;



    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        props.put(ProducerConfig.RETRIES_CONFIG, 0);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, batchSize);
        props.put(ProducerConfig.LINGER_MS_CONFIG, linger);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, batchSize);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        if (CommonUtil.isValue(userName)) {
            //用户名密码方式 begin
            try {
                logger.info("Kafka认证配置开始。。。。");
                props.put("security.protocol", "SASL_PLAINTEXT");
                props.put("sasl.mechanism", "SCRAM-SHA-256");//PLAIN
                props.put("sasl.jaas.config",
                        "org.apache.kafka.common.security.scram.ScramLoginModule required username=\"" + userName + "\" password=\"" + password + "\";");
                //用户名密码方式 end
                logger.info("Kafka认证配置结束。。。。");
            } catch (Exception e) {
                logger.info("Kafka认证异常"+e.getMessage(), e);
            }
        }
        return props;
    }

    public ProducerFactory<String, String> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<String, String>(producerFactory());
    }
}
