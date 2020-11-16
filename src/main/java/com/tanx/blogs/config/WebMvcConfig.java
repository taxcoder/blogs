package com.tanx.blogs.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author tan
 */
@Configuration
@ComponentScan({"com.tanx.blogs"})
public class WebMvcConfig implements WebMvcConfigurer {

}
