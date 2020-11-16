package com.tanx.blogs.config;

import java.util.Map;
import java.util.HashMap;
import javax.sql.DataSource;
import java.util.Collections;
import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.annotation.Bean;
import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.druid.support.http.StatViewServlet;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author tan
 */
@Configuration
public class DruidConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druidDataSource() {
        return (DataSource)new DruidDataSource();
    }

    @Bean
    public ServletRegistrationBean<?> statViewServlet() {
        ServletRegistrationBean<?> bean = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");
        Map<String, String> map = new HashMap<>();
        map.put("loginUsername", "admin");
        map.put("loginPassword", "123456");

        map.put("allow", "");

        bean.setInitParameters(map);
        return bean;
    }


    @Bean
    public FilterRegistrationBean<?> druidWebStateFilter() {
        FilterRegistrationBean<?> bean = new FilterRegistrationBean<>(new WebStatFilter(), statViewServlet());
        Map<String, String> map = new HashMap<>();
        map.put("exclusions", "*.js,*.css,/druid/*");
        bean.setInitParameters(map);
        bean.setUrlPatterns(Collections.singletonList("/*"));
        return bean;
    }
}