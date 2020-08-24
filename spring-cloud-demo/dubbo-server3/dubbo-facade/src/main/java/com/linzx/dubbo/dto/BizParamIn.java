package com.linzx.dubbo.dto;

import java.io.Serializable;

public class BizParamIn implements Serializable {

    private String field1In;

    public String getField1In() {
        return field1In;
    }

    public void setField1In(String field1In) {
        this.field1In = field1In;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"field1In\":\"")
                .append(field1In).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
