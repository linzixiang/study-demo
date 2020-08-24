package com.linzx.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "order-server2", fallback = Server2ServiceFeignClient.Server2ServiceFeignClientFallback.class)
public interface Server2ServiceFeignClient {

    @RequestMapping("/order-server2/demo/test1")
    String test1();

    @RequestMapping("/order-server2/demo/test2")
    String test2();

    @RequestMapping(value = "/order-server2/demo/maxTimeout/{timeout}", method = RequestMethod.GET)
    String timeout(@PathVariable(value = "timeout") Long timeout);

    @RequestMapping("/order-server2/demo/test1")
    String semaphoreIsolation();

    @RequestMapping("/order-server2/demo/test1")
    String threadIsolation();

    @Component
    class Server2ServiceFeignClientFallback implements Server2ServiceFeignClient {

        @Override
        public String test1() {
            return "服务繁忙";
        }

        @Override
        public String test2() {
            return "服务繁忙";
        }

        @Override
        public String timeout(Long timeout) {
            return "请求超时";
        }

        @Override
        public String semaphoreIsolation() {
            return "fail semaphore";
        }

        @Override
        public String threadIsolation() {
            return "fail thread";
        }
    }

}
