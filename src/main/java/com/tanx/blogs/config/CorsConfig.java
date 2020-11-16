package com.tanx.blogs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author tanxiang
 * @version 1.0
 * @date 2020/10/8 下午1:25
 */

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 设置所有的域名
        corsConfiguration.addAllowedOrigin("https://www.tanxiangblog.com/");
        corsConfiguration.addAllowedOrigin("http://localhost:8080");
        // 设置所有的请求头
        corsConfiguration.addAllowedHeader("*");
        // 设置所有的方法
        corsConfiguration.addAllowedMethod("*");
        // 设置cookie可以传递
        corsConfiguration.setAllowCredentials(true);
        return corsConfiguration;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig());
        return new CorsFilter(source);
    }
}