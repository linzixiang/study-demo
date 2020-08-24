package com.linzx.controller.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * 通过HystrixCommand的Api方式实现服务降级
 */
@RestController
public class HystrixCommandController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/hystrixCommandTest/{num}")
    public String hystrixCommandTest(@PathVariable("num") Integer num) {
        return new HystrixCommandService(restTemplate, num).execute();
    }

    class HystrixCommandService extends HystrixCommand<String> {

        private RestTemplate restTemplate;

        private Integer num;

        public HystrixCommandService(RestTemplate restTemplate, int num) {
            super(HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("hystrixCommandTest"))
                    .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                            .withCircuitBreakerEnabled(true)
                            .withCircuitBreakerRequestVolumeThreshold(5)));
            this.restTemplate = restTemplate;
            this.num = num;
        }

        @Override
        protected String run() throws Exception {
            if (this.num % 2 == 0) {
                return "正常访问";
            }
             // 请求异常
            return  restTemplate.getForObject("http://order-server2/order-server2/demo/xxx", String.class);
        }

        @Override
        protected String getFallback() {
            return "请求降级";
        }
    }

}
