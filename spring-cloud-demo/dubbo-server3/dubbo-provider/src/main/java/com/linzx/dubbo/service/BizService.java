package com.linzx.dubbo.service;

import com.linzx.dubbo.api.IBizService;
import com.linzx.dubbo.dto.BizParamIn;
import com.linzx.dubbo.dto.BizParamOut;
import org.apache.dubbo.config.annotation.Service;
import org.apache.dubbo.rpc.RpcContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

@Service
public class BizService implements IBizService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public BizParamOut bizMethod1(BizParamIn paramIn) {
        Map<String, String> attachments = RpcContext.getContext().getAttachments();
        logger.info("输入参数：" + paramIn.toString());
        BizParamOut bizParamOut = new BizParamOut();
        bizParamOut.setField1Out("hello world!");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return bizParamOut;
    }

}
