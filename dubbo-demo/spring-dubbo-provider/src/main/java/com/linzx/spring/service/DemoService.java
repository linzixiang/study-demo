package com.linzx.spring.service;

import com.linzx.api.dto.DemoParamDto;
import com.linzx.api.dto.DemoResDto;
import com.linzx.api.service.IDemoService;
import org.apache.dubbo.config.annotation.Service;

@Service
public class DemoService implements IDemoService {

    @Override
    public DemoResDto helloDemo(DemoParamDto paramDto) {
        DemoResDto demoResDto = new DemoResDto();
        demoResDto.setResStr("helloDemo spring");
        return demoResDto;
    }

}
