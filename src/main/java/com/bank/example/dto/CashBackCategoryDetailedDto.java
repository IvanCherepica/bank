package com.bank.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class CashBackCategoryDetailedDto {

    private long categoryId;

    private String name;

    private ManagerDetailedDto uploader;

    private List<CashBackCompanyDetailedDto> companies;
}
