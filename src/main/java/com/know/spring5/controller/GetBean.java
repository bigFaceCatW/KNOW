package com.know.spring5.controller;

import com.know.info.dto.SysUserDto;
import com.know.spring5.configuration.MyConfig;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @Author: FaceCat
 * @Date: 2020/11/24 21:23
 */
@Controller
public class GetBean {
   private static final Logger logger = LoggerFactory.getLogger(GetBean.class);
    @Resource
    private SysUserDto configSysUserDto;



    @Test
    public  void text (){
//        从上下文中拿到初始化对象
        ApplicationContext app = new AnnotationConfigApplicationContext(MyConfig.class);
        Object bean =app.getBean("configSysUserDto");
        String[] beanNames=app.getBeanDefinitionNames();
        System.out.println(Arrays.toString(beanNames));

        Environment environment = app.getEnvironment();
        System.out.println("从环境变量取值>>>"+environment.getProperty("es.host"));


    }

}
