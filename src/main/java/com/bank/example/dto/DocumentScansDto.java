package com.bank.example.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class DocumentScansDto {

    private long id;

    private String passport;

    private String ITN;

    private String insuranceNumber;
}
