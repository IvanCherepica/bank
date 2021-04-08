package com.bank.example.dto.reflection;

import java.util.Map;

public class ReflectionAnnotationDto {

    private String name;

    private Map<String, Object> attributeValueMap;

    public ReflectionAnnotationDto(String name, Map<String, Object> attributeValueMap) {
        this.name = name;
        this.attributeValueMap = attributeValueMap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Object> getAttributeValueMap() {
        return attributeValueMap;
    }

    public void setAttributeValueMap(Map<String, Object> attributeValueMap) {
        this.attributeValueMap = attributeValueMap;
    }
}
