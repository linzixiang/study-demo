package com.linzx.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

//@Order(100)
@Component
public class MyDefineGatewayFilterFactory extends AbstractGatewayFilterFactory<MyDefineGatewayFilterFactory.MyDefine> {

    private static final String NAME_KEY = "name";

    Logger logger = LoggerFactory.getLogger(MyDefineGatewayFilterFactory.class);

    public MyDefineGatewayFilterFactory() {
        super(MyDefine.class);
    }

    public GatewayFilter apply(MyDefine config) {
        return ((exchange, chain) -> {
            logger.info("[pre] Filter Request name:" + config.getName());
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                logger.info("[post] Response Filter");
            }));
        });
    }

    public List<String> shortcutFieldOrder() {
        return Arrays.asList(NAME_KEY);
    }

    public static class MyDefine {

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
