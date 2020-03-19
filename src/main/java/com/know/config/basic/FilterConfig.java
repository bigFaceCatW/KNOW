//package com.know.config.basic;
//
//import org.springframework.context.annotation.Configuration;
//
//import javax.servlet.*;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
///**
// * @Author: Facecat
// * @Date: 2020/3/17 9:01
// */
//@Configuration
//public class FilterConfig  implements Filter {
//
//        @Override
//        public void init(javax.servlet.FilterConfig filterConfig) throws ServletException {
//            System.out.println("===============CorsFilterConfig初始化=================");
//        }
//
//        @Override
//        public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//            System.out.println("=================设置消息头======================");
//            HttpServletResponse res = (HttpServletResponse) servletResponse;
//            res.setHeader("Access-Control-Allow-Origin", "*");
//            res.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
//            res.setHeader("Access-Control-Max-Age", "1728000");
//            res.setHeader("Access-Control-Allow-Headers", "Authentication, Authorization, content-type, Accept, x-requested-with, Cache-Control");
//            filterChain.doFilter(servletRequest, res);
//        }
//
//        @Override
//        public void destroy() {
//            System.out.println("死亡");
//
//        }
//
//
//    }
