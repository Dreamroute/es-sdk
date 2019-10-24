package com.github.drearmoute.es.sdk.mapping;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Mapping {
    
    private Mapping(Map<String, Object> properties) {
        this.properties = properties;
    }
    
    private Map<String, Object> properties = new HashMap<>();
    
    public void put(String field, Type type) {
        properties.put(field, type);
    }
    
    public String toJSON() {
        return toJSON(false);
    }
    
    public String toJSON(boolean pretty) {
        return JSON.toJSONString(new Mapping(this.properties), pretty);
    }

}
