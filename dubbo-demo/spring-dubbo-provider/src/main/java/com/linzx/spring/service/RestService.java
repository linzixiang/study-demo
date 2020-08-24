package com.linzx.spring.service;

import com.linzx.api.service.IRestService;
import org.apache.dubbo.config.annotation.Service;

@Service
public class RestService implements IRestService {

    @Override
    public String helloDemo(String info) {
        return info;
    }

}
