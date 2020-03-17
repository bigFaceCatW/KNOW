package com.know;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Author: Facecat
 * @Date: 2020/1/9 9:23
 */
@SpringBootApplication
@EnableTransactionManagement  //开启事务
@ServletComponentScan    //servlet配置
public class KnowledgeNoLimitApplication {
    public static void main(String[] args) {
        SpringApplication.run(KnowledgeNoLimitApplication.class, args);


    }



}
