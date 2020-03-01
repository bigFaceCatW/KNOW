package com.know.kafak;

import com.know.config.dto.PageObj;
import com.know.kafka.KafkaConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @Author: Facecat
 * @Date: 2020/3/1 10:42
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class KafkaTest {

    @Resource
    private KafkaConfig kafkaConfig;
    @Resource
    private PageObj pageObj;

    @Test
    public  void producer() throws IOException {
        String message = "Hello World---";

        kafkaConfig.send(message);
        System.in.read();
    }
}
