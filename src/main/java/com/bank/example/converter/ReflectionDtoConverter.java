package com.bank.example.converter;

import com.bank.example.dto.reflection.ReflectionAnnotationDto;
import com.bank.example.dto.reflection.ReflectionEntityDto;
import com.bank.example.dto.reflection.ReflectionFieldDto;
import lombok.SneakyThrows;

import javax.persistence.CascadeType;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReflectionDtoConverter {

    public static ReflectionEntityDto getReflectionEntityDto(Class clazz) throws InvocationTargetException, IllegalAccessException {

        Map<String, ReflectionFieldDto> fieldDtoMap = new HashMap<>();

        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            String type = field.getType().getSimpleName();
            List<ReflectionAnnotationDto> annotations = new ArrayList<>();

            for (Annotation annotation : field.getDeclaredAnnotations()) {
                String name = annotation.annotationType().getSimpleName();
                Map<String, Object> attrValueMap = new HashMap<>();

                for (Method method : annotation.getClass().getDeclaredMethods()) {
                    String attr = method.getName();
                    Object value = null;
                    try {
                        Object returnValue = method.invoke(annotation);
                        if (returnValue.getClass().isArray()) {
                            value = parseArray(returnValue);
                        } else {
                            value = returnValue.toString();
                        }
                    } catch (IllegalArgumentException ignored) {}
                    attrValueMap.put(attr, value);
                }

                annotations.add(new ReflectionAnnotationDto(name, attrValueMap));
            }

            ReflectionFieldDto fieldDto = new ReflectionFieldDto(type, annotations);

            fieldDtoMap.put(field.getName(), fieldDto);
        }

        return new ReflectionEntityDto(fieldDtoMap);
    }

    @SneakyThrows
    private static Object parseArray(Object object) {
        List objectList = Arrays.asList(object);
        List<Object> returnList = new ArrayList<>();

        if (Array.getLength(object) == 0) return new Object();

        for (Object component : objectList) {

            boolean isAnnotation = false;
            boolean isCascade = false;

            try {
                isAnnotation = Proxy.getInvocationHandler(Array.get(component, 0)).getClass().getName().equals("sun.reflect.annotation.AnnotationInvocationHandler");
            } catch (Exception ignored) {}

            try {
                isCascade = Array.get(component, 0).getClass().equals(CascadeType.class);
            } catch (Exception ignored) {}

            if (isAnnotation) {
                Field type = Proxy.getInvocationHandler(Array.get(component, 0)).getClass().getDeclaredField("type");
                Field memberValues = Proxy.getInvocationHandler(Array.get(component, 0)).getClass().getDeclaredField("memberValues");
                type.setAccessible(true);
                memberValues.setAccessible(true);

                String name = ((Class<?>)type.get(Proxy.getInvocationHandler(Array.get(component, 0)))).getSimpleName();
                Map<String, Object> attributeValueMap = (Map<String, Object>) memberValues.get(Proxy.getInvocationHandler(Array.get(component, 0)));

                returnList.add(new ReflectionAnnotationDto(name, attributeValueMap));
            } else if (isCascade) {
                for (int i = 0; i < ((CascadeType[]) objectList.get(0)).length; i++) {
                    returnList.add(Array.get(component, i));
                }
            } else {
                returnList.add(component);
            }
        }

        return returnList;
    }
}
