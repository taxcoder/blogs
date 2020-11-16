package com.tanx.blogs.config;

import com.tanx.blogs.utils.ReaderPropertiesOrYamlUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;

/**
 * @author tanxiang
 * @version 1.0
 * @date 2020/10/24 下午2:55
 */
@Configuration
@Conditional(IsCurrentEnvironment.class)
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfoBuilder())
                // 是否启用swagger
                .groupName("blogs")
                .enable("dev".equals(ReaderPropertiesOrYamlUtil.systemEnvironment()))
                .select()
                // 扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.tanx.blogs.controller"))
                // 过滤url路径
                .paths(PathSelectors.any())
                .build();
    }

    // 使用多个文档
    @Bean
    public Docket docketOne(){
        return new Docket(DocumentationType.SWAGGER_2).groupName("one");
    }

    private ApiInfo apiInfoBuilder(){

        return new ApiInfo(
                // 标题
                "onePeopleBlog-API",
                // 描述
                "tanxiang api test",
                // 版本n
                "v1.0",
                // 服务条款url
                "https://www.tanxiangblog.com/",
                // 作者信息(名称、地址、邮箱)
                new Contact("tanxiang","","1571922819@qq.com"),
                // 开源许可证
                "Apache 2.0",
                // 开源许可证地址
                "http://www.apache.org/licenses/LICENSE-2.0",
                // 第三方插件
                new ArrayList<>());
    }
}
