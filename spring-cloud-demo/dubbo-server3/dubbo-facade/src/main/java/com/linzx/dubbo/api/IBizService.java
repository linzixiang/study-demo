package com.linzx.dubbo.api;

import com.linzx.dubbo.dto.BizParamIn;
import com.linzx.dubbo.dto.BizParamOut;

public interface IBizService {

    BizParamOut bizMethod1(BizParamIn paramIn);

}
