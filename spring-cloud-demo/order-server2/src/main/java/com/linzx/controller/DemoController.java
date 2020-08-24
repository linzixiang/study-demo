package com.linzx.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;


@RestController
@RequestMapping("/demo")
public class DemoController {

    @Value("${server.port}")
    private String port;

    @RequestMapping("/test1")
    public String test1() {
        System.out.println("-----------" + port + "--------------");
        return "hello world";
    }

    /**
     * 模拟超时
     */
    @RequestMapping("/maxTimeout/{timeout}")
    public String timeout(@PathVariable("timeout") Long timeout) {
        try {
            timeout = new Double(Math.floor(new Random().nextFloat() * timeout)).longValue();
            Thread.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "callback";
    }

}
