package com.bank.example.dto.reflection;

import java.util.Map;

public class ReflectionEntityDto {

    private Map<String, ReflectionFieldDto> fieldDtoMap;

    public ReflectionEntityDto(Map<String, ReflectionFieldDto> fieldDtoMap) {
        this.fieldDtoMap = fieldDtoMap;
    }

    public Map<String, ReflectionFieldDto> getFieldDtoMap() {
        return fieldDtoMap;
    }

    public void setFieldDtoMap(Map<String, ReflectionFieldDto> fieldDtoMap) {
        this.fieldDtoMap = fieldDtoMap;
    }
    public void addField(String fieldName, ReflectionFieldDto dto) {
        this.fieldDtoMap.put(fieldName, dto);
    }
}
