package com.linzx;

import com.linzx.demo.propsource.MyPropertySourceLocator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.PropertySource;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@SpringBootApplication
/**  开启Feign客户端代理  **/
@EnableFeignClients(basePackages = "com.linzx.api")
/** 开启熔断 **/
@EnableCircuitBreaker
public class FrontServer1Application {

//    public static void main(String[] args) {
//        SpringApplication.run(FrontServer1Application.class, args);
//    }

    public static void main(String[] args) {
        Map<String, Object> defaultsProperties = new HashMap<>();
        defaultsProperties.put("server.port", 8079);
        ConfigurableApplicationContext applicationContext = new SpringApplicationBuilder()
                .sources(FrontServer1Application.class)
                .properties(defaultsProperties)
                .run(args);
        Iterator<PropertySource<?>> propertySourceIterator = applicationContext.getEnvironment().getPropertySources().iterator();
        while (propertySourceIterator.hasNext()) {
            PropertySource<?> propertySource = propertySourceIterator.next();
            String name = propertySource.getName();
            System.out.println("properties source：" + name);
        }
    }

    /**
     * ribbon负载均衡
     */
    @LoadBalanced
    @Bean
    @Primary
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        builder.setConnectTimeout(Duration.ofMinutes(1L));
        builder.setReadTimeout(Duration.ofMinutes(1L));
        return builder.build();
    }

}
