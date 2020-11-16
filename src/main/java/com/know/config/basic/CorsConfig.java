/*
package com.know.config.basic;

import com.know.util.CommonUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

*/
/**跨域访问
 * @Author: Facecat
 * @Date: 2020/3/4 17:29
 *//*

@Configuration
public class CorsConfig {

        @Value("${corsIps}")
        private String corsIps;
        private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        if(CommonUtil.isValue(corsIps)) {
            String[] ips = corsIps.split(",");
            for(String ip : ips) {
                System.out.println(ip);
                corsConfiguration.addAllowedOrigin(ip);
            }
        }else {
            corsConfiguration.addAllowedOrigin("*");
        }
        //corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        return corsConfiguration;
    }

        @Bean
        public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new
                UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig());
        return new CorsFilter(source);
    }
    }



*/
