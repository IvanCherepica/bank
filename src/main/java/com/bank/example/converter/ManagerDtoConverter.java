package com.bank.example.converter;

import com.bank.example.dto.ManagerDto;
import com.bank.example.model.Manager;

import java.util.ArrayList;
import java.util.List;

public class ManagerDtoConverter {

    public static Manager convertDtoToEntity(ManagerDto dto) {
        return new Manager(dto.getPhone(), dto.getPassportNumber());
    }

    public static ManagerDto convertEntityToDto(Manager entity) {
        return ManagerDto.builder()
                .id(entity.getId())
                .phone(entity.getPhone())
                .passportNumber(entity.getPassportNumber())
                .build();
    }

    public static List<ManagerDto> convertListEntityToListDto(List<Manager> entityList) {
        List<ManagerDto> dtoList = new ArrayList<>(entityList.size());

        for (Manager entity : entityList) {
            dtoList.add(convertEntityToDto(entity));
        }

        return dtoList;
    }
}
