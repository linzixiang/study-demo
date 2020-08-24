package com.linzx.dubbo;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;

@SpringBootApplication
public class DubboServer3Application {

    public static void main(String[] args) {
        initFlowRule();
        SpringApplication.run(DubboServer3Application.class, args);
    }

    /**
     * 初始化限流规则
     */
    public static void initFlowRule() {
        FlowRule flowRule = new FlowRule();
        flowRule.setResource("com.linzx.dubbo.api.IBizService:bizMethod1(com.linzx.dubbo.dto.BizParamIn)");
        flowRule.setCount(10);
        flowRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // 流量控制行为：1、直接拒绝（default）；2、warm up;3、匀速排队
        flowRule.setControlBehavior(RuleConstant.CONTROL_BEHAVIOR_DEFAULT);
        FlowRuleManager.loadRules(Collections.singletonList(flowRule));
    }

}
