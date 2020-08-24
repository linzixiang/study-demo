package com.linzx.apollo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApolloConfigController {

    @Value("${http.timeout}")
    private String httpTimeout;

    @GetMapping("/apolloConfig/demo")
    public String demo() {
        return httpTimeout;
    }

}
