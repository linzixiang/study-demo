package com.linzx.controller.hystrix;

import com.linzx.api.Server2ServiceFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping("/hystrix/feignClient")
public class HystrixFeignClientController {

    @Autowired
    private Server2ServiceFeignClient feignClient;

    /**
     * 请求异常
     * @param num
     * @return
     */
    @GetMapping("/circuitBreaker1/{num}")
    public String circuitBreakerTest1(@PathVariable("num") Integer num) {
        if (num % 2 == 0) {
            return "正常访问";
        }
        String retStr = "";
        if (new Random().nextFloat() > 0.5) {
            // 请求异常
            retStr = feignClient.test2();
        } else {
            retStr = feignClient.test1();
        }
        return retStr;
    }

    @GetMapping("/circuitBreaker2/{timeout}")
    public String circuitBreakerTest2(@PathVariable("timeout") Integer timeout) {
        // 请求超时
        String retStr = feignClient.timeout(Long.parseLong(timeout.toString()));
        return retStr;
    }

    /**
     * 信号量隔离
     */
    @GetMapping("/circuitBreakerTest3")
    public String circuitBreakerTest3() {
        return feignClient.semaphoreIsolation();
    }

    /**
     * 线程池隔离
     */
    @GetMapping("/circuitBreakerTest4")
    public String circuitBreakerTest4() {
        return feignClient.threadIsolation();
    }

}
