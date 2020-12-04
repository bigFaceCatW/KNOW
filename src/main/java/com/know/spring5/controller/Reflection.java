package com.know.spring5.controller;


import com.know.util.GlobalApplicationContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;

import java.lang.reflect.Method;

/**
 * @Author: FaceCat
 * @Date: 2020/12/3 15:40
 */

@Controller
@Configuration
public class Reflection {
    private static final Logger logger = LoggerFactory.getLogger(Reflection.class);

    private static final String GTR = "com.know.spring5.controller.GetBean";
    @Autowired
    private ApplicationContext applicationContext;



    @Bean
    public void text() {
        try {
            Class<?> getBean = Class.forName(GTR);
            GetBean dto = (GetBean) GlobalApplicationContextHolder.getBean(getBean);
            GetBean dto1 = (GetBean) applicationContext.getBean(getBean);
            GetBean dto2 = (GetBean)getBean.newInstance();
            Method method = getBean.getMethod("text");//通过class对象获取方法值
            Object methodReturn =method.invoke(dto); //通过方法对象，调用方法
//   ---------------------------------以类加载器方式获取class对象
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            Class<?> getBean1 =loader.loadClass(GTR);
            GetBean loaderDto = (GetBean) applicationContext.getBean(getBean);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

    }
}
