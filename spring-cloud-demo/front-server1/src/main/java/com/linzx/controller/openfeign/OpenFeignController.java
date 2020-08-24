package com.linzx.controller.openfeign;

import com.linzx.api.Server2ServiceFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/open-feign")
public class OpenFeignController {

    @Autowired
    private Server2ServiceFeignClient feignClient;

    @RequestMapping("/demo1")
    public String demo1() {
        return feignClient.test1();
    }

}
