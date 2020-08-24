package com.linzx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
/** 开启Eureka注册中心 **/
@EnableEurekaClient
public class OrderServer2Application {

    public static void main(String[] args) {
        SpringApplication.run(OrderServer2Application.class, args);
    }

}
