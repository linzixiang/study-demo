package com.linzx.api.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/demo")
public interface IRestService {

    @GET
    @Path("/helloDemo/{info}")
    String helloDemo(@PathParam("info") String info);

}
