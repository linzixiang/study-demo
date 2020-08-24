package com.linzx.api.dto;

import java.io.Serializable;

public class DemoResDto implements Serializable {

    private String resStr;

    public String getResStr() {
        return resStr;
    }

    public void setResStr(String resStr) {
        this.resStr = resStr;
    }

    @Override
    public String toString() {
        return "DemoResDto{" +
                "resStr='" + resStr + '\'' +
                '}';
    }
}
