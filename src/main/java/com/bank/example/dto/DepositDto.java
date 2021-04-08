package com.bank.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class DepositDto {

    private Long id;

    private LocalDate openDate;

    private LocalDate closeDate;

    private Float rate;

    private Double sum;
}
