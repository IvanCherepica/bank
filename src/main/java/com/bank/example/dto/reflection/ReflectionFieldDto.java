package com.bank.example.dto.reflection;

import java.util.List;

public class ReflectionFieldDto {

    private String type;

    private List<ReflectionAnnotationDto> annotations;

    public ReflectionFieldDto(String type, List<ReflectionAnnotationDto> annotations) {
        this.type = type;
        this.annotations = annotations;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<ReflectionAnnotationDto> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(List<ReflectionAnnotationDto> annotations) {
        this.annotations = annotations;
    }
}
