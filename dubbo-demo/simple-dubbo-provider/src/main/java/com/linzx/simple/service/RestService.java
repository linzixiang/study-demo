package com.linzx.simple.service;

import com.linzx.api.service.IRestService;

public class RestService implements IRestService {

    @Override
    public String helloDemo(String info) {
        return info;
    }

}
