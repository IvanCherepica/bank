package com.bank.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class CashBackCompanyDetailedDto {

    private long companyId;

    private String name;

    private ManagerDto uploader;
}
