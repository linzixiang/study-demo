package com.linzx.simple.service;

import com.linzx.api.dto.DemoParamDto;
import com.linzx.api.dto.DemoResDto;
import com.linzx.api.service.IDemoService;

public class DemoService implements IDemoService {

    @Override
    public DemoResDto helloDemo(DemoParamDto paramDto) {
        DemoResDto demoResDto = new DemoResDto();
        demoResDto.setResStr("helloDemo");
        return demoResDto;
    }

}
