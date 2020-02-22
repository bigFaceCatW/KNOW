package com.know;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: Facecat
 * @Date: 2020/1/9 9:23
 */
@SpringBootApplication
public class KnowledgeNoLimitApplication {

    public static void main(String[] args) {
        SpringApplication.run(KnowledgeNoLimitApplication.class, args);
    }

    /*
    * new SpringApplicationBuilder(SpringCloudClientApplication.class)
    * .web(WebApplicationType.SERVLET)
    * .run(args);
    *
    *
    * */

}
