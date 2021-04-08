package com.bank.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class OperatorDto {

    private Long id;

    private String firstName;

    private String lastName;

    private String phone;

    private String passportNumber;

}
