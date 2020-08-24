package com.linzx.demo.propsource;

import org.springframework.cloud.bootstrap.config.PropertySourceLocator;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;

import java.util.HashMap;
import java.util.Map;

/**
 * 需要在classpath:META-INF/spring.factories下配置该扩展点
 * org.springframework.cloud.bootstrap.BootstrapConfiguration=\
 *   com.linzx.demo.propsource.MyPropertySourceLocator
 */
public class MyPropertySourceLocator implements PropertySourceLocator {

    private final static String DEFAULT_LOCATION = "classpath:application.json";

    @Override
    public PropertySource<?> locate(Environment environment) {
        Map<String, Object> resolverJsonFile = new HashMap<>(); // TODO Json解析结果
        resolverJsonFile.put("test.field", 10);
        return new JsonPropertySource("jsonPropertySource", resolverJsonFile);
    }

}
