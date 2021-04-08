package com.bank.example.converter;

import com.bank.example.dto.ManagerDto;
import com.bank.example.dto.OperatorDto;
import com.bank.example.model.Manager;
import com.bank.example.model.Operator;

import java.util.ArrayList;
import java.util.List;

public class OperatorDtoConverter {

    public static Operator convertDtoToEntity(OperatorDto dto) {
        return new Operator(
                dto.getFirstName(),
                dto.getLastName(),
                dto.getPhone(),
                dto.getPassportNumber()
        );
    }

    public static OperatorDto convertEntityToDto(Operator entity) {
        return OperatorDto.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .phone(entity.getPhone())
                .passportNumber(entity.getPassportNumber())
                .build();
    }

    public static List<OperatorDto> convertListEntityToListDto(List<Operator> entityList) {
        List<OperatorDto> dtoList = new ArrayList<>(entityList.size());

        for (Operator entity : entityList) {
            dtoList.add(convertEntityToDto(entity));
        }

        return dtoList;
    }
}
