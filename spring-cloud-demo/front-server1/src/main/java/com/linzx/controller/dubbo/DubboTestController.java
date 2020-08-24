package com.linzx.controller.dubbo;

import com.linzx.dubbo.api.IBizService;
import com.linzx.dubbo.dto.BizParamIn;
import com.linzx.dubbo.dto.BizParamOut;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.rpc.RpcContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/dubbo/test")
public class DubboTestController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Reference(check = false)
    private IBizService bizService;

    @RequestMapping("/demo")
    public String demo(@RequestParam("field1") String field1) {
        BizParamIn paramIn = new BizParamIn();
        paramIn.setField1In(field1);
        RpcContext.getContext().setAttachment("contextKey", "contextVal");
        BizParamOut bizParamOut = null;
        try {
            bizParamOut = bizService.bizMethod1(paramIn);
        }catch (Exception e) {
            // 服务捕获FlowException异常
            logger.error("服务调用异常", e);
            return e.getMessage();
        }
        return bizParamOut.toString();
    }

}
