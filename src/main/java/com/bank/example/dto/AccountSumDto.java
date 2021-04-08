package com.bank.example.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AccountSumDto {

    private Long id;

    private String firstName;

    private String lastName;

    private Double sum;
}
