package com.bank.example.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class TariffDto {
    private Long id;
    private String name;
    private List<Long> accountIds;
}
