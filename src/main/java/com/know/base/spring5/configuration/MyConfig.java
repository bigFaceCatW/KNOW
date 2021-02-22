package com.know.base.spring5.configuration;

import com.know.mp.dto.SysUserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: FaceCat
 * @Date: 2020/11/24 21:18
 */
@Configuration
//@ComponentScan(
//        value = "com.know.spring5.service"
//)
//默认扫描：@Controller、@Service、@Repository、@Component
public class MyConfig {
    private static final Logger logger = LoggerFactory.getLogger(MyConfig.class);
//prototype：多例
//singleton：单例（默认）
//    延迟加载只针对单例起作用，默认创建对象的时候才加载
//    @Lazy
//    @Scope
    @Bean
    public SysUserDTO configSysUserDto(){
        logger.error("测试error");
        SysUserDTO dto = new SysUserDTO();
        dto.setUserName("admin");
        return  dto;
    }
    public void methodConfig(){
        System.out.println("config方法");
    }


}
