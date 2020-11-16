package com.tanx.blogs.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @version 1.0
 * @Author tanxiang
 * @Date 2020/8/29 下午12:46
 */
public class ReaderPropertiesOrYamlUtil {

    private static final Properties PROPERTIES = new Properties();
    private static final Yaml YAML = new Yaml();

    public static String properties(String res,String data) throws IOException {
        PROPERTIES.load(new ClassPathResource(res).getInputStream());
        return PROPERTIES.getProperty(data);
    }

    /**
     * 获取application.yaml中的当前环境（只服务于本项目）
     * @return 返回当前的环境
     */
    @SuppressWarnings("unchecked")
    public static String systemEnvironment(){
        try {
            InputStream inputStream = new ClassPathResource("application.yaml").getInputStream();
            if(inputStream == null){
                throw new RuntimeException("application.yaml获取不到！");
            }
            Map<String, Object> map = YAML.load(inputStream);
            return ((Map<String, Object>)((Map<String, Object>) map.get("spring")).get("profiles")).get("active").toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "prod";
        }
    }
}
