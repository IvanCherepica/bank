package com.bank.example.converter;

import com.bank.example.dto.AtmTransactionDto;
import com.bank.example.dto.CashBackCategoryDetailedDto;
import com.bank.example.dto.CashBackCompanyDetailedDto;
import com.bank.example.dto.DepartmentDto;
import com.bank.example.dto.ManagerDetailedDto;
import com.bank.example.dto.ManagerDto;
import com.bank.example.model.CashBackCategory;
import com.bank.example.model.CashBackCompany;
import com.bank.example.model.Department;
import com.bank.example.model.Manager;
import com.bank.example.model.operation.AtmTransaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CashBackCategoryDetailedConverter {

    public static List<CashBackCategoryDetailedDto> convertEntityToDtoList(List<CashBackCategory> categoryList) {
        List<CashBackCategoryDetailedDto> dtoList = new ArrayList<>(categoryList.size());

        for (CashBackCategory category : categoryList) {
            CashBackCategoryDetailedDto dto = CashBackCategoryDetailedDto.builder()
                    .categoryId(category.getId())
                    .name(category.getName())
                    .uploader(getManagerDetailedDto(category.getEditor()))
                    .companies(getCompanyDtoList(category))
                    .build();

            dtoList.add(dto);
        }

        return dtoList;
    }

    private static List<CashBackCompanyDetailedDto> getCompanyDtoList(CashBackCategory category) {
        List<CashBackCompany> companyList = new ArrayList<>(category.getCashBackCompanies());
        List<CashBackCompanyDetailedDto> dtoList = new ArrayList<>();

        for (CashBackCompany company : companyList) {
            CashBackCompanyDetailedDto dto = CashBackCompanyDetailedDto.builder()
                    .companyId(company.getId())
                    .name(company.getName())
                    .uploader(getManagerDto(company.getEditor()))
                    .build();

            dtoList.add(dto);
        }

        return dtoList;
    }

    private static ManagerDetailedDto getManagerDetailedDto(Manager manager) {
        return ManagerDetailedDto.builder()
                .id(manager.getId())
                .phone(manager.getPhone())
                .passportNumber(manager.getPassportNumber())
                .department(getDepartmentDto(manager.getDepartment()))
                .build();
    }

    private static DepartmentDto getDepartmentDto(Department department) {
        return DepartmentDto.builder()
                .id(department.getId())
                .name(department.getName())
                .build();
    }

    private static ManagerDto getManagerDto(Manager manager) {
        return ManagerDto.builder()
                .id(manager.getId())
                .phone(manager.getPhone())
                .passportNumber(manager.getPassportNumber())
                .build();
    }
}
