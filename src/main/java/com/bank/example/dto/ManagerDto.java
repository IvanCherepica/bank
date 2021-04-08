package com.bank.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class ManagerDto {

    private long id;

    private String phone;

    private String passportNumber;

    public ManagerDto() {
    }
}
