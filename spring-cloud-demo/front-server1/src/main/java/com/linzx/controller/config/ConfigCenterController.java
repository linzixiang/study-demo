package com.linzx.controller.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 当配置发生改变时，如何刷新相关bean属性
 * 第一步：添加依赖
 *      <dependency>
 *          <groupId>org.springframework.cloud</groupId>
 *          <artifactId>spring-cloud-starter-config</artifactId>
 *      </dependency>
 * 第二步：在需要刷新属性的Bean上添加注解@RefreshScope
 * 第三步：application.properties增加属性
 *      management.endpoints.web.exposure.include=refresh
 * 第三步：访问地址：http://xxx.xx.xx.xx:xxxx/actuator/refresh
 */
@RestController
@RequestMapping("/config-center")
public class ConfigCenterController {

    @Autowired
    private Environment environment;

    @RequestMapping("/field/{field}")
    public String demo1(@PathVariable("field") String field) {
        String property = environment.getProperty(field);
        return property;
    }

}
