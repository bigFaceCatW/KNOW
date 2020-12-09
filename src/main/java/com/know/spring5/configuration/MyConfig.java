package com.know.spring5.configuration;

import com.know.mybatis.dto.SysUserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.*;

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
    public SysUserDto configSysUserDto(){
        logger.error("测试error");
        SysUserDto dto = new SysUserDto();
        dto.setUserName("admin");
        return  dto;
    }
    public void methodConfig(){
        System.out.println("config方法");
    }


}
