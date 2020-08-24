package com.linzx.controller.ribbon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/ribbon-demo")
public class RibbonDemo1Controller {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @RequestMapping("/demo1")
    public String demo1() {
        ServiceInstance instance = loadBalancerClient.choose("order-server2");
        String uri = String.format("http://%s:%s/order-server2/demo/test1", instance.getHost(), instance.getPort());
        return uri;
    }

    @RequestMapping("/demo2")
    public String demo2() {
        String retStr = restTemplate.getForObject("http://order-server2/order-server2/demo/test1", String.class);
        return retStr;
    }

}
