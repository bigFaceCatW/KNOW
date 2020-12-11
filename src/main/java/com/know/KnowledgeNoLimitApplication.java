package com.know;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Author: Facecat
 * @Date: 2020/1/9 9:23
 */
//@ComponentScan({"com.know"})
//@EnableAutoConfiguration
@SpringBootApplication
@EnableTransactionManagement(proxyTargetClass=true)  //开启事务,这是true为了service能注入到controller中
@ServletComponentScan    //servlet配置
@MapperScan("com.know.mp.mapper")
public class KnowledgeNoLimitApplication {
    public static void main(String[] args) {
        SpringApplication.run(KnowledgeNoLimitApplication.class, args);


    }

}
