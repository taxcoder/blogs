package com.tanx.blogs;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * @author tanxiang
 */
@EnableAsync
@EnableOpenApi
@SpringBootApplication
@ComponentScan("com.tanx")
@MapperScan({"com.tanx.blogs.dao"})
@ServletComponentScan(basePackages = {"com.tanx.blogs.filter"})
public class BlogsApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(BlogsApplication.class, args);
    }
}
