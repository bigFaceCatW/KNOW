package com.know;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @Author: Facecat
 * @Date: 2020/1/9 9:23
 */
@SpringBootApplication
@EnableConfigurationProperties
public class KnowledgeNoLimitApplication {

    public static void main(String[] args) {
        SpringApplication.run(KnowledgeNoLimitApplication.class, args);


    }



}
