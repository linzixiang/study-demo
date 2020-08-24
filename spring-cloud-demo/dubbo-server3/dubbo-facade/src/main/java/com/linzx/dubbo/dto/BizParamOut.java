package com.linzx.dubbo.dto;

import java.io.Serializable;

public class BizParamOut implements Serializable {

    private String field1Out;

    public String getField1Out() {
        return field1Out;
    }

    public void setField1Out(String field1Out) {
        this.field1Out = field1Out;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"field1Out\":\"")
                .append(field1Out).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
