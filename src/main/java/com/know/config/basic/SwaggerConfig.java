//
//package com.know.config.basic;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
///**http://localhost:8080/swagger-ui.html 访问
// * @Author: Facecat
// * @Date: 2020/3/4 17:11
// */
//
//
//
//@Configuration
//@EnableSwagger2
//public class SwaggerConfig  {
//    @Value("${spring.application.name}") //application.properties 中配置
//    private String appName;
//    @Value("${spring.application.version}") //application.properties 中配置
//    private String appVersion;
//
///**
//     * 设置监控路径,默认监控com.usi
//     */
//
//    @Bean
//    public Docket api() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .produces(Sets.newHashSet("application/json"))
//                .consumes(Sets.newHashSet("application/json"))
//                .apiInfo(apiInfo())
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.usi"))
//                .paths(PathSelectors.any())
//                .build();
//    }
//
///**
//     * ApiInfo
//     */
//
//    private ApiInfo apiInfo()
//    {
//        return new ApiInfoBuilder()
//        .title("应用检查平台接口文档")
//        .description("应用检测平台接口文档")
//        .termsOfServiceUrl("http://localhost:8000/swagger-ui.html")
//        .contact("科大国创-网管产品部")
//        .version("1.0")
//        .build();

//    }
//
//
//
//}
//
