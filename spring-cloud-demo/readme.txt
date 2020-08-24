技术实现
    网关：spring-gateway                           ok
    注册中心：eureka                               ok
    rpc远程调用：openfeign + ribbon 或 dubbo       ok
    配置中心：apollo
    服务熔断/降级：hystrix 或 sentinel
    消息订阅：rocketmq 或 kafka
    分布式事务：seata/tcc/rocketmq

服务搭建
    配置中心：Apollo
    注册中心：eureka-server
        节点1的jvm变量配置
            --server.port=8761 --eureka.client.service-url.defaultZone=http://localhost:8762/eureka
        节点2的jvm变量配置
            --server.port=8762 --eureka.client.service-url.defaultZone=http://localhost:8761/eureka
    网关服务：gateway-server
        --server.port=80
    前置服务：front-server1
        jvm变量配置
            --server.port=8080 --eureka.client.service-url.defaultZone=http://localhost:8761/eureka,http://localhost:8762/eureka
    业务服务2：order-server2
        节点1的jvm变量配置
            --server.port=9090 --eureka.client.service-url.defaultZone=http://localhost:8761/eureka,http://localhost:8762/eureka
        节点2的jvm变量配置
            --server.port=9091 --eureka.client.service-url.defaultZone=http://localhost:8761/eureka,http://localhost:8762/eureka

链路跟踪，服务间远程调用日志输出格式：2020-08-15 22:01:44.617  INFO [application id,trace id,span id,true/false]
    trace id：一次完整的网络请求id；span id：单次调用的请求id
    测试请求 http://localhost:8888/gateway/front-server1/ribbon-demo/demo2
