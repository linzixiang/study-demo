package com.linzx.spring.controller;

import com.linzx.api.dto.DemoParamDto;
import com.linzx.api.dto.DemoResDto;
import com.linzx.api.service.IDemoService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dubboClient")
public class DubboClientController {

    @Reference
    private IDemoService demoService;

    @GetMapping("/hello")
    public String hello() {
        DemoParamDto paramDto = new DemoParamDto();
        paramDto.setParam1("param1 hello");
        DemoResDto demoResDto = demoService.helloDemo(paramDto);
        return demoResDto.toString();
    }

}
