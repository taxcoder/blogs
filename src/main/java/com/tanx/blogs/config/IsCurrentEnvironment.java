package com.tanx.blogs.config;

import com.tanx.blogs.utils.ReaderPropertiesOrYamlUtil;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.io.*;

/**
 * @author tan
 */
@Configuration
public class IsCurrentEnvironment implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        return "prod".equals(context.getEnvironment().getProperty("spring.profiles.active"));
    }
}
