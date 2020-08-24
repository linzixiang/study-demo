package com.linzx.controller.hystrix;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/hystrix/test")
public class HystrixTestController {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 熔断触发
     * @param num
     * @return
     */
    @HystrixCommand(commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value="true"), // 开启熔断
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value="5"), // 最小请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000"), // 熔断时间（5秒）
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50") // 请求失败率超过50%
    }, fallbackMethod = "circuitBreakerException")
    @GetMapping("/circuitBreaker1/{num}")
    public String circuitBreakerTest1(@PathVariable("num") Integer num) {
        if (num % 2 == 0) {
            return "正常访问";
        }
        // 请求异常
        String retStr = restTemplate.getForObject("http://order-server2/order-server2/demo/xxx", String.class);
        return retStr;
    }

    public String circuitBreakerException(Integer num) {
        return "系统繁忙";
    }

    /**
     * 超时触发熔断
     */
    @HystrixCommand(commandProperties = {
       @HystrixProperty(name = "execution.isolation.thread.interruptOnTimeout", value = "3000") // 超时时间
    },
    fallbackMethod = "circuitBreakerTimeout")
    @GetMapping("/circuitBreaker2/{timeout}")
    public String circuitBreakerTest2(@PathVariable("timeout") Integer timeout) {
        // 请求超时
        String retStr = restTemplate.getForObject("http://order-server2/order-server2/demo/maxTimeout/" + timeout, String.class);
        return retStr;
    }

    public String circuitBreakerTimeout(Integer num) {
        return "请求超时";
    }

}
