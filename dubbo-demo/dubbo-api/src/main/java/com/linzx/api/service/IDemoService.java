package com.linzx.api.service;

import com.linzx.api.dto.DemoParamDto;
import com.linzx.api.dto.DemoResDto;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/demo")
public interface IDemoService {

    @GET
    @Path("/helloDemo")
    DemoResDto helloDemo(DemoParamDto paramDto);

}
