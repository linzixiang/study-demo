package com.linzx.demo.propsource;

import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.util.StringUtils;

import java.util.Map;

public class JsonPropertySource extends EnumerablePropertySource<Map<String, Object>> {

    public JsonPropertySource(String name, Map<String, Object> source) {
        super(name, source);
    }

    @Override
    public String[] getPropertyNames() {
        return StringUtils.toStringArray(this.source.keySet());
    }

    @Override
    public Object getProperty(String name) {
        return this.source.get(name);
    }
}
